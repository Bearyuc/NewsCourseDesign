package news;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserChecker {
     // 判断cinuser与normal表中的user是否有重复
    public static boolean isUserUnique(String cinuser) {
        // 定义数据库连接参数
        String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        try {
            // 建立数据库连接
            Connection conn = DriverManager.getConnection(dbUrl, username, password);

            // 查询normal表中的user列
            String sql = "SELECT user FROM normal";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // 检查是否存在重复的用户
            while (rs.next()) {
                String user = rs.getString("user");
                if (user.equals(cinuser)) {
                    // 存在重复用户
                    rs.close();
                    stmt.close();
                    conn.close();
                    return false;
                }
            }

            // 关闭数据库连接
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("\t数据库连接发生错误：" + e.getMessage());
        }

        // 没有重复用户
        return true;
    }
}
