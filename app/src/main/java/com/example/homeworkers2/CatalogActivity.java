package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.category.CategoryAdapter;
import com.example.homeworkers2.category.CategoryData;
import com.example.homeworkers2.category.CategoryHandle;

import java.util.ArrayList;

public class CatalogActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";

    private Urls urls;

    private RecyclerView categoryList;
    private ImageButton stopButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        urls = new Urls(this);

        categoryList = findViewById(R.id.category_list);
        stopButton = findViewById(R.id.stopButton);
        //nextButton = findViewById(R.id.categories1);

        CategoryHandle.getCategories(urls, new CategoryHandle.CategoryListCallback() {
            @Override
            public void onSuccess(ArrayList<CategoryData> datas) {
                runOnUiThread(() -> {
                    categoryList.setLayoutManager(new LinearLayoutManager(CatalogActivity.this));
                    categoryList.setAdapter(new CategoryAdapter(datas, R.layout.category_catalog));
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}