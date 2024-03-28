package com.project.moneymind.views.activties;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.project.moneymind.R;
import com.project.moneymind.ViewModel.MainViewModel;
import com.project.moneymind.adapters.ViewPager_adpater_budGoal;
import com.project.moneymind.adapters.transactions_adapter;
import com.project.moneymind.adapters.viepager_adapter_forlist;
import com.project.moneymind.database.DBHelper;
import com.project.moneymind.databinding.ActivityHistoryPageBinding;
import com.project.moneymind.models.transaction;
import com.project.moneymind.utils.constants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class history_page extends AppCompatActivity {
    ActivityHistoryPageBinding binding;
    RecyclerView transactionslist;
    DBHelper db;
    Calendar calendar;
    public MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHistoryPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        constants.setcategories();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        db = new DBHelper(this);
        calendar = Calendar.getInstance();
        updateDate();
        viepager_adapter_forlist adapter=new viepager_adapter_forlist(getSupportFragmentManager()) {
        };



        binding.viewpagerforlist.setAdapter(adapter);
        binding.tabsforlist.setupWithViewPager(binding.viewpagerforlist);

        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        int acc_id = sharedPreferences.getInt("account_id", -1);
        int user_id = sharedPreferences.getInt("user_id", -1);

        binding.nextdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (constants.SELECTED_TAB == constants.DAILY) {
                    calendar.add(Calendar.DATE, 1);
                } else if (constants.SELECTED_TAB == constants.MONTHLY) {
                    calendar.add(Calendar.MONTH, 1);
                }
                updateDate();
                getTransaction();
            }
        });

        binding.prevdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (constants.SELECTED_TAB == constants.DAILY) {
                    calendar.add(Calendar.DATE, -1);
                } else if (constants.SELECTED_TAB == constants.MONTHLY) {
                    calendar.add(Calendar.MONTH, -1);
                }
                updateDate();
                getTransaction();
            }
        });

        binding.tabsforlist.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Daily")) {
                    constants.SELECTED_TAB = constants.DAILY;
                    updateDate();
                    getTransaction();
                } else if (tab.getText().equals("Monthly")) {
                    constants.SELECTED_TAB = constants.MONTHLY;
                    updateDate();
                    getTransaction();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // RecyclerView setup
     //   ArrayList<transaction> transactions = db.fetch_transactions(acc_id, user_id);
  //      transactions_adapter adapter = new transactions_adapter(this, transactions);
       // binding.transactionList.setLayoutManager(new LinearLayoutManager(this));
    //    binding.transactionList.setAdapter(adapter);
    }

    void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");
        binding.date.setText(dateFormat.format(calendar.getTime()));
    }

    public void getTransaction() {
        viewModel.getTransactions(calendar);
    }
}
