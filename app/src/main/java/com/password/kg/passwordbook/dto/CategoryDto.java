package com.password.kg.passwordbook.dto;

import java.util.List;

/**
 * Created by Nurs on 28.04.2016.
 */
public class CategoryDto {
    private int id;
    private String category;
    private long modifiedDate;
    private List<PasswordDto> passwords;

    public CategoryDto(int id, String category, long modifiedDate, List<PasswordDto> passwords) {
        this.id = id;
        this.category = category;
        this.modifiedDate = modifiedDate;
        this.passwords = passwords;
    }

    public CategoryDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public List<PasswordDto> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<PasswordDto> passwords) {
        this.passwords = passwords;
    }
}
