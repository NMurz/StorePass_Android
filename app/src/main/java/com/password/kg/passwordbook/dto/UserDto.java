package com.password.kg.passwordbook.dto;

/**
 * Created by Nurs on 28.04.2016.
 */
public class UserDto {
    private int id;
    private String fio;
    private long modifiedDate;
    private int languageId;
    private String email;

    public UserDto(int id, String fio, long modifiedDate, int languageId, String email) {
        this.id = id;
        this.fio = fio;
        this.modifiedDate = modifiedDate;
        this.languageId = languageId;
        this.email = email;
    }

    public UserDto(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
