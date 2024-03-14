package com.project.moneymind.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Logins.db";
    public static final int version = 5;
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


    public DBHelper(@Nullable Context context) {
        super(context, DBNAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase userdb) {
        String create_user_table = ("Create table " + usertable + "("
                + userid + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + username + " TEXT NOT NULL,"
                + password + " TEXT,"
                + fname + " TEXT,"
                + sname + " TEXT,"
                + mobno + " INTEGER ,"
                + email + " TEXT ,"
                + dob + " TEXT)");
        userdb.execSQL(create_user_table);

        String create_acc_table = ("Create table " + Acc_table + "("
                + Acc_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Acc_user_id + " INTEGER ,"
                + Acc_name + " TEXT , "
                + Acc_balance + " INTEGER ," +
                " FOREIGN KEY (" + Acc_user_id + ") REFERENCES " + usertable + "(" + userid + ") )");
        // userdb.execSQL("Create table expense_records(expense_id INTEGER PRIMARY KEY,Username INTEGER NOT NULL,Account_id INTEGER NOT NULL,Expense_category TEXT NOT NULL,amount REAL NOT NULL,date TEXT NOT NULL,description TEXT NOT NULL ,FOREIGN KEY (Account_id)REFERENCES Account(Account_id), FOREIGN KEY (Username)REFERENCES users(Username) )");
        // userdb.execSQL("Create table Budgets(budget_id INTEGER PRIMARY KEY,Username INTEGER NOT NULL,Account_id INTEGER NOT NULL,budget_name TEXT NOT NULL, total_amount REAL NOT NULL,start_date TEXT NOT NULL,end_date TEXT NOT NULL, FOREIGN KEY (Account_id)REFERENCES Account(Account_id), FOREIGN KEY (Username)REFERENCES users(Username) )");
        //  userdb.execSQL("Create table Goals(goal_id INTEGER PRIMARY KEY,Username INTEGER NOT NULL,Account_id INTEGER NOT NULL,goal_name TEXT NOT NULL,goal_amount REAL NOT NULL,target_date TEXT NOT NULL,FOREIGN KEY (Account_id)REFERENCES Account(Account_id), FOREIGN KEY (Username)REFERENCES users(Username) )");

        userdb.execSQL(create_acc_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase userdb, int old_ver, int new_ver) {
        userdb.execSQL("drop table if exists " + usertable);
        userdb.execSQL("drop table if exists " + Acc_table);

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

    public long CreateAccount(Integer userID, String accountName, Integer inbalance) {
        SQLiteDatabase userdb = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(Acc_user_id, userID);
        Values.put(Acc_name, accountName);
        Values.put(Acc_balance, inbalance);
        return userdb.insert(Acc_table, null, Values);
    }

    public int get_account_id(String acc_name, int user_id) {
        int acc_id = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        String u_id = String.valueOf(user_id);

        Cursor cursor = db.rawQuery("SELECT " + Acc_id + "  FROM " + Acc_table + " WHERE " + Acc_name + "=?" + " AND " + Acc_user_id + "=?", new String[]{acc_name, u_id});

        if (cursor != null && cursor.moveToFirst()) {

            int acc_id_Index = cursor.getColumnIndex(Acc_id);

            // Check if column indices are valid before retrieving data
            if (acc_id_Index >= 0) {
                acc_id = cursor.getInt(acc_id_Index);
            }
        }
        cursor.close();
        return acc_id;

    }

    public Integer getAccountBalance(int accountid, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String us_id = String.valueOf(userId);
        String account_ID = String.valueOf(accountid);
        Integer balance = -1; // Default value if account is not found

        Cursor cursor = db.rawQuery("SELECT " + Acc_balance + "  FROM " + Acc_table + " WHERE " + Acc_id + "=?" + " AND " + Acc_user_id + "=?", new String[]{account_ID, us_id});
        if (cursor != null && cursor.moveToFirst()) {

            int balanceindex = cursor.getColumnIndex( Acc_balance );
            if (balanceindex >= 0) {
                balance = cursor.getInt(balanceindex);
            }
        }
        cursor.close();
        return balance;
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
    }

