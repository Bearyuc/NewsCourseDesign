package news;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Amusepast {
     public static void main() {
        // 定义数据库连接参数
        String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        // 获取查询日期
        LocalDate queryDate = LocalDate.now(); // 当前日期作为查询日期

        try {
            // 建立数据库连接
            Connection conn = DriverManager.getConnection(dbUrl, username, password);

            // 将查询日期转换为字符串
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String queryDateStr = queryDate.format(formatter);

            // 查询日期一致的数据
            String sql = "SELECT * FROM amuse WHERE Date(ctime) != ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, queryDateStr);
            ResultSet rs = stmt.executeQuery();

            // 输出查询结果
            while (rs.next()) {
                String title = rs.getString("title");
                String date = rs.getString("ctime");
                String source = rs.getString("source");
                String description = rs.getString("description");
                String url = rs.getString("url");
                String id = rs.getString("uniquekey");

                System.out.println("\t标题：" + title);
                System.out.println("\t日期：" + date);
                System.out.println("\t来源：" + source);
                System.out.println("\t描述：" + description);
                System.out.println("\tURL：" + url);
                System.out.println("\t编号：" + id);
                System.out.println("\t-----------------------");
            }

            // 关闭数据库连接
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("\t数据库连接发生错误：" + e.getMessage());
        }
    }
}
