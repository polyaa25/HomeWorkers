package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

public class CreateOrder extends AppCompatActivity {


    private ImageButton stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        stopButton = findViewById(R.id.stopButton);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button borderButton = findViewById(R.id.button_border);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        borderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(fadeInAnimation); // Применение новой анимации при нажатии

                // Переход к другой активности
                Intent intent = new Intent(CreateOrder.this, Employee.class);
                startActivity(intent);
            }
        });
    }
}