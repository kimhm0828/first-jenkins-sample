package dev.example.service;
import dev.example.model.User;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    // SonarQube: 비밀번호 등 민감 정보가 하드코딩된 변수명 경고
    private final String DB_URL = "jdbc:postgresql://localhost:5432/sonarqube";
    private final String DB_USER = "sonar";
    private final String DB_PASS = "sonar"; // SonarQube: 하드코딩된 인증 정보 경고

    /**
     * [문제 1: SQL Injection 취약점]
     * 사용자 입력을 검증 없이 쿼리에 직접 사용합니다.
     */
    public List<User> searchUsers(String queryName) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            stmt = conn.createStatement();

            // 위험! 사용자 입력(queryName)을 쿼리에 직접 삽입
            String sql = "SELECT * FROM users WHERE name LIKE '%" + queryName + "%'";
            rs = stmt.executeQuery(sql); // SonarQube: 'Potential SQL Injection' 경고

            while (rs.next()) {
                // User 객체 생성 로직 (생략)
            }
            return users;

        } catch (SQLException e) {
            e.printStackTrace();
            return users;
        } finally {
            // [문제 2: 자원 누수(Resource Leak)]
            // 자원을 닫는 로직이 누락되어 Connection, Statement, ResultSet이 닫히지 않습니다.
            // SonarQube: "Close this 'Connection' in a 'finally' block..." 경고
        }
    }

    /**
     * [문제 3: 인지적 복잡도가 높은 긴 메서드 & 매직 넘버]
     * 복잡한 로직이 하나의 메서드에 몰려있고, 하드코딩된 숫자가 많습니다.
     */
    public String processUserPromotion(User user, int score, boolean isManager) {
        String status = "";

        // SonarQube: "Refactor this method to reduce its cognitive complexity." 경고
        if (user.getLevel() < 5) {
            if (score > 80) { // SonarQube: '80'은 매직 넘버
                user.setLevel(user.getLevel() + 1);
                status = "Promoted by score";
            } else if (score < 50 && !isManager) { // SonarQube: '50'은 매직 넘버
                user.setLevel(Math.max(1, user.getLevel() - 1));
                status = "Demoted due to low score";
            } else {
                status = "Level maintained";
            }
        } else if (user.getLevel() >= 5) {
            if (isManager && score >= 95) { // SonarQube: '95'는 매직 넘버
                status = "Manager promotion pending";
            } else {
                status = "Highest level maintained";
            }
        } else {
            // [문제 4: 미사용 코드 블록(Dead Code)]
            // user.getLevel()이 정수라면 이 else 블록은 절대 실행되지 않습니다.
            // SonarQube: "Remove this unreachable code." 경고
            status = "Error status";
        }

        // [문제 5: 하드코딩된 문자열 비교]
        if (status.equals("Manager promotion pending")) { // 하드코딩된 문자열 상수를 사용하지 않음
            return "SUCCESS: Manager status updated.";
        }

        return "SUCCESS: User processed. Status: " + status;
    }
}
