package com.example.anndata;

public class UserHelperClass {

    String name,uname,email,phone,pass,dp;

    public UserHelperClass() {
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public UserHelperClass(String name, String uname, String email, String phone, String pass, String dp) {
        this.name = name;
        this.uname = uname;
        this.email = email;
        this.phone = phone;
        this.pass = pass;
        this.dp = dp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
