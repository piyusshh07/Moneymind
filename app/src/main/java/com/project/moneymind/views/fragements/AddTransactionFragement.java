package com.project.moneymind.views.fragements;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.makeText;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.project.moneymind.R;
import com.project.moneymind.adapters.category_adapter;
import com.project.moneymind.database.DBHelper;
import com.project.moneymind.databinding.FragmentAddTransactionFragementBinding;
import com.project.moneymind.databinding.ListDialogBinding;
import com.project.moneymind.models.Category;
import com.project.moneymind.utils.constants;
import com.project.moneymind.utils.helper;
import com.project.moneymind.models.transaction;
import com.project.moneymind.views.activties.accounts_page;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddTransactionFragement extends BottomSheetDialogFragment {

TextInputEditText datebox;
DBHelper db;
String type,date,category,note,account;
AlertDialog alertDialog;
RadioGroup radioGroup;
RadioButton radioButton;

    int  amount;
    int Eamount;
    public AddTransactionFragement() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() !=null);


    }
    transaction transactions;
    FragmentAddTransactionFragementBinding binding;
    double nacba;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentAddTransactionFragementBinding.inflate(inflater);
        db=new DBHelper(requireContext());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", MODE_PRIVATE);
       int acc_id = sharedPreferences.getInt("account_id", -1);
       int  user_id = sharedPreferences.getInt("user_id", -1);

       transactions=new transaction();


        binding.saveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                date=binding.Exdate.getText().toString();
              amount= Integer.parseInt(binding.Examount.getText().toString());
                category=binding.Excategory.getText().toString();
                note=binding.Exnote.getText().toString();
                 account=binding.Exaccount.getText().toString();
                double acbal=db.getAccountBalanceById(acc_id,user_id);
                if(transactions.getType()==constants.Expense){
                    amount=amount*(-1);
                }
                db.insertExpense(acc_id,user_id,transactions.getType(),account,date,amount,category,note);
                Toast.makeText(getContext(), "Transaction saved successfully", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });


        binding.incomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_income_selector));
                binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
                transactions.setType(constants.Income);
            }
        });

        binding.expenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.expenseBtn.setBackground(getContext().getDrawable(R.drawable.default_expense_selector));
                binding.incomeBtn.setBackground(getContext().getDrawable(R.drawable.default_selector));
                transactions.setType(constants.Expense);
            }
        });

        binding.Exdate.setOnClickListener(new View.OnClickListener() {
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
                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMMM yyyy");

                      String datetoshow= helper.formatdate(calendar.getTime());

                        binding.Exdate.setText(datetoshow);


            }
        });
                datepicker.show();
            }
        });
        binding.Excategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListDialogBinding dialogBinding=ListDialogBinding.inflate(inflater);
                AlertDialog categorydialog=new AlertDialog.Builder(getContext()).create();
                categorydialog.setView(dialogBinding.getRoot());



                    category_adapter categoryAdpater=new category_adapter(getContext(), constants.categories, new category_adapter.CategoryclickListener() {
                        @Override
                        public void oncategoryclicked(Category category) {
                            binding.Excategory.setText(category.getCategoryname());
                            categorydialog.dismiss();
                        }});
                    dialogBinding.recyclerview.setLayoutManager(new GridLayoutManager(getContext(),3));
                    dialogBinding.recyclerview.setAdapter(categoryAdpater);

                    categorydialog.show();

            }
        });
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
        View view=getLayoutInflater().inflate(R.layout.accountype,null);
        builder.setView(view);
        alertDialog=builder.create();
       radioGroup=view.findViewById(R.id.radiogroup);

        binding.Exaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                View dialogView=getLayoutInflater().inflate(R.layout.accountype,null);
                builder.setView(dialogView);
                alertDialog=builder.create();
                RadioGroup radioGroup=dialogView.findViewById(R.id.radiogroup);

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton radioButton = dialogView.findViewById(checkedId);
                        if (radioButton != null) {
                            binding.Exaccount.setText(radioButton.getText());
                        }
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });



        return binding.getRoot();
    }

}