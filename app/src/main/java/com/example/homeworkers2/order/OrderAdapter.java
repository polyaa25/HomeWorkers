package com.example.homeworkers2.order;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homeworkers2.Employee;
import com.example.homeworkers2.OrdersList;
import com.example.homeworkers2.R;
import com.example.homeworkers2.accaunt.AccauntData;
import com.example.homeworkers2.accaunt.ServicesData;
import com.example.homeworkers2.accaunt.ServicesHandle;
import com.example.homeworkers2.backend.Urls;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<OrderData> orders;

    public static class OrderViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout orderLayout;

        public TextView nameEmployee;
        public TextView nameServices;

        public ImageView avatarEmployee;

        public OrderViewHolder(View view){
            super(view);

            orderLayout = view.findViewById(R.id.order_layout);

            nameEmployee = view.findViewById(R.id.name_order);
            nameServices = view.findViewById(R.id.name_service);

            avatarEmployee = view.findViewById(R.id.avatar_order);
        }
    }

    public OrderAdapter(List<OrderData> orders){
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View orderView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order, parent, false);

        return new OrderViewHolder(orderView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        OrderData order = orders.get(position);

        AccauntData employee = order.getEmployeeUser();
        ServicesData services = employee.getServices();

        Drawable drawable = ContextCompat.getDrawable(holder.orderLayout.getContext(), R.drawable.style_order_unactive);

        if(!order.getIsActive()){
            holder.orderLayout.setBackground(drawable);
        }

        holder.nameEmployee.setText(employee.getLastName() + " " + employee.getFirstName());
        holder.nameServices.setText(services.getName());

        Picasso.get()
                .load(employee.getAvatarUrl())
                .into(holder.avatarEmployee);

        holder.orderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context laoutContext = holder.orderLayout.getContext();

                Intent intent = new Intent(laoutContext, Employee.class);

                intent.putExtra(OrdersList.EXTRA_ACCAUNT_EMPLOYEE, employee);

                holder.orderLayout.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

}
