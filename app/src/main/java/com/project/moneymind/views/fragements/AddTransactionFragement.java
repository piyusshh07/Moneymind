package com.project.moneymind.views.fragements;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.project.moneymind.R;
import com.project.moneymind.adapters.category_adapter;
import com.project.moneymind.databinding.FragmentAddTransactionFragementBinding;
import com.project.moneymind.databinding.ListDialogBinding;
import com.project.moneymind.models.Category;
import com.project.moneymind.utils.constants;
import com.project.moneymind.utils.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddTransactionFragement extends BottomSheetDialogFragment {

TextInputEditText datebox;
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

        binding.date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog datepicker = new DatePickerDialog(getContext());
                datepicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(calendar.DAY_OF_MONTH, datepicker.getDatePicker().getDayOfMonth());
                        calendar.set(calendar.MONTH, datepicker.getDatePicker().getMonth());
                        calendar.set(calendar.YEAR, datepicker.getDatePicker().getYear());

                      //  SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMMM yyyy");

                      String datetoshow= helper.formatdate(calendar.getTime());

                        binding.date.setText(datetoshow);


            }
        });
                datepicker.show();
            }
        });
        binding.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListDialogBinding dialogBinding=ListDialogBinding.inflate(inflater);
                AlertDialog categorydialog=new AlertDialog.Builder(getContext()).create();
                categorydialog.setView(dialogBinding.getRoot());



                    category_adapter categoryAdpater=new category_adapter(getContext(), constants.categories, new category_adapter.CategoryclickListener() {
                        @Override
                        public void oncategoryclicked(Category category) {
                            binding.category.setText(category.getCategoryname());
                            categorydialog.dismiss();
                        }});
                    dialogBinding.recyclerview.setLayoutManager(new GridLayoutManager(getContext(),3));
                    dialogBinding.recyclerview.setAdapter(categoryAdpater);

                    categorydialog.show();

            }
        });
        return binding.getRoot();
    }

}