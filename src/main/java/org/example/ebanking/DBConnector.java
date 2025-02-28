package org.example.ebanking;

import java.sql.*;

public class DBConnector {

    private static Connection con = null;
    private static final String CONNECT_STRING = "jdbc:mysql://localhost:3306/ChaseBank?user=root";

    private Connection openConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(CONNECT_STRING);
            con.setAutoCommit(false);
            System.out.println("Database connection established.");
            return con;
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("No connection to " + CONNECT_STRING);
            e.printStackTrace();
        }
        con.commit();
        return null;
    }

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                con = new DBConnector().openConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public int executeUpdate(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            int result = stmt.executeUpdate();
            conn.commit();
            return result;

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return -1;
        }
    }

    public ResultSet executeQuery(String sql, Object... params) {
        try {
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Set parameters
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            return stmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}