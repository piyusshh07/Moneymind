package com.project.moneymind;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.project.moneymind.ViewModel.MainViewModel;
import com.project.moneymind.databinding.FragmentMonthlyStatsBinding;
import com.project.moneymind.models.transaction;
import com.project.moneymind.utils.constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Monthly_Stats extends Fragment {

FragmentMonthlyStatsBinding binding;
MainViewModel viewModel;
Calendar calendar;
    public Monthly_Stats() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentMonthlyStatsBinding.inflate(getLayoutInflater());
        viewModel=new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        calendar=Calendar.getInstance();
        binding.IncomebtnStatsMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.IncomebtnStatsMonth.setBackground(getContext().getDrawable(R.drawable.default_income_selector));
                binding.ExpensebtnStatsMonth.setBackground(getContext().getDrawable(R.drawable.default_selector));
                constants.SELECTED_STATS_TYPE=constants.Income;

            }
        });

        binding.ExpensebtnStatsMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.ExpensebtnStatsMonth.setBackground(getContext().getDrawable(R.drawable.default_expense_selector));
                binding.IncomebtnStatsMonth.setBackground(getContext().getDrawable(R.drawable.default_selector));
                constants.SELECTED_STATS_TYPE=constants.Expense;

            }
        });
        Pie pie = AnyChart.pie();

        viewModel.categorytransaction.observe(getViewLifecycleOwner(), new Observer<ArrayList<transaction>>() {
            @Override
            public void onChanged(ArrayList<transaction> transactions) {
                if(transactions.size() > 0) {

                    binding.emptystateM.setVisibility(View.GONE);
                    binding.anychartViewMonth.setVisibility(View.VISIBLE);

                    List<DataEntry> data = new ArrayList<>();

                    Map<String, Double> categoryMap = new HashMap<>();

                    for(transaction transaction : transactions) {
                        String category = transaction.getCategory();
                        double amount = transaction.getAmount();

                        if(categoryMap.containsKey(category)) {
                            double currentTotal = categoryMap.get(category).doubleValue();
                            currentTotal += Math.abs(amount);

                            categoryMap.put(category, currentTotal);
                        } else {
                            categoryMap.put(category, Math.abs(amount));
                        }
                    }

                    for(Map.Entry<String, Double> entry : categoryMap.entrySet()) {
                        data.add(new ValueDataEntry(entry.getKey(),entry.getValue()));
                    }
                    pie.data(data);

                } else {
                    binding.emptystateM.setVisibility(View.VISIBLE);
                    binding.anychartViewMonth.setVisibility(View.GONE);
                }


            }



        });

        viewModel.getTransactionsChart(calendar,constants.SELECTED_STATS_TYPE);

        binding.anychartViewMonth.setChart(pie);


        return binding.getRoot();
    }
}