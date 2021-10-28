package me.Abhigya.core.database.sql;

import java.sql.*;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

@Deprecated
public class SQL {

    private final Connection connection;

    public SQL(Connection connection) {
        this.connection = connection;
    }

    public void createTable(String table, String... columns) throws SQLException {
        String col =
                Arrays.stream(columns)
                        .map(
                                new Function<String, String>() {
                                    @Override
                                    public String apply(String str) {
                                        if (!(str.equalsIgnoreCase(columns[columns.length - 1])))
                                            return str + ", ";
                                        else return str;
                                    }
                                })
                        .collect(Collectors.joining())
                        .trim();

        PreparedStatement ps =
                connection.prepareStatement(
                        "CREATE TABLE IF NOT EXISTS " + table + " (" + col + ");");
        ps.executeUpdate();
        ps.close();
    }

    public void insertData(String columns, String values, String table) throws SQLException {
        PreparedStatement ps =
                connection.prepareStatement(
                        "INSERT INTO " + table + " (" + columns + ") VALUES (" + values + ");");
        ps.executeUpdate();
        ps.close();
    }

    public void deleteData(String table, String column, Object value) throws SQLException {
        PreparedStatement ps =
                connection.prepareStatement("DELETE FROM " + table + " WHERE " + column + "=?;");
        ps.setObject(1, value);
        ps.executeUpdate();
        ps.close();
    }

    public void set(String table, String gate, Object gate_value, String column, Object value)
            throws SQLException {
        PreparedStatement ps =
                connection.prepareStatement(
                        "UPDATE " + table + " SET " + column + "=? WHERE " + gate + "=?;");
        ps.setObject(1, value);
        ps.setObject(2, gate_value);
        ps.executeUpdate();
        ps.close();
    }

    public boolean exists(String table, String column, Object value) throws SQLException {
        PreparedStatement ps =
                connection.prepareStatement("SELECT * FROM " + table + " WHERE " + column + "=?;");
        ps.setObject(1, value);

        ResultSet results = ps.executeQuery();
        boolean b = results.next();
        results.close();
        return b;
    }

    public ResultSet executeQuery(String statement) throws SQLException {
        return this.executeQuery(connection.prepareStatement(statement));
    }

    public ResultSet executeQuery(PreparedStatement statement) throws SQLException {
        return statement.executeQuery();
    }

    public int executeUpdate(String statement) throws SQLException {
        return this.executeUpdate(connection.prepareStatement(statement));
    }

    public int executeUpdate(PreparedStatement statement) throws SQLException {
        return statement.executeUpdate();
    }

    public String getString(String table, String column, String gate, Object gate_value)
            throws SQLException {
        PreparedStatement ps =
                connection.prepareStatement(
                        "SELECT " + column + " FROM " + table + " WHERE " + gate + "=?;");
        ps.setObject(1, gate_value);

        ResultSet rs = ps.executeQuery();
        String toReturn;

        if (rs.next()) {
            toReturn = rs.getString(column);
            ps.close();
            return toReturn;
        }

        return null;
    }

    public int getInt(String table, String column, String gate, Object gate_value)
            throws SQLException {
        PreparedStatement ps =
                connection.prepareStatement(
                        "SELECT " + column + " FROM " + table + " WHERE " + gate + "=?;");
        ps.setObject(1, gate_value);

        ResultSet rs = ps.executeQuery();
        int toReturn;

        if (rs.next()) {
            toReturn = rs.getInt(column);
            ps.close();
            return toReturn;
        }
        return 0;
    }

    public Double getDouble(String table, String column, String gate, Object gate_value)
            throws SQLException {
        PreparedStatement ps =
                connection.prepareStatement(
                        "SELECT " + column + " FROM " + table + " WHERE " + gate + "=?;");
        ps.setObject(1, gate_value);

        ResultSet rs = ps.executeQuery();
        double toReturn;

        if (rs.next()) {
            toReturn = rs.getDouble(column);
            ps.close();
            return toReturn;
        }
        return null;
    }

    public long getLong(String table, String column, String gate, Object gate_value)
            throws SQLException {
        PreparedStatement ps =
                connection.prepareStatement(
                        "SELECT " + column + " FROM " + table + " WHERE " + gate + "=?;");
        ps.setObject(1, gate_value);

        ResultSet rs = ps.executeQuery();
        long toReturn;

        if (rs.next()) {
            toReturn = rs.getLong(column);
            ps.close();
            return toReturn;
        }
        return 0;
    }

    public byte getByte(String table, String column, String gate, Object gate_value)
            throws SQLException {
        PreparedStatement ps =
                connection.prepareStatement(
                        "SELECT " + column + " FROM " + table + " WHERE " + gate + "=?;");
        ps.setObject(1, gate_value);

        ResultSet rs = ps.executeQuery();
        byte toReturn;

        if (rs.next()) {
            toReturn = rs.getByte(column);
            ps.close();
            return toReturn;
        }
        return 0;
    }

    public boolean getBoolean(String table, String column, String gate, Object gate_value)
            throws SQLException {
        PreparedStatement ps =
                connection.prepareStatement(
                        "SELECT " + column + " FROM " + table + " WHERE " + gate + "=?;");
        ps.setObject(1, gate_value);

        ResultSet rs = ps.executeQuery();
        boolean toReturn;

        if (rs.next()) {
            toReturn = rs.getBoolean(column);
            ps.close();
            return toReturn;
        }
        return false;
    }

    public Array getArray(String table, String column, String gate, Object gate_value)
            throws SQLException {
        PreparedStatement ps =
                connection.prepareStatement(
                        "SELECT " + column + " FROM " + table + " WHERE " + gate + "=?;");
        ps.setObject(1, gate_value);

        ResultSet rs = ps.executeQuery();
        Array toReturn;

        if (rs.next()) {
            toReturn = rs.getArray(column);
            ps.close();
            return toReturn;
        }
        return null;
    }

    public Object get(String table, String column, String gate, Object gate_value)
            throws SQLException {
        PreparedStatement ps =
                connection.prepareStatement(
                        "SELECT " + column + " FROM " + table + " WHERE " + gate + "=?;");
        ps.setObject(1, gate_value);

        ResultSet rs = ps.executeQuery();
        Object toReturn;

        if (rs.next()) {
            toReturn = rs.getObject(column);
            ps.close();
            return toReturn;
        }
        return null;
    }

    public Connection getConnection() {
        return connection;
    }
}
