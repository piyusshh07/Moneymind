package com.project.moneymind.ViewModel;



import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.project.moneymind.database.DBHelper;
import com.project.moneymind.models.transaction;
import com.project.moneymind.utils.constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MainViewModel extends AndroidViewModel {




    public MutableLiveData<ArrayList<transaction>> transaction = new MutableLiveData<>();
    public MutableLiveData<ArrayList<transaction>> categorytransaction = new MutableLiveData<>();
    public MutableLiveData<Double> totalIncome = new MutableLiveData<>();
    public MutableLiveData<Double> totalExpense = new MutableLiveData<>();
    public MutableLiveData<Double> totalAccount = new MutableLiveData<>();

    public MutableLiveData<Double> totalAccountBalance = new MutableLiveData<>();

    Calendar calendar;
    DBHelper db;


    public MainViewModel(@NonNull Application application) {
        super(application);
        db = new DBHelper(this.getApplication());
    }
    transaction transactions=new transaction();

    public void getTransactionsChart(Calendar calendar, String Type) {
        this.calendar = calendar;
        ArrayList<transaction> newTransactions;

        if (constants.SELECTED_TAB_STATS == constants.DAILY) {
            newTransactions = db.getTransactionDetailsOfDayChart(calendar, Type,transactions.getAcid(),transactions.getUser_id());
        } else if (constants.SELECTED_TAB_STATS == constants.MONTHLY) {
            newTransactions = db.getTransactionDetailsOfMonthsChart(calendar, Type ,transactions.getAcid(),transactions.getUser_id());
        } else {
            newTransactions = new ArrayList<>(); // Handle other cases if needed
        }

        categorytransaction.setValue(newTransactions);
    }

    public void getTransactions(Calendar calendar) {
        this.calendar = calendar;
        ArrayList<transaction> newTransactions;
        double income = 0;
        double expense = 0;
        double total = 0;


        if (constants.SELECTED_TAB == constants.DAILY) {
            newTransactions = db.getTransactionDetailsForAccount(transactions.getAcid(),transactions.getUser_id(),calendar);
            income = db.getTotalIncomeForDate(calendar,transactions.getAcid(),transactions.getUser_id());
            expense = db.getTotalExpenseForDate(calendar,transactions.getAcid(),transactions.getUser_id());
            total = db.getTotalAccountForDate(calendar,transactions.getAcid(),transactions.getUser_id());
        } else if (constants.SELECTED_TAB == constants.MONTHLY) {
            newTransactions = db.getTransactionDetailsOfMonths(calendar,transactions.getAcid(),transactions.getUser_id());
            income = db.getTotalIncomeForMonth(calendar,transactions.getAcid(),transactions.getUser_id());
            expense = db.getTotalExpenseForMonth(calendar,transactions.getAcid(),transactions.getUser_id());
            total = db.getTotalAccountForMonth(calendar,transactions.getAcid(),transactions.getUser_id());
        } else {
            newTransactions = new ArrayList<>(); // Handle other cases if needed
        }
        totalAccount.setValue(total);
        totalExpense.setValue(expense);
        totalIncome.setValue(income);
        transaction.setValue(newTransactions);

    }

    // Other methods...


    public void addTransactions(transaction Transaction) {
        db.addtransaction(Transaction,transactions.getAcid(),transactions.getUser_id());
        updateAccountBalance();
    }

    public void deleteTransaction(transaction Transaction) {

        db.deleteTransaction(Transaction,transactions.getAcid(),transactions.getUser_id());
        getTransactions(calendar);
        updateAccountBalance();


    }
    private void updateAccountBalance() {
        totalAccountBalance.setValue(db.getTotalAccountBalance(transactions.getAcid(), transactions.getUser_id()));
    }
    public void setbalance(){
        double currentbal=db.getTotalAccountBalance(transactions.getAcid(),transactions.getUser_id());
        totalAccountBalance.setValue(currentbal);
    }
    public LiveData<Double> getbalance(){
        return totalAccountBalance;

    }
}


