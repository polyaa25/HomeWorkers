package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.homeworkers2.accaunt.AccauntHandle;
import com.example.homeworkers2.backend.Urls;

import org.json.JSONObject;

public class Registracia extends AppCompatActivity {

    private EditText phoneText;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText passwordText;
    private EditText password2Text;

    Urls urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracia);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        urls = new Urls(this);

        phoneText = findViewById(R.id.phone);
        firstNameText = findViewById(R.id.firstName);
        lastNameText = findViewById(R.id.lastName);
        passwordText = findViewById(R.id.password1);
        password2Text = findViewById(R.id.password2);

        Button registerButton = findViewById(R.id.register1);

        JSONObject body = new JSONObject();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(fadeInAnimation); // Применение новой анимации при нажатии

                AccauntHandle.accauntCreate(
                        urls,
                        phoneText.getText().toString(),
                        firstNameText.getText().toString(),
                        lastNameText.getText().toString(),
                        passwordText.getText().toString(),
                        password2Text.getText().toString()
                );

                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        urls.shutdown();
    }
}