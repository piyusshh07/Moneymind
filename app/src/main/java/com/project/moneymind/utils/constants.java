package com.project.moneymind.utils;

import com.project.moneymind.R;
import com.project.moneymind.models.Category;

import java.util.ArrayList;

public class constants {

    public static  String Income="Income";
    public static  String Expense="Expense";
    public static ArrayList<Category> categories;
    public static void setcategories(){
        categories=new ArrayList<>();
        categories.add(new Category("Foods & drinks", R.drawable.restaurant_cat));
        categories.add(new Category("Shopping",R.drawable.shopping_cat));
        categories.add(new Category("Housing",R.drawable.house_cat));
        categories.add(new Category("Groceries",R.drawable.groceries_cat));
        categories.add(new Category("Transportation",R.drawable.transport_cat));
        categories.add(new Category("Vehicle",R.drawable.vehicle_cat));
        categories.add(new Category("Life & entertainment",R.drawable.entertainment_cat));
        categories.add(new Category("Communication ,PC",R.drawable.pc_cat));
        categories.add(new Category("Financial expenses",R.drawable.expense_cat));
        categories.add(new Category("Investments",R.drawable.investment_cat));
        categories.add(new Category("Income",R.drawable.income_cat));
        categories.add(new Category("Other",R.drawable.menu));
    }
    public static Category getcategorydetails(String categoryName){
        for (Category cat:categories){
            if(cat.getCategoryname().equals(categoryName)){
                return cat;
            }
        }
        return null;
    }
}
