package me.Abhigya.core.database.sql.hikaricp;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang.Validate;

import java.util.Properties;

/**
 * Class for creating Hikari client.
 */
public class HikariClientBuilder {

    private HikariConfig config;
    private boolean reconnect;

    public HikariClientBuilder(String jdbcUrl, String username, String password, boolean reconnect) {
        this.config = new HikariConfig();

        Validate.notNull(jdbcUrl, "JDBC url cannot be null!");
        Validate.notNull(username, "Username cannot be null!");
        Validate.notNull(password, "Password cannot be null!");

        this.config.setJdbcUrl(jdbcUrl);
        this.config.setUsername(username);
        this.config.setPassword(password);
        this.reconnect = reconnect;
    }

    public HikariClientBuilder(Class<?> dataSourceClass, String username, String password, boolean reconnect) {
        this.config = new HikariConfig();

        Validate.notNull(dataSourceClass, "DataSource class cannot be null!");
        Validate.notNull(username, "Username cannot be null!");
        Validate.notNull(password, "Password cannot be null!");

        this.config.setDataSourceClassName(dataSourceClass.getName());
        this.config.setUsername(username);
        this.config.setPassword(password);
        this.reconnect = reconnect;
    }

    public HikariClientBuilder(String jdbcUrl, String username, String password) {
        this(jdbcUrl, username, password, true);
    }

    public HikariClientBuilder(Class<?> dataSourceClass, String username, String password) {
        this(dataSourceClass, username, password, true);
    }

    public HikariClientBuilder addProperty(String key, String value) {
        this.config.addDataSourceProperty(key, value);
        return this;
    }

    public HikariClientBuilder setProperty(Properties property) {
        this.config.setDataSourceProperties(property);
        return this;
    }

    public HikariClientBuilder setAutoCommit(boolean value) {
        this.config.setAutoCommit(value);
        return this;
    }

    public HikariClientBuilder setConnectionTimeout(long timeout) {
        this.config.setConnectionTimeout(timeout);
        return this;
    }

    public HikariClientBuilder setIdleTimeout(long timeout) {
        this.config.setIdleTimeout(timeout);
        return this;
    }

    public HikariClientBuilder setKeepAliveTime(long time) {
        this.config.setKeepaliveTime(time);
        return this;
    }

    public HikariClientBuilder setMaxLifeTime(long time) {
        this.config.setMaxLifetime(time);
        return this;
    }

    public HikariClientBuilder setConnectionTestQuery(String query) {
        this.config.setConnectionTestQuery(query);
        return this;
    }

    public HikariClientBuilder setMinimumIdle(int time) {
        this.config.setMinimumIdle(time);
        return this;
    }

    public HikariClientBuilder setMaximumPoolSize(int size) {
        this.config.setMaximumPoolSize(size);
        return this;
    }

    public HikariClientBuilder setMetricRegistry(Object registry) {
        this.config.setMetricRegistry(registry);
        return this;
    }

    public HikariClientBuilder setHealthCheckRegistry(Object registry) {
        this.config.setHealthCheckRegistry(registry);
        return this;
    }

    public HikariClientBuilder setPoolName(String name) {
        this.config.setPoolName(name);
        return this;
    }

    public HikariCP build() {
        return new HikariCP(config, new HikariDataSource(config), reconnect);
    }

    public HikariConfig getConfig() {
        return config;
    }

    public HikariClientBuilder setConfig(HikariConfig config) {
        this.config = config;
        return this;
    }

    public boolean isReconnect() {
        return reconnect;
    }

    public HikariClientBuilder setReconnect(boolean reconnect) {
        this.reconnect = reconnect;
        return this;
    }
}
