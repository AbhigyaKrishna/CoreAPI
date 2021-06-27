package me.Abhigya.core.database.sql;

import me.Abhigya.core.database.Database;
import me.Abhigya.core.database.DatabaseType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;

public abstract class SQLDatabase extends Database {

    public SQLDatabase(DatabaseType type) {
        super(type);
    }

    public abstract Connection getConnection() throws IOException, SQLTimeoutException, IllegalStateException, SQLException;

    public abstract int getLostConnections();
}
