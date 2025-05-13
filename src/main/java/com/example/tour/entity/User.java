package com.example.tour.entity;

import java.io.Serializable;

public class User implements Serializable {
    private Long id;
    private String username;
    private String email;


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
