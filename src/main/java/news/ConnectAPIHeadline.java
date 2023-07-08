package news;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

public class ConnectAPIHeadline {
    public static void main(String[] args) {
        // 调用连接API的方法
        collectDataFromAPI();
    }

    // 连接API并收集数据的方法
    public static void collectDataFromAPI() {
        // 定义数据库连接参数
        String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        String apiUrl = "http://v.juhe.cn/toutiao/index";
        String apiKey = "ec8a0810381bc50e6e9f76ba290e8945";
        String type = "";
        int page = 1;
        int pageSize = 10;
        String result = sendGetRequest(apiUrl, apiKey, type, page, pageSize);

        // 解析JSON数据
        JSONObject json = new JSONObject(result);
        if (json.getInt("error_code") == 0) {
            JSONArray articles = json.getJSONObject("result").getJSONArray("data");

            try {
                // 建立数据库连接
                Connection conn = DriverManager.getConnection(dbUrl, username, password);

                // 处理收集到的数据
                for (int i = 0; i < articles.length(); i++) {
                    JSONObject article = articles.getJSONObject(i);
                    String title = article.getString("title");
                    String date = article.getString("date");
                    String authorName = article.getString("author_name");
                    String category = article.getString("category");
                    String url = article.getString("url");
                    String uniquekey = article.getString("uniquekey");
                    // 检查数据是否已存在
                    if (!isNewsExists(conn, title)) {
                        // 插入数据到headlines表
                        insertNews(conn, title, date, authorName, category, url, uniquekey);
                        System.out.println("\t成功插入数据：" + title);
                    } else {
                        System.out.println("\t已存在相同的数据，跳过插入操作：" + title);
                    }
                }

                // 关闭数据库连接
                conn.close();

                System.out.println("\t成功将API数据插入数据库！");
            } catch (SQLException e) {
                System.out.println("\t数据库连接发生错误：" + e.getMessage());
            }
        } else {
            System.out.println("\tAPI请求错误：" + json.getString("reason"));
        }
    }

    // 发送GET请求并返回响应结果
    private static String sendGetRequest(String apiUrl, String apiKey, String type, int page, int pageSize) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(apiUrl + "?key=" + apiKey + "&type=" + type + "&page=" + page + "&page_size=" + pageSize);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
            connection.disconnect();
        } catch (Exception e) {
            System.out.println("\t发送GET请求时发生错误：" + e.getMessage());
        }
        return result.toString();
    }

    // 插入数据到数据库表
    private static void insertNews(Connection conn, String title, String date, String authorName, String category, String url, String uniquekey) throws SQLException {
        String sql = "INSERT INTO headlines (title, date, author_name, category, url, uniquekey) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, title);
        stmt.setString(2, date);
        stmt.setString(3, authorName);
        stmt.setString(4, category);
        stmt.setString(5, url);
        stmt.setString(6, uniquekey);
        stmt.executeUpdate();
        stmt.close();
    }

    // 检查数据是否已存在
    private static boolean isNewsExists(Connection conn, String title) throws SQLException {
        String sql = "SELECT COUNT(*) FROM headlines WHERE title = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, title);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        rs.close();
        stmt.close();
        return count > 0;
    }
}
