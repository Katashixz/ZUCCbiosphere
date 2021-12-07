package com.example.zuccbiosphere.dto;

public class userInfoDto {
    public String Openid = "";
    public String Name = "";
    public String Password = "";
    public String Phone = "";
    public String Gender = "";
    public String AvatarUrl = "";

    public String getOpenid() {
        return Openid;
    }

    public void setOpenid(String openid) {
        this.Openid = openid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getAvatarUrl() {
        return AvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        AvatarUrl = avatarUrl;
    }
}
