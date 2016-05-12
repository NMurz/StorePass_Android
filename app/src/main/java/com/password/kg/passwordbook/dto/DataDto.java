package com.password.kg.passwordbook.dto;

import java.util.List;

/**
 * Created by Nurs on 28.04.2016.
 */
public class DataDto {
    private UserDto user;
    private List<CategoryDto> data;

    public DataDto(UserDto user, List<CategoryDto> data) {
        this.user = user;
        this.data = data;
    }

    public DataDto() {
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<CategoryDto> getData() {
        return data;
    }

    public void setData(List<CategoryDto> data) {
        this.data = data;
    }
}
