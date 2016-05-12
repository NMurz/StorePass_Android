package com.password.kg.passwordbook.model;

/**
 * Created by Nurs on 16.02.2016.
 */
public class ChangePassword {
    private String OldPassword;
    private String NewPassword;
    private String ConfirmPassword;

    public ChangePassword(String oldPassword, String newPassword, String confirmPassword) {
        OldPassword = oldPassword;
        NewPassword = newPassword;
        ConfirmPassword = confirmPassword;
    }

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        OldPassword = oldPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String newPassword) {
        NewPassword = newPassword;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        ConfirmPassword = confirmPassword;
    }
}
