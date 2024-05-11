package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class Registracia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracia);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Button register1Button = findViewById(R.id.register1);
        register1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(fadeInAnimation); // Применение новой анимации при нажатии

                // Переход к другой активности
                Intent intent = new Intent(Registracia.this, Homepage.class);
                startActivity(intent);
            }
        });
    }
}