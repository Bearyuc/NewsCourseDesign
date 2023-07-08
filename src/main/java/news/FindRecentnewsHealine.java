package news;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FindRecentnewsHealine {
        public static void main() {
        // 定义数据库连接参数
        String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        // 获取当前日期并计算查询日期的三天前日期
        LocalDate currentDate = LocalDate.now();
        LocalDate queryDate = currentDate.minusDays(3);

        // 将日期转换为字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String queryDateStr = queryDate.format(formatter);

        try {
            // 建立数据库连接
            Connection conn = DriverManager.getConnection(dbUrl, username, password);

            // 查询距离查询日期三天内的数据
            String sql = "SELECT * FROM headlines WHERE date >= ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, queryDateStr);
            ResultSet rs = stmt.executeQuery();
            System.out.println("\t--------头条新闻--------");
            // 输出查询结果
            while (rs.next()) {
                String title = rs.getString("title");
                String date = rs.getString("date");
                String resouce = rs.getString("author_name");
                String id = rs.getString("uniquekey");
                // 其他需要输出的字段

                System.out.println("\t标题：" + title);
                System.out.println("\t日期：" + date);
                System.out.println("\t来源：" + resouce);
                System.out.println("\t编号：" + id);
                // 输出其他字段的值
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
