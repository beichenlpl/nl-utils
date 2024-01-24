package com.github.beichenlpl.nlutils.sql.func;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author lpl
 * @version 1.0
 * @since 2023.12.22
 */
@FunctionalInterface
public interface PrepareStatementBuilder {
    PreparedStatement build(PreparedStatement ps) throws SQLException;
}
