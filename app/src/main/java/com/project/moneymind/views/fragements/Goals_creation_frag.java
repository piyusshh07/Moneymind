package com.project.moneymind.views.fragements;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.project.moneymind.databinding.FragmentGoalsCreationFragBinding;
import com.project.moneymind.models.Goals;
import com.project.moneymind.utils.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Goals_creation_frag extends BottomSheetDialogFragment {

    FragmentGoalsCreationFragBinding binding;
    String name;
    Goals goal = new Goals();

    public Goals_creation_frag() {
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
        binding = FragmentGoalsCreationFragBinding.inflate(inflater, container, false);

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = binding.goalnamenew.getText().toString();
                goal.setName(name);
                double amount = Double.parseDouble(binding.goalamount.getText().toString());
                goal.setAmount(amount);
            }
        });

        binding.goaldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                    datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
                            String goalDate = dateFormat.format(calendar.getTime());
                            String date = helper.formatdate(calendar.getTime());
                            goal.setDeadline(calendar.getTime());
                            goal.setId(calendar.getTime().getTime());
                            binding.goaldate.setText(date);
                        }
                    });
                    datePickerDialog.show();
                }
            }
        });

        return binding.getRoot();
    }
}
