package com.example.esso;
public class User {
    private String username;
    private String password;
    private String gender;
    private String birth_date;
    private String numberphone;
    private String tokenID;

    public String getusername() {
        return username;
    }
    public void setusername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth_date() {
        return birth_date;
    }
    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getNumberphone() {
        return numberphone;
    }
    public void setNumberphone(String numberphone) {
        this.numberphone = numberphone;
    }
    public String getTokenID() {
        return tokenID;
    }
    public void setTokenID(String tokenID) {
        this.tokenID = tokenID;
    }

}
