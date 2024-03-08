package com.project.moneymind;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.project.moneymind.databinding.FragmentAddTransactionFragementBinding;

public class AddTransactionFragement extends BottomSheetDialogFragment {


    public AddTransactionFragement() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    FragmentAddTransactionFragementBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentAddTransactionFragementBinding.inflate(inflater);


        binding.incomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_income_selector));
                binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            }
        });

        binding.expenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_expense_selector));
                binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
            }
        });
        return binding.getRoot();

    }

}