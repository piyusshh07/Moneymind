package com.project.moneymind.models;

public class Category {
    private  String Categoryname;
    private int categoryimage;

    public Category(String categoryname, int categoryimage) {
        Categoryname = categoryname;
        this.categoryimage = categoryimage;
    }

    public String getCategoryname() {

        return Categoryname;
    }

    public void setCategoryname(String categoryname) {
        Categoryname = categoryname;
    }



    public int getCategoryimage() {
        return categoryimage;
    }
    public void setCategoryimage(int categoryimage) {
        this.categoryimage = categoryimage;
    }
}
