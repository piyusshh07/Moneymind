package com.project.moneymind.views.activties;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.moneymind.R;
import com.project.moneymind.adapters.transactions_adapter;
import com.project.moneymind.databinding.ActivityHistoryPageBinding;
import com.project.moneymind.models.transaction;
import com.project.moneymind.utils.constants;

import java.util.ArrayList;
import java.util.Date;

public class history_page extends AppCompatActivity {
ActivityHistoryPageBinding binding;
   RecyclerView transactionslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityHistoryPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        constants.setcategories();

        ArrayList<transaction> transactions=new ArrayList<>();
        transactions.add(new transaction("Income","Vehicle","Cash","note",new Date(),5000,2));

        transactions_adapter adapter=new transactions_adapter(this,transactions);
        binding.transactionList.setLayoutManager(new LinearLayoutManager(this));
        binding.transactionList.setAdapter(adapter);


    }
    }