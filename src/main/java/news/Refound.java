package news;

import java.sql.*;


public class Refound {
     public static boolean checkUser(String name, String no) {
        // 定义数据库连接参数
        String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        boolean userExists = false;

        try {
            // 建立数据库连接
            Connection conn = DriverManager.getConnection(dbUrl, username, password);

            // 查询用户信息
            String sql = "SELECT * FROM normal WHERE user = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            // 检查用户是否存在
            if (rs.next()) {
                String phone = rs.getString("phone");
                if (phone.equals(no)) {
                    userExists = true;
                }
            }

            // 关闭数据库连接
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("数据库连接发生错误：" + e.getMessage());
        }

        return userExists;
    }
}
