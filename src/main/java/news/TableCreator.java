package news;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableCreator {
    // 创建表格的函数
    public static void createTable(String tableName) {
        // 定义数据库连接参数
        String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        // 定义创建表的SQL语句
        String createTableSQL = "CREATE TABLE " + tableName + " ("
                + "no INT AUTO_INCREMENT PRIMARY KEY,"
                + "title VARCHAR(100),"
                + "ctime VARCHAR(100),"
                + "source VARCHAR(100),"
                + "description VARCHAR(1000),"
                + "url VARCHAR(100),"
                + "uniquekey VARCHAR(100)"
                + ")";

        try {
            // 建立数据库连接
            Connection conn = DriverManager.getConnection(dbUrl, username, password);

            // 创建PreparedStatement对象并执行SQL语句
            PreparedStatement stmt = conn.prepareStatement(createTableSQL);
            stmt.executeUpdate();

            // 关闭数据库连接
            stmt.close();
            conn.close();

            System.out.println("\t成功创建表：" + tableName);
        } catch (SQLException e) {
            System.out.println("\t创建表时发生错误：" + e.getMessage());
        }
    }
}
