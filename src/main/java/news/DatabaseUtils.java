package news;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtils {
    public static List<String> getTableNames(String dbUrl, String username, String password) {
        List<String> tableNames = new ArrayList<>();

        try {
            // 建立数据库连接
            Connection conn = DriverManager.getConnection(dbUrl, username, password);

            // 获取数据库元数据
            DatabaseMetaData metaData = conn.getMetaData();

            // 获取所有表的元数据
            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});

            // 遍历表名并添加到列表中
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                if (!tableName.equalsIgnoreCase("admin") && !tableName.equalsIgnoreCase("normal")) {
                    tableNames.add(tableName);
                }
            }

            // 关闭数据库连接
            tables.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("\t数据库连接发生错误：" + e.getMessage());
        }

        return tableNames;
    }
}
