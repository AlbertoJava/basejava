package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
       /* try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM RESUME")
        ) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        transactionExecute(ps->ps.execute(),"DELETE FROM RESUME");
    }

    @Override
    public void save(Resume resume) {
 /*       try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO RESUME (uuid, full_name) values (?,?)")
        ) {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        */
        transactionExecute(ps->{
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        },"INSERT INTO RESUME (uuid, full_name) values (?,?)" );
    }

    @Override
    public Resume get(String uuid) {
       /* try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM RESUME where uuid=?")
        ) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume r = new Resume(uuid, rs.getString("full_name"));
            return r;
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
        return transactionExecute(ps->{
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString( "full_name"));
        },"SELECT * FROM RESUME where uuid=?");

    }

    @Override
    public void delete(String uuid) {
 /*       try (Connection conn=connectionFactory.getConnection();
             PreparedStatement ps=conn.prepareStatement("DELETE from RESUME where UUID=?")
        ){
            ps.setString(1,uuid);
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
        transactionExecute(p -> {
            p.setString(1, uuid);
            if (p.executeUpdate()==0){throw new NotExistStorageException(uuid);}
            return null;
        }, "DELETE from RESUME where UUID=?");
    }

    @Override
    public List<Resume> getAllSorted() {
   /*     try (Connection conn=connectionFactory.getConnection();
             PreparedStatement ps=conn.prepareStatement("SELECT * FROM RESUME ORDER BY full_name")
        ){
            ResultSet rs=ps.executeQuery();
             List<Resume> list = new ArrayList<>();
            while (rs.next()){
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return list;
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
        return transactionExecute(p -> {
            ResultSet rs = p.executeQuery();
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return list;
        }, "SELECT * FROM RESUME ORDER BY full_name");

    }

    @Override
    public int size() {
   /*     try (Connection conn=connectionFactory.getConnection();
             PreparedStatement ps=conn.prepareStatement("SELECT count (uuid) FROM RESUME")
        ){
            ResultSet rs=ps.executeQuery();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/
        return transactionExecute(ps -> {ResultSet rs= ps.executeQuery();rs.next(); return rs.getInt(1);}, "SELECT count (uuid) FROM RESUME");
    }

    @Override
    public void update(Resume resume) {
   /*     try (Connection conn=connectionFactory.getConnection();
             PreparedStatement ps=conn.prepareStatement("UPDATE  RESUME set full_name=? where uuid=?")
        ){  ps.setString(1,resume.getFullName());
            ps.setString(2,resume.getUuid());
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }*/

        transactionExecute(ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate()==0) {throw new NotExistStorageException(resume.getUuid());}
            return null;
        }, "UPDATE  RESUME set full_name=? where uuid=?");
    }

    private <T> T transactionExecute(ABlockOfCode<T> aBlockOfCode, String sqlQery) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQery)
        ) {
            return aBlockOfCode.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
