package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;

import com.example.homeworkers2.accaunt.AccauntHandle;
import com.example.homeworkers2.backend.Auth;
import com.example.homeworkers2.backend.NetworkChangeReceiver;
import com.example.homeworkers2.backend.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView nonAuthText;
    
    private EditText phoneText;
    private EditText passwordText;

    private NetworkChangeReceiver networkChangeReceiver;
    private Urls urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        urls = new Urls(this);

        networkChangeReceiver = new NetworkChangeReceiver(urls);
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        phoneText = findViewById(R.id.login_phone_edit_text);
        passwordText = findViewById(R.id.login_password_edit_text);

        nonAuthText = findViewById(R.id.login_or_password_error);

         //Анимация для изображения
        ImageView imageView = findViewById(R.id.imageView2);
        Animation slideInRight = AnimationUtils.loadAnimation(this, R.anim.log_in_right);
        imageView.startAnimation(slideInRight);

        // Анимация для кнопки
        Button button_login = findViewById(R.id.button_login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(networkChangeReceiver.isConnected());
                if(networkChangeReceiver.isConnectedAndMessage(MainActivity.this)){
                    login();
                }
            }
        });

        // Загрузка анимации плавного появления

        Button registerButton = findViewById(R.id.button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Переход к другой активности
                Intent intent = new Intent(MainActivity.this, RegistraciaActivity.class);
                startActivity(intent);
            }
        });


        Button forgot_password_Button = findViewById(R.id.button_forgot_password);
        forgot_password_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Переход на другое активити
                Intent intent = new Intent(MainActivity.this, PasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(){
        AccauntHandle.UserCallback callback = new AccauntHandle.UserCallback() {
            @Override
            public void onSuccess(Response response) {
                try {
                    if(response != null){
                        if(response.isSuccessful()){

                            JSONObject responseJson = new JSONObject(response.body().string());
                            String token = responseJson.getString("auth_token");
                            String userId = responseJson.getString("user_id");

                            Auth.setAuthUserId(userId);
                            Auth.setAuthToken(token);

                            runOnUiThread(() -> {
                                Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                                startActivity(intent);
                            });
                        } else {
                            runOnUiThread(() -> {
                                nonAuthText.setVisibility(View.VISIBLE);
                            });
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        };

        AccauntHandle.login(urls, phoneText.getText().toString(), passwordText.getText().toString(), callback);

    }
}


