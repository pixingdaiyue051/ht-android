package com.tequeno.app.login;

public class LoginDto {

    private String phone;
    private String otp;
    private String uname;
    private String password;

    public LoginDto() {
    }

    public String getPhone() {
        return phone;
    }

    public LoginDto setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getOtp() {
        return otp;
    }

    public LoginDto setOtp(String otp) {
        this.otp = otp;
        return this;
    }

    public String getUname() {
        return uname;
    }

    public LoginDto setUname(String uname) {
        this.uname = uname;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LoginDto setPassword(String password) {
        this.password = password;
        return this;
    }
}