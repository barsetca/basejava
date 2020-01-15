package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.AbstractSections;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        //Class.forName - for connection Servlet to DB
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            String uuid = resume.getUuid();
            try (PreparedStatement ps =
                         conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                insertUuidFullName(resume.getFullName(), uuid, ps);
                checkExist(ps, uuid);
            }

            deleteElement("DELETE FROM contact WHERE resume_uuid = ?", uuid, conn);
            deleteElement("DELETE FROM section WHERE resume_uuid = ?", uuid, conn);

            insertContacts(resume, conn);
            insertSections(resume, conn);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps =
                         conn.prepareStatement("INSERT INTO resume (full_name, uuid) VALUES (?, ?)")) {
                insertUuidFullName(resume.getFullName(), resume.getUuid(), ps);
                ps.execute();
            }
            insertContacts(resume, conn);
            insertSections(resume, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        "      LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "      LEFT JOIN section s " +
                        "         ON r.uuid = s.resume_uuid " +
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
                        setSection(resume, rs);
                    } while (rs.next());

                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            checkExist(ps, uuid);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps =
                         conn.prepareStatement("SELECT * FROM resume " +
                                 "ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    String full_name = rs.getString("full_name");
                    resumes.computeIfAbsent(uuid, v -> new Resume(uuid, full_name));
                }
            }

            try (PreparedStatement ps =
                         conn.prepareStatement("SELECT * FROM contact ")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    setContact(resumes.get(rs.getString("resume_uuid")), rs);
                }
            }
            try (PreparedStatement ps =
                         conn.prepareStatement("SELECT * FROM section ")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    setSection(resumes.get(rs.getString("resume_uuid")), rs);
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", ps -> {
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
        String type = rs.getString("contact_type");
        if (type != null) {
            resume.setContact(ContactType.valueOf(type), rs.getString("contact_value"));
        }
    }

    private void setSection(Resume resume, ResultSet rs) throws SQLException {
        String section = rs.getString("section_value");
        if (section != null) {
            SectionType type = SectionType.valueOf(rs.getString("section_type"));
            resume.setSection(type, JsonParser.read(section, AbstractSections.class));
        }

        /* Рeaлизация если section_value = контентинированная строка через \n

        String type = rs.getString("section_type");
        String section = rs.getString("section_value");
        if (type != null) {
            switch (type) {
                case "OBJECTIVE":
                case "PERSONAL":
                    resume.setSection(SectionType.valueOf(type), new LineSection(section));
                    break;
                case "ACHIEVEMENT":
                case "QUALIFICATION":
                    String[] sectionArray = section.split("\n");
                    resume.setSection(SectionType.valueOf(type), new ListSection(sectionArray));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + type);
            }
        }*/
    }

    private void insertContacts(Resume resume, Connection conn) throws SQLException {
        Map<ContactType, String> contacts = resume.getContactsMap();
        if (contacts.size() != 0) {
            try (PreparedStatement ps =
                         conn.prepareStatement("INSERT INTO contact (contact_type, contact_value, resume_uuid) VALUES (?, ?, ?)")) {
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

    private void insertSections(Resume resume, Connection conn) throws SQLException {
        Map<SectionType, AbstractSections> sections = resume.getSectionsMap();
        if (sections.size() != 0) {
            try (PreparedStatement ps =
                         conn.prepareStatement("INSERT INTO section (section_type, section_value, resume_uuid) VALUES (?, ?, ?)")) {
                for (Map.Entry<SectionType, AbstractSections> section : sections.entrySet()) {
                    String sectionType = section.getKey().name();
                    ps.setString(1, sectionType);
                    String value = JsonParser.write(section.getValue(), AbstractSections.class);
                    ps.setString(2, value);
                   /*
                   Рeaлизация если section_value = контентинированная строка через \n
                    switch (sectionType) {
                        case "OBJECTIVE":
                        case "PERSONAL":
                            LineSection lineSection = (LineSection) section.getValue();
                            ps.setString(2, lineSection.getText());
                            break;
                        case "ACHIEVEMENT":
                        case "QUALIFICATION":
                            ListSection listSection = (ListSection) section.getValue();
                            ps.setString(2, String.join("\n", listSection.getItems()));
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + sectionType);
                    }*/
                    ps.setString(3, resume.getUuid());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    private void deleteElement(String sql, String uuid, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }

    private void insertUuidFullName(String full_name, String uuid, PreparedStatement ps) throws SQLException {
        ps.setString(1, full_name);
        ps.setString(2, uuid);
    }
}
