package com.urise.webapp.sql;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlHelper {

    public <R> R helper(ConnectionFactory connectionFactory, String sql, InterfaceHelper<R> interfaceHelper) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
           return interfaceHelper.innerExecute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
