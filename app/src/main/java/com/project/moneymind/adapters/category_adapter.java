package com.project.moneymind.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.moneymind.R;
import com.project.moneymind.databinding.SampleCategoryItemBinding;
import com.project.moneymind.models.Category;

import java.util.ArrayList;
import java.util.Locale;

public class category_adapter extends RecyclerView.Adapter<category_adapter.Category_view_holder> {

    Context context;
    ArrayList<Category> categories;

    public interface CategoryclickListener{
        void oncategoryclicked(Category category);
    }
    CategoryclickListener categoryclickListener;


    public  category_adapter(Context context, ArrayList<Category> categories,CategoryclickListener categoryclickListener){
        this.context=context;
        this.categories=categories;
        this.categoryclickListener=categoryclickListener;

    }
    public Category_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Category_view_holder(LayoutInflater.from(context).inflate(R.layout.sample_category_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull Category_view_holder holder, int position) {
        Category category=categories.get(position);
        holder.binding.categorytext.setText(category.getCategoryname());
        holder.binding.categoryicon.setImageResource(category.getCategoryimage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryclickListener.oncategoryclicked(category);
            }
        });
    }


    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }

    public class Category_view_holder extends RecyclerView.ViewHolder{
        SampleCategoryItemBinding binding;
        public Category_view_holder(@NonNull View itemView){
            super(itemView);
            binding=SampleCategoryItemBinding.bind(itemView);

        }

    }
}
