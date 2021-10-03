package com.auth.sample.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserAuth {
    @NotNull(message = "Please enter the username")
    @Size(min = 5, max = 15)
    private String username;
    @NotNull(message = "Please enter the password")
    @Size(min = 8, max = 15)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
