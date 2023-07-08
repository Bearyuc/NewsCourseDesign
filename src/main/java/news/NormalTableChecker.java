package news;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NormalTableChecker {
    // 检查normal表中的用户数据
    public static boolean checkUser(String name, String no) {
        // 定义数据库连接参数
        String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        try {
            // 建立数据库连接
            Connection conn = DriverManager.getConnection(dbUrl, username, password);

            // 查询用户数据
            String sql = "SELECT * FROM normal WHERE user = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            // 检查查询结果
            if (rs.next()) {
                String dbPassword = rs.getString("password");

                // 判断password是否与no一致
                if (no.equals(dbPassword)) {
                    // 关闭数据库连接
                    rs.close();
                    stmt.close();
                    conn.close();
                    return true;
                }
            }

            // 关闭数据库连接
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("\t数据库连接发生错误：" + e.getMessage());
        }

        return false;
    }
}
