package news;

import java.sql.*;

public class Changepassword {
public static boolean updatePassword(String name, String newPassword) {
        // 定义数据库连接参数
        String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        boolean userExists = false;

        try {
            // 建立数据库连接
            Connection conn = DriverManager.getConnection(dbUrl, username, password);

            // 查询用户信息
            String selectSql = "SELECT * FROM normal WHERE user = ?";
            PreparedStatement selectStmt = conn.prepareStatement(selectSql);
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();

            // 检查用户是否存在
            if (rs.next()) {
                String userId = rs.getString("user");

                // 更新密码
                String updateSql = "UPDATE normal SET password = ? WHERE user = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, newPassword);
                updateStmt.setString(2, userId);
                updateStmt.executeUpdate();
                updateStmt.close();

                userExists = true;
            }

            // 关闭数据库连接
            rs.close();
            selectStmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("\t数据库连接发生错误：" + e.getMessage());
        }

        return userExists;
    }
}
