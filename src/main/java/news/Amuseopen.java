package news;

import java.awt.*;
import java.net.URI;
import java.sql.*;

public class Amuseopen {
        public static boolean openURLFromDB(String nid) {
        // 定义数据库连接参数
        String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        boolean found = false;

        try {
            // 建立数据库连接
            Connection conn = DriverManager.getConnection(dbUrl, username, password);

            // 构建查询语句
            String sql = "SELECT url FROM area WHERE uniquekey = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nid);

            // 执行查询
            ResultSet rs = stmt.executeQuery();

            // 获取结果集中的 URL 值并打开浏览器
            if (rs.next()) {
                found = true;
                String url = rs.getString("url");
                Desktop.getDesktop().browse(new URI(url));
            }

            // 关闭数据库连接
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("\t操作发生错误：" + e.getMessage());
        }

        return found;
    }
}
