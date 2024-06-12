package com.example.homeworkers2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.homeworkers2.accaunt.AccauntHandle;
import com.example.homeworkers2.backend.NetworkChangeReceiver;
import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.sms.SmsCodeHandle;

public class Password extends AppCompatActivity {

    public static final String PHONE_NUMBER_EXTRA = "phone_number_extra";

    private TextView phoneError;
    private EditText phoneEdit;
    private Button buttonNext;

    private Urls urls;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        urls = new Urls(this);
        networkChangeReceiver = new NetworkChangeReceiver(urls);
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        phoneError = findViewById(R.id.password_phone_error);
        phoneEdit = findViewById(R.id.password_phone_edit);
        buttonNext = findViewById(R.id.phone_next_button);

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent data = result.getData();
                    if(data == null){ return; }

                    boolean smsResult = data.getBooleanExtra(SmsCodeHandle.SMS_CODE_RESULT_EXTRA, false);

                    if(smsResult) {
                        Intent intent = new Intent(Password.this, PasswordResetActivity.class);
                        intent.putExtra(PHONE_NUMBER_EXTRA, phoneEdit.getText().toString());
                        startActivity(intent);

                        finish();
                    }
                }
        );

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(networkChangeReceiver.isConnectedAndMessage(Password.this)){
                    checkPhone(resultLauncher);
                }
            }
        });

    }

    private void checkPhone(ActivityResultLauncher<Intent> resultLauncher){
        AccauntHandle.PhoneCallback callback = new AccauntHandle.PhoneCallback() {
            @Override
            public void onSuccess(boolean isExists, boolean isFirst) {
                runOnUiThread(() -> {
                    if(!isFirst){
                        phoneError.setVisibility(View.VISIBLE);
                        phoneError.setText("Пользователя с таким номером не существует");
                    }else{
                        Intent intent = new Intent(Password.this, CodeActivity.class);
                        intent.putExtra(SmsCodeHandle.TELEPHONE_TO_SMS_CODE_EXTRA, phoneEdit.getText().toString());

                        resultLauncher.launch(intent);
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        };

        AccauntHandle.checkPhoneNumber(urls, phoneEdit.getText().toString(), callback);
    }
}