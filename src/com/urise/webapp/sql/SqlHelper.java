package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            try{
            conn.setAutoCommit(false);//после выпонения не будет выполняться автоматический commit
            T res =executor.execute(conn);
            conn.commit();
            return res;
            }
            catch (SQLException e){
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
    public <T> T transactionExecute(ABlockOfCode<T> aBlockOfCode, String sqlQuery) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlQuery)
        ) {
            return aBlockOfCode.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
