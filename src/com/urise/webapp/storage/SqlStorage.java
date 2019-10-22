package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        sqlHelper.transactionExecute(ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            try {
                ps.execute();
            } catch (SQLException e) {
                if(e.getSQLState().equals("23505"))
                    throw new ExistStorageException(resume.getUuid());
            }
            return null;
        }, "INSERT INTO contact (resume_uuid, type, value) values (?,?,?)");
        for (Map.Entry<ContactType,String> entry:resume.getContacts().entrySet()) {
            sqlHelper.transactionExecute(ps -> {
                ps.setString(1, resume.getUuid());
                ps.setString(2, entry.getKey().name());
                ps.setString(3, entry.getValue());
                try {
                    ps.execute();
                } catch (SQLException e) {
                    if (e.getSQLState().equals("23505"))
                        throw new ExistStorageException(resume.getUuid());
                }

                return null;
            }, "INSERT INTO RESUME (uuid, full_name) values (?,?)");
        }
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
            do{
                String value =rs.getString("value");
                ContactType type = ContactType.valueOf(rs.getString("type"));
                r.addContact(type,value);
            }while (rs.next());
            return r;
        },
        "SELECT * FROM RESUME r " +
                "LEFT JOIN CONTACT c on (r.uuid=c.resume_uuid) " +
                "WHERE r.uuid=?");
    }

    @Override
    public void delete(String uuid) {
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
            if (!rs.next()) {return 0;}
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
