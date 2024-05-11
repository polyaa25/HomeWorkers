package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Создание Handler
        Handler handler = new Handler();

        // Отложенное выполнение кода через 2000 миллисекунд (2 секунды)
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Код, который нужно выполнить после задержки
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
            }
        }, 2000); // Задержка в миллисекундах
    }
}