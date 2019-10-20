package com.urise.webapp.storage;

import com.urise.webapp.sql.ConnectionFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ABlockOfCode<T> {
    T execute(PreparedStatement ps) throws SQLException;
}
