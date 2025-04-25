package com.t2308e.exam.javaservletexam.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/player_evaluation";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver không tìm thấy: " + e.getMessage());
            e.printStackTrace();
        }

        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void testConnection() {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                System.out.println("Kết nối thành công!");
            }
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        testConnection();
    }
}
