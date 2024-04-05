package com.project.moneymind;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.moneymind.databinding.FragmentGoalsFragBinding;
import com.project.moneymind.databinding.GoalsListBinding;
import com.project.moneymind.views.fragements.AddTransactionFragement;
import com.project.moneymind.views.fragements.Goals_creation_frag;


public class Goals_frag extends Fragment {

FragmentGoalsFragBinding binding;
GoalsListBinding goalsListBinding;
    public Goals_frag() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentGoalsFragBinding.inflate(inflater, container, false);
        goalsListBinding = GoalsListBinding.inflate(inflater, container, false);

        binding.creategoaltext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Goals_creation_frag().show(getParentFragmentManager(), null);

            }
        });

        // Set progress on the progress bar
        goalsListBinding.progressBar.setProgress(50); // Set the progress as needed

        return binding.getRoot();
    }
}