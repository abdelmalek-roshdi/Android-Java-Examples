package com.example.lab2;

public class ContactData {
    private String msg;
    private String phone;

    public ContactData() {
    }

    public ContactData(String msg, String phone) {
        this.msg = msg;
        this.phone = phone;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
