package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.category.CategoryAdapter;
import com.example.homeworkers2.category.CategoryData;
import com.example.homeworkers2.category.CategoryHolder;

import java.util.List;

public class Categories extends AppCompatActivity {

    public static final String EXTRA_CATEGORY_ID = "EXTRA_CATEGORY_ID";

    private Urls urls;

    private List<CategoryData> categories;

    private RecyclerView categoryList;
    private ImageButton stopButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        urls = new Urls(this);

        categoryList = findViewById(R.id.category_list);
        stopButton = findViewById(R.id.stopButton);
        //nextButton = findViewById(R.id.categories1);

        categories = CategoryHolder.getCategories(urls);

        System.out.println(categories.size());

        categoryList.setLayoutManager(new LinearLayoutManager(this));
        categoryList.setAdapter(new CategoryAdapter(categories));

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}