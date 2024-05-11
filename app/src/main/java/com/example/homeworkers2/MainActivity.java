package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Анимация для изображения
        ImageView imageView = findViewById(R.id.imageView2);
        Animation slideInRight = AnimationUtils.loadAnimation(this, R.anim.log_in_right);
        imageView.startAnimation(slideInRight);

        // Анимация для кнопки
        Button button_login = findViewById(R.id.button_login);
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.lod_gradient);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(fadeInAnimation); // Анимация при нажатии

            }
        });

        // Загрузка анимации плавного появления

        Button registerButton = findViewById(R.id.button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(fadeInAnimation); // Применение новой анимации при нажатии

                // Переход к другой активности
                Intent intent = new Intent(MainActivity.this, Registracia.class);
                startActivity(intent);
            }
        });


        Button forgot_password_Button = findViewById(R.id.button_forgot_password);
        forgot_password_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(fadeInAnimation);// Анимация при нажатии

                // Переход на другое активити
                Intent intent = new Intent(MainActivity.this, Password.class);
                startActivity(intent);
            }
        });

        Button button_login_Button = findViewById(R.id.button_login);
        button_login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(scaleAnimation); // Анимация при нажатии

                // Переход на другое активити
                Intent intent = new Intent(MainActivity.this, Homepage.class);
                startActivity(intent);
            }
        });
    }
}


