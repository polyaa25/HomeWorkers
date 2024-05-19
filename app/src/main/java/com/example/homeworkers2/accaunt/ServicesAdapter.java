package com.example.homeworkers2.accaunt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkers2.CreateOrder;
import com.example.homeworkers2.R;
import com.example.homeworkers2.Services;
import com.example.homeworkers2.category.CategoryAdapter;
import com.example.homeworkers2.category.CategoryData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder> {

    private List<ServicesData> services;

    public ServicesAdapter(List<ServicesData> services){
        this.services = services;
    }

    public class ServicesViewHolder extends RecyclerView.ViewHolder {

        View view;

        public Button service;

        public ServicesViewHolder(@NonNull View view) {
            super(view);
            this.view = view;
            service = view.findViewById(R.id.service);
        }
    }

    @NonNull
    @Override
    public ServicesAdapter.ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View serviceView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service, parent, false);
        return new ServicesAdapter.ServicesViewHolder(serviceView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.ServicesViewHolder holder, int position) {
        ServicesData service = services.get(position);

        holder.service.setText(service.getName());

        holder.service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = holder.service.getContext();

                Intent intent = new Intent(context, CreateOrder.class);

                intent.putExtra(Services.EXTRA_SERVICE_ID, service.getId());

                context.startActivity(intent);

                if (context instanceof Activity){
                    ((Activity) context).finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }
}
