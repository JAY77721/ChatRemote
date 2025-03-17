package db;

import java.sql.*;

//可以连接数据库进一步存储聊天记录

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://127.0.0.1/ChatRemote";
    private static final String USER = "root";
    private static final String PASSWORD = "root";  // 请更新为你的数据库密码

    // 获取数据库连接（静态方法）
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("连接数据库失败。");
        }
    }


}
