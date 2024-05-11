package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Aplication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplication);

        ImageButton imageButton2Button = findViewById(R.id.imageButton2);
        imageButton2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход к другой активности
                Intent intent = new Intent(Aplication.this, Homepage.class);
                startActivity(intent);
            }
        });
    }
}