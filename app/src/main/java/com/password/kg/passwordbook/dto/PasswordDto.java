package com.password.kg.passwordbook.dto;

/**
 * Created by Nurs on 28.04.2016.
 */
public class PasswordDto {
    private int id;
    private String title;
    private String link;
    private String descriptionText;
    private String passwordText;
    private long modifiedDate;

    public PasswordDto(int id, String title, String link, String descriptionText, String passwordText, long modifiedDate) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.descriptionText = descriptionText;
        this.passwordText = passwordText;
        this.modifiedDate = modifiedDate;
    }

    public PasswordDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescriptionText() {
        return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
    }

    public String getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(String passwordText) {
        this.passwordText = passwordText;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
