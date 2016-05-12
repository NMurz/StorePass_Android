package com.password.kg.passwordbook.model;

/**
 * Created by Nurs on 21.01.2016.
 */
public class SignUpUser {
    private String email;
    private String password;
    private String confirmPassword;
    private int languageId;

    public SignUpUser(String email, String password, String confirmPassword, int languageId) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.languageId = languageId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }
}
