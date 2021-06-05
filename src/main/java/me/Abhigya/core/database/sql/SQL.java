package me.Abhigya.core.database.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL {

    private final Connection connection;

    public SQL(Connection connection) {
        this.connection = connection;
    }

    public void createTable(String table, String columns) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS "
                    + table + " (" + columns + ");");
            ps.executeUpdate();
            ps.close();
        } catch(SQLException ex) {
            // TODO
        }
    }

    public void insertData(String columns, String values, String table) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("INSERT INTO "
                    + table + "(" + columns + ") VALUES (" + values + ");");
            ps.executeUpdate();
            ps.close();
        } catch(SQLException ex) {
            // TODO
        }
    }

    public void deleteData(String table, String column, Object value) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("DELETE FROM " + table + " WHERE " + column + "=?");
            ps.setObject(1, value);
            ps.executeUpdate();
            ps.close();
        } catch(SQLException ex) {
            // TODO
        }
    }

    public void set(String table, String gate, Object gate_value, String column, Object value) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("UPDATE " + table + " SET " + column + "=? WHERE " + gate + "=?");
            ps.setObject(1, value);
            ps.setObject(2, gate_value);
            ps.executeUpdate();
            ps.close();
        } catch(SQLException ex) {
            // TODO
        }
    }

    public boolean exists(String table, String column, Object value) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("SELECT * FROM "
                    + table + " WHERE " + column + "=?");
            ps.setObject(1, value);

            ResultSet results = ps.executeQuery();
            ps.close();
            return results.next();
        } catch(SQLException ex) {
            // TODO
        }
        return false;
    }

    public String getString(String table, String column, String gate, Object gate_value) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("SELECT " + column + " FROM " + table
                    + " WHERE " + gate + "=?");
            ps.setObject(1, gate_value);

            ResultSet rs = ps.executeQuery();
            String toReturn;

            if (rs.next()) {
                toReturn = rs.getString(column);
                ps.close();
                return toReturn;
            }
        } catch (SQLException ex) {
            // TODO
        }
        return null;
    }

    public int getInt(String table, String column, String gate, Object gate_value) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("SELECT " + column + " FROM " + table
                    + " WHERE " + gate + "=?");
            ps.setObject(1, gate_value);

            ResultSet rs = ps.executeQuery();
            int toReturn;

            if (rs.next()) {
                toReturn = rs.getInt(column);
                ps.close();
                return toReturn;
            }
        } catch (SQLException ex) {
            // TODO
        }
        return 0;
    }

    public Double getDouble(String table, String column, String gate, Object gate_value) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("SELECT " + column + " FROM " + table
                    + " WHERE " + gate + "=?");
            ps.setObject(1, gate_value);

            ResultSet rs = ps.executeQuery();
            double toReturn;

            if (rs.next()) {
                toReturn = rs.getDouble(column);
                ps.close();
                return toReturn;
            }
        } catch (SQLException ex) {
            // TODO
        }
        return null;
    }

    public long getLong(String table, String column, String gate, Object gate_value) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("SELECT " + column + " FROM " + table
                    + " WHERE " + gate + "=?");
            ps.setObject(1, gate_value);

            ResultSet rs = ps.executeQuery();
            long toReturn;

            if (rs.next()) {
                toReturn = rs.getLong(column);
                ps.close();
                return toReturn;
            }
        } catch (SQLException ex) {
            // TODO
        }
        return 0;
    }

    public byte getByte(String table, String column, String gate, Object gate_value) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("SELECT " + column + " FROM " + table
                    + " WHERE " + gate + "=?");
            ps.setObject(1, gate_value);

            ResultSet rs = ps.executeQuery();
            byte toReturn;

            if (rs.next()) {
                toReturn = rs.getByte(column);
                ps.close();
                return toReturn;
            }
        } catch (SQLException ex) {
            // TODO
        }
        return 0;
    }

    public Object get(String table, String column, String gate, Object gate_value) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("SELECT " + column + " FROM " + table
                    + " WHERE " + gate + "=?");
            ps.setObject(1, gate_value);

            ResultSet rs = ps.executeQuery();
            Object toReturn;

            if (rs.next()) {
                toReturn = rs.getObject(column);
                ps.close();
                return toReturn;
            }
        } catch(SQLException ex) {
            // TODO
        }
        return null;
    }

    public Connection getConnection() {
        return connection;
    }
}
