package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.homeworkers2.accaunt.ServicesAdapter;
import com.example.homeworkers2.accaunt.ServicesData;
import com.example.homeworkers2.accaunt.ServicesHandle;
import com.example.homeworkers2.backend.Urls;

import java.util.ArrayList;
import java.util.List;

public class Services extends AppCompatActivity {

    public static final String EXTRA_SERVICE_ID = "EXTRA_SERVICE_ID";

    private Urls urls;

    private ArrayList<ServicesData> services;

    private RecyclerView servicesList;

    private ImageButton stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        urls = new Urls(this);

        String categoryId = getIntent().getStringExtra(Categories.EXTRA_CATEGORY_ID);

        services = ServicesHandle.getServicesByCategoryId(urls, categoryId);

        servicesList = findViewById(R.id.service_list);

        servicesList.setLayoutManager(new LinearLayoutManager(this));
        servicesList.setAdapter(new ServicesAdapter(services));

        stopButton = findViewById(R.id.stopButton);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}