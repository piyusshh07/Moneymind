package com.project.moneymind;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.project.moneymind.databinding.FragmentBudgetFragBinding;
import com.project.moneymind.utils.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Budget_frag extends Fragment {
    FragmentBudgetFragBinding binding;
    String Budget_name , Budget_amount;

    public Budget_frag() {
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

        binding=FragmentBudgetFragBinding.inflate(getLayoutInflater());

        binding.budname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Budget_name= binding.budname.getText().toString();
               binding.budname.setText(Budget_name);

            }
        });

        binding.budAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Budget_amount=binding.budAmount.getText().toString();

            }
        });

        binding.buddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    DatePickerDialog datepicker=new DatePickerDialog(getContext());
                    datepicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker View, int i, int i1, int i2) {
                            Calendar calendar=Calendar.getInstance();
                            calendar.set(calendar.DAY_OF_MONTH, datepicker.getDatePicker().getDayOfMonth());
                            calendar.set(calendar.MONTH, datepicker.getDatePicker().getMonth());
                            calendar.set(calendar.YEAR, datepicker.getDatePicker().getYear());
                            SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMMM yyyy");

                            String budget_date= dateFormat.format(calendar.getTime());
                            binding.buddate.setText(budget_date);
                        }
                    });
                    datepicker.show();
                }
            }
        });
        binding.addbudbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return binding.getRoot();
    }
}