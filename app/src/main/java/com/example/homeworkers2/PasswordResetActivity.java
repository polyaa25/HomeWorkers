package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.homeworkers2.accaunt.AccauntHandle;
import com.example.homeworkers2.backend.NetworkChangeReceiver;
import com.example.homeworkers2.backend.Urls;

public class PasswordResetActivity extends AppCompatActivity {

    private Urls urls;
    private NetworkChangeReceiver networkChangeReceiver;

    private TextView resetPasswordError;
    private EditText passwordEdit;
    private EditText passwordRepeatEdit;
    private Button sendResetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        urls = new Urls(this);

        networkChangeReceiver = new NetworkChangeReceiver(urls);
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        resetPasswordError = findViewById(R.id.reset_password_error);
        passwordEdit = findViewById(R.id.reset_password1);
        passwordRepeatEdit = findViewById(R.id.reset_password2);

        sendResetPasswordButton = findViewById(R.id.send_new_password_button);

        Intent thisIntent = getIntent();

        sendResetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(networkChangeReceiver.isConnectedAndMessage(PasswordResetActivity.this)){
                    resetPassword(thisIntent);
                }
            }
        });

    }

    private void resetPassword(Intent intent){
        AccauntHandle.PasswordCallback passwordCallback = new AccauntHandle.PasswordCallback() {
            @Override
            public void onSuccess(boolean isIdenticalPasswords) {
                runOnUiThread(()->{
                    if(!isIdenticalPasswords){
                        resetPasswordError.setVisibility(View.VISIBLE);
                    }else{
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        };

        String phone = intent.getStringExtra(Password.PHONE_NUMBER_EXTRA);

        AccauntHandle.resetPassword(
                urls,
                phone,
                passwordEdit.getText().toString(),
                passwordRepeatEdit.getText().toString(), passwordCallback
        );
    }
}