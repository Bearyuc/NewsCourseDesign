package news;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NormalTableWriter {
    // 将变量值插入到normal表中
    public static void insertData(String user, String password, String phone) {
        // 定义数据库连接参数
        String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
        String username = "root";
        String sqlpassword = "123456";

        try {
            // 建立数据库连接
            Connection conn = DriverManager.getConnection(dbUrl, username, sqlpassword);

            // 执行插入操作
            String sql = "INSERT INTO normal (user, password, phone) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user);
            stmt.setString(2, password);
            stmt.setString(3, phone);
            stmt.executeUpdate();

            // 关闭数据库连接
            stmt.close();
            conn.close();
            System.out.println("\t注册成功");
        } catch (SQLException e) {
            System.out.println("\t数据库连接发生错误：" + e.getMessage());
        }
    }
}
