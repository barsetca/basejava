package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <R> R executeSql(String sql, InterfaceHelper<R> interfaceHelper) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return interfaceHelper.executeInner(ps);
        } catch (SQLException e) {
            String exist = e.getSQLState();
            if (exist.equals("23505")) {
                throw new ExistStorageException(null);
            }
            throw new StorageException(e);
        }
    }
}
