package news;

import java.sql.*;

public class FindTable {
    public static void findTableInfo(String cag) {
        // 定义数据库连接参数
        String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        try {
            // 建立数据库连接
            Connection conn = DriverManager.getConnection(dbUrl, username, password);

            // 查询表信息
            String sql = "SHOW TABLES LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cag);
            ResultSet rs = stmt.executeQuery();

            // 输出表前 10 行信息
            if (rs.next()) {
                String tableName = rs.getString(1);
                System.out.println("\t表名：" + tableName);

                String queryDataSql = "SELECT * FROM " + tableName + " LIMIT 10";
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery(queryDataSql);

                int rowNumber = 0;
                while (rs2.next()) {
                    rowNumber++;
                    System.out.println("\t行号：" + rowNumber);
                    // 输出每列的数据
                    for (int i = 1; i <= rs2.getMetaData().getColumnCount(); i++) {
                        String columnName = rs2.getMetaData().getColumnName(i);
                        String columnValue = rs2.getString(i);
                        System.out.println("\t" + columnName + ": " + columnValue);
                    }
                    System.out.println("\t------------------");
                }

                rs2.close();
                stmt2.close();
            } else {
                System.out.println("\t未找到与表名匹配的表");
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
