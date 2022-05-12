package com.tequeno.myapp;

public class LoginDto {

    private String phone;
    private String otp;
    private String uname;
    private String pwd;

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

    public String getPwd() {
        return pwd;
    }

    public LoginDto setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }
}