package me.Abhigya.core.database.sql;

import me.Abhigya.core.database.Database;
import me.Abhigya.core.database.DatabaseType;

import java.io.IOException;
import java.sql.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class SQLDatabase extends Database {

    public SQLDatabase(DatabaseType type) {
        super(type);
    }

    public abstract Connection getConnection()
            throws IOException, SQLTimeoutException, IllegalStateException, SQLException;

    public abstract int getLostConnections();

    public boolean execute(String query) throws SQLException {
        try {
            PreparedStatement statement = this.getConnection().prepareStatement(query);
            boolean b = statement.execute();
            statement.close();
            return b;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean execute(PreparedStatement statement) throws SQLException {
        return statement.execute();
    }

    public CompletableFuture<Boolean> executeAsync(String query) {
        return CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return execute(query);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    return false;
                });
    }

    public ResultSet query(String query) throws SQLException {
        try {
            PreparedStatement statement = this.getConnection().prepareStatement(query);
            return statement.executeQuery();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResultSet query(PreparedStatement statement) throws SQLException {
        return statement.executeQuery();
    }

    public void query(String query, SQLConsumer<ResultSet> consumer) throws SQLException {
        ResultSet resultSet = this.query(query);

        consumer.accept(resultSet);

        if(!resultSet.isClosed())
            resultSet.close();
        if(!resultSet.getStatement().isClosed())
            resultSet.getStatement().close();
    }

    public void query(PreparedStatement statement, SQLConsumer<ResultSet> consumer)
            throws SQLException {
        ResultSet resultSet = this.query(statement);

        consumer.accept(resultSet);

        if(!resultSet.isClosed())
            resultSet.close();
        if(!resultSet.getStatement().isClosed())
            resultSet.getStatement().close();
    }

    public CompletableFuture<ResultSet> queryAsync(String query) {
        return CompletableFuture.supplyAsync(
                new Supplier<ResultSet>() {
                    @Override
                    public ResultSet get() {
                        try {
                            return SQLDatabase.this.query(query);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
    }

    public CompletableFuture<ResultSet> queryAsync(PreparedStatement statement) {
        return CompletableFuture.supplyAsync(
                new Supplier<ResultSet>() {
                    @Override
                    public ResultSet get() {
                        try {
                            return SQLDatabase.this.query(statement);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                });
    }

    public void queryAsync(String query, SQLConsumer<ResultSet> consumer) {
        CompletableFuture.runAsync(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SQLDatabase.this.query(query, consumer);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void queryAsync(PreparedStatement statement, SQLConsumer<ResultSet> consumer) {
        CompletableFuture.runAsync(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            SQLDatabase.this.query(statement, consumer);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public int update(String update) throws SQLException {
        try {
            PreparedStatement statement = this.getConnection().prepareStatement(update);
            int result = statement.executeUpdate();

            statement.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int update(PreparedStatement statement) throws SQLException {
        int result = statement.executeUpdate();

        statement.close();
        return result;
    }

    public CompletableFuture<Integer> updateAsync(String update) {
        return CompletableFuture.supplyAsync(
                new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        int results = 0;
                        try {
                            results = SQLDatabase.this.update(update);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return results;
                    }
                });
    }

    public CompletableFuture<Integer> updateAsync(PreparedStatement statement) {
        return CompletableFuture.supplyAsync(
                new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        int results = 0;
                        try {
                            results = SQLDatabase.this.update(statement);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return results;
                    }
                });
    }
}
