package news;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteHeadline {
    // 静态方法，用于删除数据库中与 uniquekey 相关的记录
    public static boolean deleteNewsByUniqueKey(String uniqueKey) {
        // 定义数据库连接参数
        String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        try {
            // 建立数据库连接
            Connection conn = DriverManager.getConnection(dbUrl, username, password);

            // 删除记录
            String sql = "DELETE FROM headlines WHERE uniquekey = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, uniqueKey);
            int rowsAffected = stmt.executeUpdate();

            // 关闭数据库连接
            stmt.close();
            conn.close();

            // 返回删除是否成功的标志
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("\t数据库连接发生错误：" + e.getMessage());
            return false;
        }
    }
}
