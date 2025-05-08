package com.example.tour.entity;

import javax.persistence.*;

@Entity
@Table(name = "users") // 推荐设置表名，避免数据库关键字冲突
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    // 构造器（无参是必须的）
    public User() {
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // Getter 和 Setter（可用 IDE 快捷生成）
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
