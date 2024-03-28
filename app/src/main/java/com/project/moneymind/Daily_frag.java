package com.project.moneymind;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.moneymind.ViewModel.MainViewModel;
import com.project.moneymind.adapters.transactions_adapter;
import com.project.moneymind.database.DBHelper;
import com.project.moneymind.databinding.FragmentDailyFragBinding;
import com.project.moneymind.models.transaction;
import com.project.moneymind.utils.constants;

import java.util.ArrayList;
import java.util.Calendar;

public class Daily_frag extends Fragment {

    DBHelper db;
    FragmentDailyFragBinding binding;
    Calendar calendar;
    MainViewModel viewModel;

    public Daily_frag() {
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
        binding = FragmentDailyFragBinding.inflate(inflater, container, false);

        calendar = Calendar.getInstance();
        constants.setcategories();
        db = new DBHelper(requireContext());
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding.RecordList.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.transaction.observe(getViewLifecycleOwner(), new Observer<ArrayList<transaction>>() {
            @Override
            public void onChanged(ArrayList<transaction> transactions) {
                transactions_adapter adapter = new transactions_adapter(requireContext(), transactions);

                binding.RecordList.setAdapter(adapter);
                Log.d("transaction", String.valueOf(transactions));
            }

        });
        viewModel.totalIncome.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.incomelabel.setText(String.valueOf(aDouble));
                Log.d("income", String.valueOf(viewModel.totalIncome));
            }
        });

        viewModel.totalExpense.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.expenselabel.setText(String.valueOf(aDouble));
                Log.d("totalrx", String.valueOf(aDouble));
            }
        });

        viewModel.totalAccount.observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                binding.totallabel.setText(String.valueOf(aDouble));
                Log.d("Expense", String.valueOf(viewModel.totalAccount));
            }
        });
        viewModel.getTransactions(calendar);

        return binding.getRoot();
    }
}