package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.homeworkers2.backend.Auth;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Auth.create(this);

        // Создание Handler
        Handler handler = new Handler();

        // Отложенное выполнение кода через 2000 миллисекунд (2 секунды)
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Auth.getAuthToken() != null){
                    Intent intent = new Intent(Splash.this, Homepage.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                }

                finish();

            }
        }, 2000); // Задержка в миллисекундах
    }
}