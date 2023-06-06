package com.example.anndata;

public class MainModel {

    String name,fItem,fType,desc,ckDate,ckTime,address,contact,dp;

    public MainModel() {
    }

    public MainModel(String name, String fItem, String fType, String desc, String ckDate, String ckTime, String address, String contact, String dp) {
        this.name = name;
        this.fItem = fItem;
        this.fType = fType;
        this.desc = desc;
        this.ckDate = ckDate;
        this.ckTime = ckTime;
        this.address = address;
        this.contact = contact;
        this.dp = dp;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getCkDate() {
        return ckDate;
    }

    public void setCkDate(String ckDate) {
        this.ckDate = ckDate;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCkTime() {
        return ckTime;
    }

    public void setCkTime(String ckTime) {
        this.ckTime = ckTime;
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
}
