package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);



        Button further1_Button = findViewById(R.id.further1);
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.lod_gradient);
        further1_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(scaleAnimation); // Анимация при нажатии

                // Переход на другое активити
                Intent intent = new Intent(Password.this, SmsCode.class);
                startActivity(intent);
            }
        });

        Button button_login_page_Button = findViewById(R.id.button_login_page);
        button_login_page_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(scaleAnimation); // Анимация при нажатии

                // Переход на другое активити
                Intent intent = new Intent(Password.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}