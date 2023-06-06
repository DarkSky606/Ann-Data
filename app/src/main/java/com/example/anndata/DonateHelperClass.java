package com.example.anndata;

public class DonateHelperClass {

    String name,fItem,fType,desc,ckDate,ckTime,address,contact,uname,dp;

    public DonateHelperClass() {
    }

    public DonateHelperClass(String name, String fItem, String fType,String desc, String ckDate, String ckTime,  String address, String contact, String uname, String dp) {
        this.name = name;
        this.fItem = fItem;
        this.fType = fType;
        this.desc = desc;
        this.ckDate = ckDate;
        this.ckTime = ckTime;
        this.address = address;
        this.contact = contact;
        this.uname = uname;
        this.dp = dp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getfItem() {
        return fItem;
    }

    public void setfItem(String fItem) {
        this.fItem = fItem;
    }

    public String getfType() {
        return fType;
    }

    public void setfType(String fType) {
        this.fType = fType;
    }

    public String getCkDate() {
        return ckDate;
    }

    public void setCkDate(String ckDate) {
        this.ckDate = ckDate;
    }

    public String getCkTime() {
        return ckTime;
    }

    public void setCkTime(String ckTime) {
        this.ckTime = ckTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }
}

