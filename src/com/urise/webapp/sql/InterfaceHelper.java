package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface InterfaceHelper<R> {
    R executeInner(PreparedStatement ps) throws SQLException;
}
