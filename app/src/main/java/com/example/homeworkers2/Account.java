package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;

public class Account extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        ImageButton bakeButton2Button = findViewById(R.id.bakeButton2);
        bakeButton2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход к другой активности
                Intent intent = new Intent(Account.this, Homepage.class);
                startActivity(intent);
            }
        });

        Button account_zaregButton = findViewById(R.id.account_zareg);
        account_zaregButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(fadeInAnimation); // Применение новой анимации при нажатии

            }
        });
    }
}