package com.project.moneymind.views.activties;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.moneymind.R;
import com.project.moneymind.adapters.transactions_adapter;
import com.project.moneymind.database.DBHelper;
import com.project.moneymind.databinding.ActivityHistoryPageBinding;
import com.project.moneymind.models.transaction;
import com.project.moneymind.utils.constants;

import java.util.ArrayList;
import java.util.Date;

public class history_page extends AppCompatActivity {
ActivityHistoryPageBinding binding;
   RecyclerView transactionslist;
   DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityHistoryPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        constants.setcategories();
        db=new DBHelper(this);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
     int   acc_id = sharedPreferences.getInt("account_id", -1);
     int   user_id = sharedPreferences.getInt("user_id", -1);


        ArrayList<transaction> transactions = db.fetch_transactions(acc_id,user_id); // Fetch transactions from the database
        transactions_adapter adapter = new transactions_adapter(this, transactions);
        binding.transactionList.setLayoutManager(new LinearLayoutManager(this));
        binding.transactionList.setAdapter(adapter);
        Log.d("transaction", String.valueOf(transactions));
    }



    }