package com.project.moneymind.database;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.health.connect.datatypes.Record;
import android.util.Log;

import androidx.annotation.Nullable;

import com.project.moneymind.models.transaction;
import com.project.moneymind.views.activties.home_page;
import com.project.moneymind.views.activties.registration_page;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Logins.db";
    public static final int version = 10;
    //user login
    public static final String userid = "Userid";
    public static final String username = "Username";
    public static final String usertable = "users";
    public static final String password = "Password";
    public static final String fname = "Fname";
    public static final String sname = "Sname";
    public static final String dob = "Dob";
    public static final String mobno = "Mobno";
    public static final String email = "Email";

    //account details
    public static final String Acc_table = "accounts";

    public static final String Acc_user_id = "Userid";
    public static final String Acc_id = "account_id ";
    public static final String Acc_name = "account_name";
    public static final String Acc_balance = "account_balance";
    public static final String Expense_table="expense_table";
    public static final String Expense_id="expense_id";
    public static final String Expense_account_id="expense_accont_id";

    public static final String Expense_type="expense_type";
    public static final String Expense_date="expense_date";
    public static final String Expense_amount="expense_amount";
    public static final String Expense_category="expense_category";

    public static final String Expense_note="expense_note";
    public static final  String Expense_account="expense_account";



    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase userdb) {
        String create_user_table = ("CREATE TABLE " + usertable + "("
                + userid + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + username + " TEXT NOT NULL,"
                + password + " TEXT NOT NULL,"
                + fname + " TEXT NOT NULL,"
                + sname + " TEXT NOT NULL,"
                + email + " TEXT NOT NULL,"
                + mobno + " TEXT NOT NULL,"
                + dob + " TEXT NOT NULL)");
        userdb.execSQL(create_user_table);


        String create_acc_table = ("Create table " + Acc_table + "("
                + Acc_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Acc_user_id + " INTEGER ,"
                + Acc_name + " TEXT NOT NULL, "
                + Acc_balance + " INTEGER ," +
                " FOREIGN KEY (" + Acc_user_id + ") REFERENCES " + usertable + "(" + userid + ") )");
        userdb.execSQL(create_acc_table);
        String create_record_table = ("CREATE TABLE " + Expense_table + "("
                + Expense_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Expense_account_id + " INTEGER, "
                + Expense_account + " TEXT NOT NULL,"
                + Expense_type + " TEXT NOT NULL, "
                + Expense_date + " TEXT, "
                + Expense_amount + " DOUBLE, "
                + Expense_category + " TEXT NOT NULL, "
                + Expense_note + " TEXT, "
                + "FOREIGN KEY (" + Expense_account_id + ") REFERENCES " + Acc_table + "(" + Acc_id + "))");
        userdb.execSQL(create_record_table);

        // userdb.execSQL("Create table expense_records(expense_id INTEGER PRIMARY KEY,Username INTEGER NOT NULL,Account_id INTEGER NOT NULL,Expense_category TEXT NOT NULL,amount REAL NOT NULL,date TEXT NOT NULL,description TEXT NOT NULL ,FOREIGN KEY (Account_id)REFERENCES Account(Account_id), FOREIGN KEY (Username)REFERENCES users(Username) )");
        // userdb.execSQL("Create table Budgets(budget_id INTEGER PRIMARY KEY,Username INTEGER NOT NULL,Account_id INTEGER NOT NULL,budget_name TEXT NOT NULL, total_amount REAL NOT NULL,start_date TEXT NOT NULL,end_date TEXT NOT NULL, FOREIGN KEY (Account_id)REFERENCES Account(Account_id), FOREIGN KEY (Username)REFERENCES users(Username) )");
        //  userdb.execSQL("Create table Goals(goal_id INTEGER PRIMARY KEY,Username INTEGER NOT NULL,Account_id INTEGER NOT NULL,goal_name TEXT NOT NULL,goal_amount REAL NOT NULL,target_date TEXT NOT NULL,FOREIGN KEY (Account_id)REFERENCES Account(Account_id), FOREIGN KEY (Username)REFERENCES users(Username) )");


    }

    @Override
    public void onUpgrade(SQLiteDatabase userdb, int old_ver, int new_ver) {
        userdb.execSQL("drop table if exists " + usertable);
        userdb.execSQL("drop table if exists " + Acc_table);
        userdb.execSQL("drop table if exists "+ Expense_table);

        onCreate(userdb);
    }

    public boolean insertData(String Fname, String Sname, String Email, String Mobno, String Dob, String Username, String Password) {
        SQLiteDatabase userdb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(username, Username);
        contentValues.put(password, Password);
        contentValues.put(fname, Fname);
        contentValues.put(sname, Sname);
        contentValues.put(mobno, Mobno);
        contentValues.put(email, Email);
        contentValues.put(dob, Dob);

        long result = userdb.insert(usertable, null, contentValues);
        if (result == -1) return false;
        else
            return true;

    }
    public long CreateAccount(Integer userID, String accountName, Integer inbalance) {
        SQLiteDatabase userdb = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(Acc_user_id, userID);
        Values.put(Acc_name, accountName);
        Values.put(Acc_balance, inbalance);
        return userdb.insert(Acc_table, null, Values);
    }

    public void insertExpense( int accid, String type, String account ,String date, int amount, String category, String note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Expense_account_id,accid);
        values.put(Expense_type, type);
        values.put(Expense_account, account);
        values.put(Expense_date, date);
        values.put(Expense_amount, amount);
        values.put(Expense_category, category);
        values.put(Expense_note, note);
        db.insert(Expense_table, null, values);
        db.close();
    }



    public boolean checkusername(String Username) {
        SQLiteDatabase userdb = this.getWritableDatabase();
        Cursor cursor = userdb.rawQuery("select * from " + usertable + " where " + username + " = ?", new String[]{Username});
        if (cursor.getCount() > 0) {
            return true;
        } else
            cursor.close();
        return false;
    }

    public boolean checkusernamepassword(String Username, String Password) {
        SQLiteDatabase userdb = this.getWritableDatabase();
        Cursor cursor = userdb.rawQuery("select * from " + usertable + " where " + username + " = ? and " + password + " = ? ", new String[]{Username, Password});
        if (cursor.getCount() > 0) {
            return true;
        } else
            cursor.close();
        return false;
    }



    public int getuserid(String F_name) {
        int user_id = -1;
        SQLiteDatabase userdb = this.getWritableDatabase();
        Cursor cursor = userdb.rawQuery("Select " + userid + " from " + usertable + " Where " + username + "=?", new String[]{F_name});
        if (cursor != null && cursor.moveToFirst()) {
            int userid_index = cursor.getColumnIndex(userid);
            if (userid_index >= 0) {
                user_id = cursor.getInt(userid_index);
            }
        }
        cursor.close();
        return user_id;

    }

    public int getaccuserid(int accid) {
        int accuser_id = -1;
        String acc_id=String.valueOf(accid);
        SQLiteDatabase userdb = this.getWritableDatabase();
        Cursor cursor = userdb.rawQuery("Select " + Acc_user_id + " from " + Acc_table + " Where " + Acc_id + "=?", new String[]{acc_id});
        if (cursor != null && cursor.moveToFirst()) {
            int accuserid_index = cursor.getColumnIndex(Acc_user_id);
            if (accuserid_index >= 0) {
                accuser_id = cursor.getInt(accuserid_index);
            }
        }
        cursor.close();
        return accuser_id;

    }


    public ArrayList<String> fetch_accnames(int user_id) {
        ArrayList<String> acc_names = new ArrayList<>();
        SQLiteDatabase userdb = this.getWritableDatabase();

        String[] selectionargs = {String.valueOf(user_id)};
        Cursor cursor = userdb.rawQuery(" select " + Acc_name + " from " + Acc_table + " where " + Acc_user_id + "=?", selectionargs);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int accname = cursor.getColumnIndex(Acc_name);
                if (accname >= 0) {
                    String accountname = cursor.getString(accname);
                    acc_names.add(accountname);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        return acc_names;
    }
    public ArrayList<transaction> fetch_transactions() {
        ArrayList<transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Select all transactions
        Cursor cursor = db.rawQuery("SELECT * FROM " + Expense_table, null);

        // Loop through the cursor and add each transaction to the list
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(Expense_type));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex(Expense_category));
                @SuppressLint("Range") String account=cursor.getString(cursor.getColumnIndex(Expense_account));
                @SuppressLint("Range") String note = cursor.getString(cursor.getColumnIndex(Expense_note));
                @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex(Expense_date));
                @SuppressLint("Range") Double amount = cursor.getDouble(cursor.getColumnIndex(Expense_amount));
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(Expense_id));

                Date exdate = null;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                try {
                    exdate = dateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                transactions.add(new transaction(type, category, account,note,exdate,amount,id));
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return transactions;
    }



    public int getAccountId(String accName, int userId) {
        int accId = -1;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + Acc_id + " FROM " + Acc_table + " WHERE " + Acc_name + "=? AND " + Acc_user_id + "=?", new String[]{accName, String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            accId = cursor.getInt(0); // Assuming Acc_id is at index 0
        }
        if (cursor != null) {
            cursor.close();
        }
        return accId;
    }



    public double getAccountBalanceById(int accId, int userId) {
        double accBalance = -1;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + Acc_balance + " FROM " + Acc_table + " WHERE " + Acc_id + "=? AND " + Acc_user_id + "=?", new String[]{String.valueOf(accId), String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            accBalance = cursor.getDouble(0); // Assuming Acc_balance is at index 0
        }

        if (cursor != null) {
            cursor.close();
        }

        return accBalance;
    }

    @SuppressLint("Range")
    public String getfname(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String Fname = null;

        // Assuming fname, usertable, and userid are column names or variables storing column names
        Cursor cursor = db.rawQuery("SELECT " + fname + " FROM " + usertable + " WHERE " + userid + "=?", new String[]{String.valueOf(userId)});

        if (cursor != null && cursor.moveToFirst()) {
            Fname = cursor.getString(cursor.getColumnIndex(fname));
        }

        // Always close the cursor after using it
        if (cursor != null) {
            cursor.close();
        }

        Log.d("db", "fname = " + Fname);
        return Fname;
    }




    public boolean checkAccountname(String Accountname, int userid) {
        SQLiteDatabase userdb = this.getWritableDatabase();
        String uid=String.valueOf(userid);
        Cursor cursor = userdb.rawQuery("SELECT * FROM " + Acc_table + " WHERE " + Acc_name + "=? And " + Acc_user_id + "=? ", new String[]{Accountname, uid});
        if (cursor.getCount() > 0) {
            return true;
        } else
            cursor.close();
        return false;
    }

    public boolean updateAccountBalance(int accId, int userId, double newBalance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Acc_balance, newBalance);

        int rowsAffected = db.update(Acc_table, values, Acc_id + "=? AND " + Acc_user_id + "=?", new String[]{String.valueOf(accId), String.valueOf(userId)});

        return rowsAffected > 0;

    }

}

