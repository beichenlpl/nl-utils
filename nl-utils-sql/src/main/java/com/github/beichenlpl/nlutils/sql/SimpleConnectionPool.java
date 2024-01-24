package com.github.beichenlpl.nlutils.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

/**
 * @author lpl
 * @version 1.0
 * @since 2023.12.22
 */
public class SimpleConnectionPool {
    private DataSource dataSource;

    private Integer minConnectSize;

    private Integer maxConnectSize;

    private Long connectTimeout;

    private final List<SimpleConnection> pool = new Vector<>();

    protected static class SimpleConnection {
        private final Connection connection;

        private final Long timestamp;

        private Boolean isUsable;

        public SimpleConnection(Connection connection) {
            this.connection = connection;
            this.timestamp = System.currentTimeMillis();
            this.isUsable = true;
        }

        public void setUsable(Boolean usable) {
            isUsable = usable;
        }

        public Boolean getUsable() {
            return isUsable;
        }

        public Connection getConnection() {
            return connection;
        }

        public Long getTimestamp() {
            return timestamp;
        }
    }


    public SimpleConnectionPool(DataSource dataSource) {
        this.dataSource = dataSource;
        this.minConnectSize = 3;
        this.maxConnectSize = 6;
        this.connectTimeout = 10000L;
        initialization();
    }

    public SimpleConnectionPool(DataSource dataSource, Integer minConnectSize, Integer maxConnectSize) {
        this.dataSource = dataSource;
        this.minConnectSize = minConnectSize;
        this.maxConnectSize = maxConnectSize;
        this.connectTimeout = 1000L;
        initialization();
    }

    public SimpleConnectionPool(DataSource dataSource, Integer minConnectSize, Integer maxConnectSize, Long connectTimeout) {
        this.dataSource = dataSource;
        this.minConnectSize = minConnectSize;
        this.maxConnectSize = maxConnectSize;
        this.connectTimeout = connectTimeout;
        initialization();
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Integer getMinConnectSize() {
        return minConnectSize;
    }

    public void setMinConnectSize(Integer minConnectSize) {
        this.minConnectSize = minConnectSize;
    }

    public Integer getMaxConnectSize() {
        return maxConnectSize;
    }

    public void setMaxConnectSize(Integer maxConnectSize) {
        this.maxConnectSize = maxConnectSize;
    }

    public Long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    protected SimpleConnection getConnection() throws SQLException, ClassNotFoundException {
        long current = System.currentTimeMillis();
        for (int i = 0; i < pool.size(); i++) {
            SimpleConnection connection = pool.get(i);
            if (connection != null && current - connection.getTimestamp() > connectTimeout) {
                connection.getConnection().close();
                pool.remove(i);
                i--;
            }
        }

        if (pool.size() < minConnectSize) {
            for (int i = 0; i < minConnectSize - pool.size(); i++) {
                pool.add(createConnection());
            }
        }

        SimpleConnection connection = null;
        for (SimpleConnection simpleConnection : pool) {
            if (simpleConnection.getUsable()) {
                connection = simpleConnection;
                break;
            }
        }

        if (Objects.nonNull(connection)) {
            connection.setUsable(false);
            return connection;
        }

        if (pool.size() == maxConnectSize) {
            throw new IllegalStateException("No available connection!");
        } else {
            connection = createConnection();
            connection.setUsable(false);
            pool.add(connection);
            return connection;
        }
    }

    protected synchronized void releaseSource(PreparedStatement ps, SimpleConnection connection) {
        if (Objects.nonNull(ps)) {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }

        connection.setUsable(true);
    }

    private void initialization() {
        try {
            for (int i = 0; i < maxConnectSize; i++) {
                pool.add(createConnection());
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error initializing connection pool!", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    private SimpleConnection createConnection() throws SQLException, ClassNotFoundException {
        Class.forName(dataSource.getDriver());
        return new SimpleConnection(DriverManager.getConnection(dataSource.getUrl(), dataSource.getUser(), dataSource.getPassword()));
    }
}

