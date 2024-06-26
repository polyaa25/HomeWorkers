package com.example.homeworkers2.category;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkers2.CategoriesActivity;
import com.example.homeworkers2.R;
import com.example.homeworkers2.ServicesActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private List<CategoryData> categories;
    private int layout;

    public CategoryAdapter(List<CategoryData> categories, int layout){
        this.categories = categories;
        this.layout = layout;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout categoryLayout;
        public ImageView logoCategory;
        public TextView nameCategory;

        public CategoryViewHolder(@NonNull View view) {
            super(view);

            categoryLayout = view.findViewById(R.id.category);

            logoCategory = view.findViewById(R.id.logo_category);
            nameCategory = view.findViewById(R.id.name_category);
        }
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View categoryView = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);
        return new CategoryViewHolder(categoryView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        CategoryData category = categories.get(position);

        holder.nameCategory.setText(category.getName());

        Picasso.get()
                .load(category.getIconUrl())
                .into(holder.logoCategory);


        if(layout == R.layout.category) {

            holder.categoryLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = holder.categoryLayout.getContext();

                    Intent intent = new Intent(context, ServicesActivity.class);

                    intent.putExtra(CategoriesActivity.EXTRA_CATEGORY, category);

                    context.startActivity(intent);

                    if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
