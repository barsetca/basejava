package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.executeSqlHelper("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        String full_name = resume.getFullName();
        sqlHelper.executeSqlHelper("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            writeUuidFullName(full_name, uuid, ps);
            checkExist(ps, uuid);
            return null;
        });
        sqlHelper.<Void>transactionalExecute(conn -> {
            try (PreparedStatement ps =
                         conn.prepareStatement("UPDATE contact SET type = ?, value = ? WHERE resume_uuid = ?")) {
                for (Map.Entry<ContactType, String> contact : resume.getContactsMap().entrySet()) {
                    writeMap(contact, uuid, ps);
                }
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        String full_name = resume.getFullName();
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps =
                         conn.prepareStatement("INSERT INTO resume (full_name, uuid) VALUES (?, ?)")) {
                writeUuidFullName(full_name, uuid, ps);
                ps.execute();
            }
            try (PreparedStatement ps =
                         conn.prepareStatement("INSERT INTO contact (type,value, resume_uuid) VALUES (?, ?, ?)")) {
                for (Map.Entry<ContactType, String> contact : resume.getContactsMap().entrySet()) {
                    writeMap(contact, uuid, ps);
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {

        return sqlHelper.executeSqlHelper("" +
                        "    SELECT * FROM resume r " +
                        "      LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = createResume(uuid, rs);
                    do {
                        writeContact(resume, rs);
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.executeSqlHelper("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            checkExist(ps, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps =
                         conn.prepareStatement(
                                 "SELECT * FROM resume r " +
                                         "LEFT JOIN contact c " +
                                         "ON r.uuid = c.resume_uuid " +
                                         "ORDER BY r.full_name, r.uuid")) {
                List<Resume> resumes = new ArrayList<>();
                ResultSet rs = ps.executeQuery();
                String check = null;
                Resume resume = null;
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    if (!uuid.equals(check)) {
                        if (check != null) {
                            resumes.add(resume);
                        }
                        resume = createResume(uuid, rs);
                        writeContact(resume, rs);
                        check = uuid;
                    } else if (rs.getString("type") != null) {
                        writeContact(resume, rs);
                    }
                }
                resumes.add(resume);
                return resumes;
            }
        });
    }

    @Override
    public int size() {
        return sqlHelper.executeSqlHelper("SELECT count(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return !rs.next() ? 0 : rs.getInt(1);
        });
    }

    private void checkExist(PreparedStatement ps, String uuid) throws SQLException {
        if (ps.executeUpdate() == 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    private void writeContact(Resume resume, ResultSet rs) throws SQLException {
        resume.setContact(ContactType.valueOf(rs.getString("type")),
                rs.getString("value"));
    }

    private Resume createResume(String uuid, ResultSet rs) throws SQLException {
        return new Resume(rs.getString("uuid"), rs.getString("full_name"));
    }

    private void writeMap(Map.Entry<ContactType, String> contact, String uuid, PreparedStatement ps) throws SQLException {
        ps.setString(1, contact.getKey().name());
        ps.setString(2, contact.getValue());
        ps.setString(3, uuid);
    }

    private void writeUuidFullName(String full_name, String uuid, PreparedStatement ps) throws SQLException {
        ps.setString(1, full_name);
        ps.setString(2, uuid);
    }
}
