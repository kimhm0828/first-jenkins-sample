package dev.example.model;

public class User {
    private int id;
    private String name;
    private int level;
    private String password; // SonarQube: 민감한 정보를 Plain Text로 저장하는 것 경고

    public User(int id, String name, int level, String password) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.password = password;
    }

    // Getter와 Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
