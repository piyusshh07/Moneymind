package com.project.moneymind.database;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.project.moneymind.models.Goals;
import com.project.moneymind.models.transaction;
import com.project.moneymind.utils.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Logins.db";
    public static final int version = 12;
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

    //Expenses variables
    public static final String Expense_table="expense_table";
    public static final String Expense_id="expense_id";
    public static final String Expense_account_id="expense_accont_id";

    public static final String Expense_type="expense_type";
    public static final String Expense_date="expense_date";
    public static final String Expense_amount="expense_amount";
    public static final String Expense_category="expense_category";

    public static final String Expense_note="expense_note";
    public static final  String Expense_account="expense_account";
    public static final String Expense_Acc_user_id="expense_acc_user_id";

    //goals Varibales
    public static final String Goal_table="Goal_Table";

    public static final String Goal_id="Goal_ID";
    public static final String Goal_name="Goal_Name";

    public static final String Goal_amount="Goal_Amount";

    public static final String Goal_date="Goal_Date";
    public static final String Goal_acc_id="Goal_ACC_ID";
    public static final String Goal_saved_amount="Goal_Saved_Amount";

    public static final String Goal_User_id="Goal_User_Id";





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
                + Acc_name + " TEXT NOT NULL, " +
                " FOREIGN KEY (" + Acc_user_id + ") REFERENCES " + usertable + "(" + userid + ") )");
        userdb.execSQL(create_acc_table);
        String create_record_table = ("CREATE TABLE " + Expense_table + "("
                + Expense_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Expense_account_id + " INTEGER, "
                + Expense_Acc_user_id +" INTEGER, "
                + Expense_account + " TEXT NOT NULL,"
                + Expense_type + " TEXT NOT NULL, "
                + Expense_date + " TEXT, "
                + Expense_amount + " DOUBLE, "
                + Expense_category + " TEXT NOT NULL, "
                + Expense_note + " TEXT, "
                + "FOREIGN KEY (" + Expense_Acc_user_id + ") REFERENCES " + Acc_table + "(" + Acc_user_id + "), "
                + "FOREIGN KEY (" + Expense_account_id + ") REFERENCES " + Acc_table + "(" + Acc_id + "))");
        userdb.execSQL(create_record_table);
        String create_Goal_table = ("CREATE TABLE "+ Goal_table + "("
        + Goal_id +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +Goal_name +" TEXT NOT NULL, "
                + Goal_amount + " DOUBLE NOT NULL, "
                + Goal_date + " TEXT NOT NULL, "
                + Goal_acc_id +" INTEGER,"
                + Goal_saved_amount +" DOUBLE, "
                + Goal_User_id +" INTEGER, "
                + "FOREIGN KEY (" + Goal_User_id + ") REFERENCES " + Acc_table + "(" + Acc_user_id + "), "
                + "FOREIGN KEY (" + Goal_acc_id + ") REFERENCES " + Acc_table + "(" + Acc_id + "))");
                userdb.execSQL(create_Goal_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase userdb, int old_ver, int new_ver) {
        userdb.execSQL("drop table if exists " + usertable);
        userdb.execSQL("drop table if exists " + Acc_table);
        userdb.execSQL("drop table if exists "+ Expense_table);
        userdb.execSQL("drop table if exists "+ Goal_table);

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
    public long CreateAccount(Integer userID, String accountName) {
        SQLiteDatabase userdb = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put(Acc_user_id, userID);
        Values.put(Acc_name, accountName);
        return userdb.insert(Acc_table, null, Values);
    }
    public void insertGoalData(Goals goal , int accId, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String formattedDate = helper.formatdate(goal.getDeadline());
        ContentValues values = new ContentValues();
        values.put(Goal_name, goal.getName());
        values.put(Goal_amount, goal.getAmount());
        values.put(Goal_date, formattedDate);
        values.put(Goal_acc_id, accId);
        values.put(Goal_User_id,userId);
        values.put(Goal_saved_amount, goal.getSavedAmount());
        db.insert(Goal_table, null, values);
        db.close();
    }
    public void updateSavedAmount(int goalId, double savedAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Goal_saved_amount, savedAmount);

        db.update(Goal_table, values, Goal_id + " = ?", new String[]{String.valueOf(goalId)});
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
    public long addtransaction(transaction Transaction, int acc_id, int user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String formattedDate = helper.formatdate(Transaction.getDate());
        values.put(Expense_account_id, acc_id);
        values.put(Expense_Acc_user_id, user_id);
        values.put(Expense_type, Transaction.getType());
        values.put(Expense_category, Transaction.getCategory());
        values.put(Expense_account, Transaction.getAccount());
        values.put(Expense_amount, Transaction.getAmount());
        values.put(Expense_note, Transaction.getNote());
        values.put(Expense_date, formattedDate);

        return db.insert(Expense_table, null, values);
    }




    public void deleteTransaction(transaction Transaction, int acc_id, int user_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long transactionId = Transaction.getId();
        String[] whereArgs = new String[]{String.valueOf(transactionId)};
        String sql = "DELETE FROM " + Expense_table + " WHERE " + Expense_id + " = ? AND " + Expense_account_id + " = ? AND " + Expense_Acc_user_id + " = ?";
        db.execSQL(sql, new String[]{String.valueOf(transactionId), String.valueOf(acc_id), String.valueOf(user_id)});
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


    public Boolean delete_account(String acc_name, int usr_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        int rowdeleted = db.delete(Acc_table, Acc_user_id + "=? And " + Acc_name + "=? ", new String[]{String.valueOf(usr_id), acc_name});

        if (rowdeleted > 0) {
            return true;
        }
        return false;

    }

    public Boolean edit_account(int acc_id, int usr_id, String newacc_name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Acc_name, newacc_name);
        int rowedited = db.update(Acc_table, values, Acc_id + " = ? And " + Acc_user_id + "= ?", new String[]{String.valueOf(acc_id), String.valueOf(usr_id)});
        if (rowedited > 0) {
            return true;
        }
        return false;


    }

    public ArrayList<transaction> getTransactionDetailsForAccount(int acc_id, int userid, Calendar calendar) {
        String formattedDate = helper.formatdate(calendar.getTime());
        ArrayList<transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String rawQuery = "SELECT * FROM " + Expense_table + " WHERE " + Expense_date + " = ? AND " + Expense_account_id + " = ? AND " + Expense_Acc_user_id + " = ?";
        String[] selectionArgs = {formattedDate, String.valueOf(acc_id), String.valueOf(userid)};
        Cursor cursor = db.rawQuery(rawQuery, selectionArgs);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = -1;
                int acid=-1;
                int user_id=-1;
                String type = null;
                String category = null;
                String account = null;
                double amount = 0.0;
                String note = null;
                Date date = null;

                int idIndex = cursor.getColumnIndex(Expense_id);
                if (idIndex >= 0) {
                    id = cursor.getLong(idIndex);
                }
                int AcidIndex=cursor.getColumnIndex(Expense_account_id);
                if(AcidIndex>=0){
                    acid=cursor.getInt(AcidIndex);

                }
                int useridIndex = cursor.getColumnIndex(Expense_Acc_user_id);
                if (useridIndex >= 0) {
                    user_id = cursor.getInt(useridIndex);
                }


                int typeIndex = cursor.getColumnIndex(Expense_type);
                if (typeIndex >= 0) {
                    type = cursor.getString(typeIndex);
                }

                int categoryIndex = cursor.getColumnIndex(Expense_category);
                if (categoryIndex >= 0) {
                    category = cursor.getString(categoryIndex);
                }

                int accountIndex = cursor.getColumnIndex(Expense_account);
                if (accountIndex >= 0) {
                    account = cursor.getString(accountIndex);
                }

                int amountIndex = cursor.getColumnIndex(Expense_amount);
                if (amountIndex >= 0) {
                    amount = cursor.getDouble(amountIndex);
                }

                int noteIndex = cursor.getColumnIndex(Expense_note);
                if (noteIndex >= 0) {
                    note = cursor.getString(noteIndex);
                }

                int dateIndex = cursor.getColumnIndex(Expense_date);
                if (dateIndex >= 0) {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM,yy", Locale.getDefault());
                        date = dateFormat.parse(cursor.getString(dateIndex));

                    } catch (ParseException e) {
                        e.printStackTrace();
                        // Handle parsing exception
                    }


                }

                transaction Transaction = new transaction(acid,user_id,type, category, note, account, date, amount, id );

                transactions.add(Transaction);
                // Parse the cursor and create transactions as before
            } while (cursor.moveToNext());
            cursor.close();
        }
        return transactions;
    }

    public ArrayList<transaction> getTransactionDetailsOfDayChart(Calendar calendar, String Type, int acc_id, int userid) {

        String formattedDate= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            formattedDate = helper.formatdate(calendar.getTime());
        }
        ArrayList<transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String rawQuery = "SELECT * FROM " + Expense_table +" WHERE "+ Expense_date +" = ? AND " + Expense_type + " = ? AND " + Expense_account_id + " = ? AND " + Expense_Acc_user_id + " = ?";
        String[] selectionArgs = {formattedDate, Type, String.valueOf(acc_id), String.valueOf(userid)};
        Cursor cursor = db.rawQuery(rawQuery, selectionArgs);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = -1;
                int acid=-1;
                int user_id=-1;
                String type = null;
                String category = null;
                String account = null;
                double amount = 0.0;
                String note = null;
                Date date = null;

                int idIndex = cursor.getColumnIndex(Expense_id);
                if (idIndex >= 0) {
                    id = cursor.getLong(idIndex);
                }
                int AcidIndex=cursor.getColumnIndex(Expense_account_id);
                if(AcidIndex>=0){
                    acid=cursor.getInt(AcidIndex);

                }
                int useridIndex = cursor.getColumnIndex(Expense_Acc_user_id);
                if (useridIndex >= 0) {
                    user_id = cursor.getInt(useridIndex);
                }

                int typeIndex = cursor.getColumnIndex(Expense_type);
                if (typeIndex >= 0) {
                    type = cursor.getString(typeIndex);
                }

                int categoryIndex = cursor.getColumnIndex(Expense_category);
                if (categoryIndex >= 0) {
                    category = cursor.getString(categoryIndex);
                }

                int accountIndex = cursor.getColumnIndex(Expense_account);
                if (accountIndex >= 0) {
                    account = cursor.getString(accountIndex);
                }

                int amountIndex = cursor.getColumnIndex(Expense_amount);
                if (amountIndex >= 0) {
                    amount = cursor.getDouble(amountIndex);
                }

                int noteIndex = cursor.getColumnIndex(Expense_note);
                if (noteIndex >= 0) {
                    note = cursor.getString(noteIndex);
                }

                int dateIndex = cursor.getColumnIndex(Expense_date);
                if (dateIndex >= 0) {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM,yy", Locale.getDefault());
                        date = dateFormat.parse(cursor.getString(dateIndex));

                    } catch (ParseException e) {
                        e.printStackTrace();
                        // Handle parsing exception
                    }


                }

                transaction Transaction = new transaction(acid,user_id,type, category, note, account, date, amount, id);

                transactions.add(Transaction);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return transactions;
    }

    public ArrayList<transaction> getTransactionDetailsOfMonths(Calendar calendar, int acc_id, int userid) {
        ArrayList<transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        // Assuming tableName and dateColumnName are String variables representing the table name and date column respectively

        // Use parameterized query to avoid string concatenation
        String query = "SELECT * FROM " + Expense_table +
                " WHERE " + Expense_date + " = ? AND " + Expense_account_id + " = ? AND " + Expense_Acc_user_id + " = ?";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());

        Cursor cursor = db.rawQuery(query, new String[]{formattedDate, String.valueOf(acc_id), String.valueOf(userid)});

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(Expense_id));
                    @SuppressLint("Range") int acid = cursor.getInt(cursor.getColumnIndex(Expense_account_id));
                    @SuppressLint("Range") int user_id = cursor.getInt(cursor.getColumnIndex(Expense_Acc_user_id));
                    @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(Expense_type));
                    @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex(Expense_category));
                    @SuppressLint("Range") String account = cursor.getString(cursor.getColumnIndex(Expense_account));
                    @SuppressLint("Range") double amount = cursor.getDouble(cursor.getColumnIndex(Expense_amount));
                    @SuppressLint("Range") String note = cursor.getString(cursor.getColumnIndex(Expense_note));
                    @SuppressLint("Range") Date date = dateFormat.parse(cursor.getString(cursor.getColumnIndex(Expense_date)));

                    transaction Transactions = new transaction(acid, user_id, type, category, account, note, date, amount,id);
                    transactions.add(Transactions);
                } while (cursor.moveToNext());
            }
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle parsing exception
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return transactions;
    }

    public ArrayList<transaction> getTransactionDetailsOfMonthsChart(Calendar calendar, String Type, int acc_id, int userid) {

        String formattedDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            formattedDate = helper.format_date_month(calendar.getTime());
        }
        ArrayList<transaction> transactions = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Expense_table +
                " WHERE " + Expense_date + " LIKE ? AND " + Expense_type + " = ? AND " + Expense_account_id + " = ? AND " + Expense_Acc_user_id + " = ?";

        String[] selectionArgs = {"%" + formattedDate, Type, String.valueOf(acc_id), String.valueOf(userid)};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = -1;
                int acid = -1;
                int user_id = -1;
                String type = null;
                String category = null;
                String account = null;
                double amount = 0.0;
                String note = null;
                Date date = null;

                int idIndex = cursor.getColumnIndex(Expense_id);
                if (idIndex >= 0) {
                    id = cursor.getLong(idIndex);
                }
                int AcidIndex = cursor.getColumnIndex(Expense_account_id);
                if (AcidIndex >= 0) {
                    acid = cursor.getInt(AcidIndex);

                }
                int useridIndex = cursor.getColumnIndex(Expense_Acc_user_id);
                if (useridIndex >= 0) {
                    user_id = cursor.getInt(useridIndex);
                }

                int typeIndex = cursor.getColumnIndex(Expense_type);
                if (typeIndex >= 0) {
                    type = cursor.getString(typeIndex);
                }

                int categoryIndex = cursor.getColumnIndex(Expense_category);
                if (categoryIndex >= 0) {
                    category = cursor.getString(categoryIndex);
                }

                int accountIndex = cursor.getColumnIndex(Expense_account);
                if (accountIndex >= 0) {
                    account = cursor.getString(accountIndex);
                }

                int amountIndex = cursor.getColumnIndex(Expense_amount);
                if (amountIndex >= 0) {
                    amount = cursor.getDouble(amountIndex);
                }

                int noteIndex = cursor.getColumnIndex(Expense_note);
                if (noteIndex >= 0) {
                    note = cursor.getString(noteIndex);
                }

                int dateIndex = cursor.getColumnIndex(Expense_date);
                if (dateIndex >= 0) {
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM,yy", Locale.getDefault());
                        date = dateFormat.parse(cursor.getString(dateIndex));

                    } catch (ParseException e) {
                        e.printStackTrace();
                        // Handle parsing exception
                    }


                }

                transaction Transaction = new transaction(acid, user_id, type, category, note, account, date, amount, id);

                transactions.add(Transaction);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return transactions;
    }


    public double getTotalIncomeForDate(Calendar calendar, int acc_id, int user_id) {
        double totalIncome = 0;
        String formattedDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            formattedDate = helper.formatdate(calendar.getTime());
        }

        // Create your SQLite query to fetch total income for the given date
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(" + Expense_amount + ") AS total_income FROM " + Expense_table+ " WHERE " + Expense_date + " = ? AND " + Expense_type + " = ? AND " + Expense_account_id + " = ? AND " + Expense_Acc_user_id + " = ?";
        String[] selectionArgs = {formattedDate, "Income", String.valueOf(acc_id), String.valueOf(user_id)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Retrieve the sum of income from the cursor
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("total_income");
            if (columnIndex >= 0) {
                totalIncome = cursor.getDouble(columnIndex);
            }
            cursor.close();
        }

        // Close the database connection
        db.close();

        return totalIncome;
    }


    public double getTotalIncomeForMonth(Calendar calendar, int acc_id, int user_id) {
        double totalIncome = 0;
        String formattedDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            formattedDate = helper.format_date_month(calendar.getTime());
        }

        // Create your SQLite query to fetch total income for the given date
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT SUM(" + Expense_amount + ") AS total_income FROM " +
                Expense_table + " WHERE " +
                Expense_date + " LIKE ? AND " +
                Expense_type + " = ? AND " +
                Expense_account_id + " = ? AND " +
                Expense_Acc_user_id + " = ?";

        String[] selectionArgs = {"%" + formattedDate, "Income", String.valueOf(acc_id), String.valueOf(user_id)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Retrieve the sum of income from the cursor
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("total_income");
            if (columnIndex >= 0) {
                totalIncome = cursor.getDouble(columnIndex);
            }
            cursor.close();
        }

        // Close the database connection
        db.close();

        return totalIncome;
    }

    public double getTotalExpenseForDate(Calendar calendar, int acc_id, int user_id) {
        double totalExpense = 0;
        String formattedDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            formattedDate = helper.formatdate(calendar.getTime());
        }

        // Create your SQLite query to fetch total income for the given date
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(" + Expense_amount + ") AS total_expense FROM " + Expense_table +
                " WHERE " + Expense_date + " = ? AND " + Expense_type + " = ? AND " +
                Expense_account_id + " = ? AND " + Expense_Acc_user_id + " = ?";
        String[] selectionArgs = {formattedDate, "Expense", String.valueOf(acc_id), String.valueOf(user_id)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Retrieve the sum of income from the cursor
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("total_expense");
            if (columnIndex >= 0) {
                totalExpense = cursor.getDouble(columnIndex);
            }
            cursor.close();
        }

        // Close the database connection
        db.close();

        return totalExpense;
    }


    public double getTotalExpenseForMonth(Calendar calendar, int acc_id, int user_id) {
        double totalExpense = 0;
        String formattedDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            formattedDate = helper.format_date_month(calendar.getTime());
        }

        // Create your SQLite query to fetch total income for the given date
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT SUM(" + Expense_amount + ") AS total_expense FROM " +
                Expense_table + " WHERE " +
                Expense_date + " LIKE ? AND " +
                Expense_type + " = ? AND " +
                Expense_account_id + " = ? AND " + Expense_Acc_user_id + " = ?";

        String[] selectionArgs = {"% " + formattedDate, "Expense", String.valueOf(acc_id), String.valueOf(user_id)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Retrieve the sum of income from the cursor
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("total_expense");
            if (columnIndex >= 0) {
                totalExpense = cursor.getDouble(columnIndex);
            }
            cursor.close();
        }

        // Close the database connection
        db.close();

        return totalExpense;
    }

    public double getTotalAccountForDate(Calendar calendar, int acc_id, int user_id) {
        double totalAccount = 0;
        String formattedDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            formattedDate = helper.formatdate(calendar.getTime());
        }

        // Create your SQLite query to fetch total income for the given date
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(" + Expense_amount + ") AS total_expense FROM " + Expense_table+ " WHERE " + Expense_date + " = ? AND " + Expense_account_id + " = ? AND " + Expense_Acc_user_id + " = ?";
        String[] selectionArgs = {formattedDate, String.valueOf(acc_id), String.valueOf(user_id)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Retrieve the sum of income from the cursor
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("total_expense");
            if (columnIndex >= 0) {
                totalAccount = cursor.getDouble(columnIndex);
            }
            cursor.close();
        }

        // Close the database connection
        db.close();

        return totalAccount;
    }

    public double getTotalAccountForMonth(Calendar calendar, int acc_id, int user_id) {
        double totalAccount = 0;
        String formattedDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            formattedDate = helper.format_date_month(calendar.getTime());
        }

        // Create your SQLite query to fetch total income for the given date
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT SUM(" + Expense_amount + ") AS total_expense FROM " +
                Expense_table + " WHERE " +
                Expense_date + " LIKE ? AND " + Expense_account_id + " = ? AND " + Expense_Acc_user_id + " = ?";


        String[] selectionArgs = {"% " + formattedDate, String.valueOf(acc_id), String.valueOf(user_id)};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Retrieve the sum of income from the cursor
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("total_expense");
            if (columnIndex >= 0) {
                totalAccount = cursor.getDouble(columnIndex);
            }
            cursor.close();
        }

        // Close the database connection
        db.close();

        return totalAccount;
    }

    public double getTotalAccountBalance(int acc_id, int user_id) {
        double totalBalanceAccount = 0;

        // Create your SQLite query to fetch total income for the given date
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT SUM(" + Expense_amount + ") AS total_expense FROM " +
                Expense_table + " WHERE " +
                Expense_account_id + " = ? AND " + Expense_Acc_user_id + " = ?";

        String[] selectionArgs = {String.valueOf(acc_id), String.valueOf(user_id)};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        // Retrieve the sum of income from the cursor
        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("total_expense");
            if (columnIndex >= 0) {
                totalBalanceAccount = cursor.getDouble(columnIndex);
            }
            cursor.close();
        }

        // Change the sign to make it positive if it's a negative value
        totalBalanceAccount = Math.abs(totalBalanceAccount);

        // Close the database connection
        db.close();

        return totalBalanceAccount;
    }
    
}

