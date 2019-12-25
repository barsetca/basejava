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

    public <R> R executeSqlHelper(String sql, InterfaceHelper<R> interfaceHelper) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return interfaceHelper.executeInner(ps);
        } catch (SQLException e) {
            throw ExeptionUtil.convertExeption(e);
        }
    }
    public <R> R transactionalExecute (SqlTransaction<R> executor){
        try(Connection conn = connectionFactory.getConnection()) {
            try{
                conn.setAutoCommit(false);
                R res = executor.executeTransaction(conn);
                conn.commit();
                return res;
            }catch (SQLException e) {
                conn.rollback();
                throw ExeptionUtil.convertExeption(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        }
    }

