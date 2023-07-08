package news;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDatabaseChecker {

    private Connection getConnection() throws SQLException {
        String dbUrl = "jdbc:mysql://localhost:3306/news?serverTimezone=UTC";
        String dbUser = "root";
        String dbPassword = "123456";
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    // 判断user1是否在admin表的user列中出现，并判断密码是否匹配
    public int checkUser(String user1, String password1) {
        int result = 0; // 默认为0，表示用户不存在或密码不匹配
        try {
            Connection conn = getConnection();
            String sql = "SELECT * FROM admin WHERE user = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user1);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String passwordFromDb = rs.getString("password");
                if (password1.equals(passwordFromDb)) {
                    result = 1; // 用户存在且密码匹配
                }
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
