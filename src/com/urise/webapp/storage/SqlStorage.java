package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.transactionExecute(ps -> ps.execute(), "DELETE FROM RESUME");
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO RESUME (uuid, full_name) values (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }

                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) values (?,?,?)")) {
                        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                            ps.setString(1, resume.getUuid());
                            ps.setString(2, entry.getKey().name());
                            ps.setString(3, entry.getValue());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                    return null;
                }
        );
    }


    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionExecute(ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        String value = rs.getString("value");
                        ContactType type = ContactType.valueOf(rs.getString("type"));
                        r.addContact(type, value);
                    } while (rs.next());
                    return r;
                },
                "SELECT * FROM RESUME r " +
                        "LEFT JOIN CONTACT c on (r.uuid=c.resume_uuid) " +
                        "WHERE r.uuid=?");
    }

    @Override
    public void delete(String uuid) {
        /*удаление по каскаду*/
        sqlHelper.transactionExecute(p -> {
            p.setString(1, uuid);
            if (p.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        }, "DELETE from RESUME where UUID=?");
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionExecute(p -> {
            ResultSet rs = p.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return list;
        }, "SELECT * FROM RESUME ORDER BY full_name,uuid");

    }

    @Override
    public int size() {
        return sqlHelper.transactionExecute(ps -> {
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return 0;
            }
            return rs.getInt(1);
        }, "SELECT count (uuid) FROM RESUME");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionExecute(ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        }, "UPDATE  RESUME set full_name=? where uuid=?");
    }


}
