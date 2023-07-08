package news;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DownloadArea {
    public static boolean saveHeadlineData(String nid) {
        // 定义数据库连接参数
        String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        try {
            // 建立数据库连接
            Connection conn = DriverManager.getConnection(dbUrl, username, password);

            // 查询与uniquekey匹配的行数据
            String sql = "SELECT * FROM area WHERE uniquekey = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nid);
            ResultSet rs = stmt.executeQuery();

            // 创建保存文件的文件夹（如果不存在）
            String folderName = "download";
            Path folderPath = Paths.get(folderName);
            if (!Files.exists(folderPath)) {
                Files.createDirectories(folderPath);
            }

            // 判断是否有匹配的记录
            boolean found = rs.next();

            // 如果有匹配的记录，则保存数据到本地文件
            if (found) {
                String title = rs.getString("title");
                String ctime = rs.getString("ctime");
                String source = rs.getString("source");
                String description = rs.getString("description");
                String url = rs.getString("url");

                // 保存数据到txt文件
                String filename = folderName + "/" + nid + ".txt";
                try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                    writer.println("标题：" + title);
                    writer.println("发布时间：" + ctime);
                    writer.println("来源：" + source);
                    writer.println("描述：" + description);
                    writer.println("URL：" + url);
                } catch (IOException e) {
                    System.out.println("\t保存数据到文件时发生错误：" + e.getMessage());
                }

                System.out.println("\t成功保存数据到文件：" + filename);
            }

            // 关闭数据库连接
            rs.close();
            stmt.close();
            conn.close();

            return found;
        } catch (SQLException e) {
            System.out.println("\t数据库连接发生错误：" + e.getMessage());
            return false; // 返回false表示发生数据库连接错误
        } catch (IOException e) {
            System.out.println("\t创建文件夹时发生错误：" + e.getMessage());
            return false; // 返回false表示发生创建文件夹错误
        }
    }
}
