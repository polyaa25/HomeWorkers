package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.homeworkers2.backend.Auth;
import com.example.homeworkers2.backend.NetworkChangeReceiver;
import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.order.OrderAdapter;
import com.example.homeworkers2.order.OrderData;
import com.example.homeworkers2.order.OrderHandle;
import com.example.homeworkers2.order.OrdersComporator;

import java.util.ArrayList;
import java.util.Collections;

public class OrdersListActivity extends AppCompatActivity {

    public static final String EXTRA_ACCAUNT_EMPLOYEE = "EXTRA_ACCAUNT_EMPLOYEE";

    private Urls urls;
    private NetworkChangeReceiver networkChangeReceiver;
    private ArrayList<OrderData> orderDataList;
    private OrderAdapter adapter;

    private ImageButton stopButton;
    private RecyclerView orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);

        orderDataList = new ArrayList<>();

        urls = new Urls(this);
        adapter = new OrderAdapter(orderDataList);

        orderList = findViewById(R.id.order_list);
        stopButton = findViewById(R.id.stopButton);

        orderList.setLayoutManager(new LinearLayoutManager(OrdersListActivity.this));
        orderList.setAdapter(adapter);

        networkChangeReceiver = new NetworkChangeReceiver(urls);
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        networkChangeReceiver.observe(this, isConnected -> {
            if (isConnected) {
                updateOrderList();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateOrderList(){
        OrderHandle.OrderListCallback callback = new OrderHandle.OrderListCallback() {
            @Override
            public void onSuccess(ArrayList<OrderData> datas) {
                runOnUiThread(() -> {
                    orderDataList.clear();
                    orderDataList.addAll(datas);

                    Collections.sort(orderDataList, new OrdersComporator());

                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        };

        OrderHandle.getOrdersByUserId(urls, Auth.getAuthUserId(), callback);
    }
}