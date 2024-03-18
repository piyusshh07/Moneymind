package com.project.moneymind.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.moneymind.R;
import com.project.moneymind.databinding.HistoryRecordBinding;
import com.project.moneymind.models.Category;
import com.project.moneymind.models.transaction;
import com.project.moneymind.utils.constants;
import com.project.moneymind.utils.helper;

import java.util.ArrayList;



public class transactions_adapter extends RecyclerView.Adapter<transactions_adapter.transactionViewHolder>{
ArrayList<transaction> transactions;
Context context;
    public transactions_adapter(Context context , ArrayList<transaction> transactions){
        this.context=context;
        this.transactions=transactions;
    }
    @NonNull
    @Override
    public transactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new transactionViewHolder(LayoutInflater.from(context).inflate(R.layout.history_record,parent,false));

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull transactionViewHolder holder, int position) {
        transaction  transaction=transactions.get(position);
        holder.binding.transactionAmount.setText(String.valueOf(transaction.getAmount()));
        holder.binding.transactionDate.setText(helper.formatdate(transaction.getDate()));
        holder.binding.transactionCategory.setText(transaction.getCategory());

        Category transactinCategory = constants.getcategorydetails(transaction.getCategory());
        holder.binding.categoryIcon.setImageResource(transactinCategory.getCategoryimage());

        if(transaction.getType().equals(constants.Income)){
        holder.binding.transactionAmount.setTextColor(R.color.green);
        } else if (transaction.getType().equals(constants.Expense)) {
            holder.binding.transactionAmount.setTextColor(R.color.red);

        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class transactionViewHolder extends RecyclerView.ViewHolder{
        HistoryRecordBinding binding;
        public transactionViewHolder(@NonNull View ItemView){
            super(ItemView);
            binding=HistoryRecordBinding.bind(ItemView);

        }
    }

}
