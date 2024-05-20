package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.homeworkers2.backend.Auth;
import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.order.OrderAdapter;
import com.example.homeworkers2.order.OrderData;
import com.example.homeworkers2.order.OrderHandle;

import java.util.ArrayList;

public class OrdersList extends AppCompatActivity {

    public static final String EXTRA_ACCAUNT_EMPLOYEE = "EXTRA_ACCAUNT_EMPLOYEE";

    private Urls urls;


    private ImageButton stopButton;
    private RecyclerView orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);

        urls = new Urls(this);

        OrderHandle.OrderListCallback callback = new OrderHandle.OrderListCallback() {
            @Override
            public void onSuccess(ArrayList<OrderData> datas) {

                runOnUiThread(() -> {
                    System.out.println(datas);
                    orderList = findViewById(R.id.order_list);

                    orderList.setLayoutManager(new LinearLayoutManager(OrdersList.this));

                    OrderAdapter adapter = new OrderAdapter(datas);

                    orderList.setAdapter(adapter);
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        };

        OrderHandle.getOrdersByUserId(urls, Auth.getAuthUserId(), callback);

//        order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(OrdersList.this, Employee.class);
//                startActivity(intent);
//            }
//        });

        stopButton = findViewById(R.id.stopButton);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}