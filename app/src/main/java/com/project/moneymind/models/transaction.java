package com.project.moneymind.models;

import java.util.Date;

public class transaction   {
    private String type,category,account,note;
    private Date date;
    private double amount;
    private long id;

    public transaction(String type, String category, String account, String note, Date date, double amount, long id) {
        this.type = type;
        this.category = category;
        this.account = account;
        this.note = note;
        this.date = date;
        this.amount = amount;
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getAccount() {
        return account;
    }

    public String getNote() {
        return note;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public long getId() {
        return id;
    }
}
