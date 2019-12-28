package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
        sqlHelper.<Void>transactionalExecute(conn -> {
            try (PreparedStatement ps =
                         conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                insertUuidFullName(full_name, uuid, ps);
                checkExist(ps, uuid);
            }

            try (PreparedStatement ps =
                         conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
                ps.setString(1, uuid);
                ps.execute();
            }
            insertContacts(resume, conn);
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
                insertUuidFullName(full_name, uuid, ps);
                ps.execute();
            }
            insertContacts(resume, conn);
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
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        setContact(resume, rs);
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
        return sqlHelper.executeSqlHelper("SELECT * FROM resume r " +
                "LEFT JOIN contact c " +
                "ON r.uuid = c.resume_uuid " +
                "ORDER BY r.full_name, r.uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            Map<String, Resume> resumes = new LinkedHashMap<>();
            while (rs.next()) {
                String uuid = rs.getString("uuid");
                Resume resume = resumes.get(uuid);
                if (resume == null) {
                    resume = new Resume(uuid, rs.getString("full_name"));
                    SqlStorage.this.setContact(resume, rs);
                    resumes.put(uuid, resume);
                } else {
                    SqlStorage.this.setContact(resume, rs);
                }
            }
            return new ArrayList<>(resumes.values());
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

    private void setContact(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        if (type != null) {
            resume.setContact(ContactType.valueOf(type),
                    rs.getString("value"));
        }
    }

    private void insertContacts(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps =
                     conn.prepareStatement("INSERT INTO contact (type, value, resume_uuid) VALUES (?, ?, ?)")) {

            Map<ContactType, String> contacts = resume.getContactsMap();
            if (contacts.size() != 0) {
                for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
                    ps.setString(1, contact.getKey().name());
                    ps.setString(2, contact.getValue());
                    ps.setString(3, resume.getUuid());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    private void insertUuidFullName(String full_name, String uuid, PreparedStatement ps) throws SQLException {
        ps.setString(1, full_name);
        ps.setString(2, uuid);
    }
}
