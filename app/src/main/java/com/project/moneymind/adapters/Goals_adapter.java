package com.project.moneymind.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.moneymind.R;
import com.project.moneymind.databinding.FragmentGoalsCreationFragBinding;
import com.project.moneymind.databinding.FragmentGoalsFragBinding;
import com.project.moneymind.models.Goals;

import java.util.ArrayList;

public class Goals_adapter extends RecyclerView.Adapter<Goals_adapter.GoalsViewholder>{

    ArrayList<Goals> Goal;
    Context context;
    @NonNull
    @Override

    public Goals_adapter.GoalsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new GoalsViewholder(LayoutInflater.from(context).inflate(R.layout.fragment_goals_creation_frag,parent,false));
    }
    public Goals_adapter(Context context,ArrayList<Goals> goals){
        this.Goal=goals;
        this.context=context;

    }

    @Override
    public void onBindViewHolder(@NonNull Goals_adapter.GoalsViewholder holder, int position) {
    Goals goal= Goal.get(position);
    holder.binding.goalamount.setText(String.valueOf(goal.getAmount()));
    holder.binding.goaldate.setText(String.valueOf(goal.getDeadline()));
    holder.binding.goalnamenew.setText(String.valueOf(goal.getName()));
    }

    @Override
    public int getItemCount() {
        return Goal.size();
    }

    public class GoalsViewholder extends RecyclerView.ViewHolder {

        FragmentGoalsCreationFragBinding binding;
        public GoalsViewholder(@NonNull View itemView) {
            super(itemView);
            binding=FragmentGoalsCreationFragBinding.bind(itemView);

        }
    }
}
