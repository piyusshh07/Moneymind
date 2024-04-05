package com.project.moneymind.views.activties;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.project.moneymind.R;
import com.project.moneymind.ViewModel.MainViewModel;
import com.project.moneymind.adapters.Charts_adapter;
import com.project.moneymind.adapters.Stats_viewPager_Adapter;
import com.project.moneymind.databinding.ActivityChartsBinding;
import com.project.moneymind.utils.constants;
import com.project.moneymind.utils.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Charts_activity extends AppCompatActivity {
    ActivityChartsBinding binding;
    Calendar calendar;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChartsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        calendar=Calendar.getInstance();
        viewModel= new ViewModelProvider(this).get(MainViewModel.class);
        updateDate();

            Charts_adapter adapter = new Charts_adapter(getSupportFragmentManager()){};
            binding.StatsViewPager1.setAdapter(adapter);
            binding.TabStats.setupWithViewPager(binding.StatsViewPager1);

            binding.TabStats.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getText().equals("Daily")) {
                        constants.SELECTED_TAB_STATS = constants.DAILY;
                        updateDate();
                        getTransactionCharts();
                    } else {
                        constants.SELECTED_TAB_STATS = constants.MONTHLY;
                        updateDate();
                        getTransactionCharts();
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        binding.nextdateStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (constants.SELECTED_TAB_STATS == constants.DAILY) {
                    calendar.add(Calendar.DATE, 1);
                    updateDate();
                    getTransactionCharts();

                } else if (constants.SELECTED_TAB_STATS == constants.MONTHLY) {
                    calendar.add(Calendar.MONTH, 1);
                    updateDate();
                    getTransactionCharts();
                }


            }
        });

        binding.prevdateStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (constants.SELECTED_TAB_STATS == constants.DAILY) {
                    calendar.add(Calendar.DATE, -1);
                    updateDate();
                    getTransactionCharts();
                } else if (constants.SELECTED_TAB_STATS == constants.MONTHLY) {
                    calendar.add(Calendar.MONTH, -1);
                }
                updateDate();
                getTransactionCharts();

            }
        });



    }

   public void updateDate(){
        if( constants.SELECTED_TAB_STATS ==constants.DAILY) {
            binding.dateStats.setText(helper.formatdate(calendar.getTime()));
        }else if(constants.SELECTED_TAB_STATS ==constants.MONTHLY){
            binding.dateStats.setText(helper.format_date_month(calendar.getTime()));
        }

    }
    public void getTransactionCharts(){
        viewModel.getTransactionsChart(calendar,constants.SELECTED_STATS_TYPE);
    }
}

