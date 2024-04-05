package com.project.moneymind.models;

import java.util.Date;

public class Goals {

        private String name;
        private double amount;
        private Date deadline;
        private double savedAmount;
        private int acid;
        private long id;


        // Constructor, getters, and setters
        public Goals(){}



    public Goals(String name, double amount, Date deadline, double savedAmount, int acid,long id) {
        this.name = name;
        this.amount = amount;
        this.deadline = deadline;
        this.savedAmount = savedAmount;
        this.acid = acid;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDeadline() {
        return deadline;
    }
    public void setId(long id){
            this.id=id;
    }
    public int getId(){
            return (int) id;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public double getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(double savedAmount) {
        this.savedAmount = savedAmount;
    }

    public int getAcid() {
        return acid;
    }

    public void setAcid(int acid) {
        this.acid = acid;
    }


}





