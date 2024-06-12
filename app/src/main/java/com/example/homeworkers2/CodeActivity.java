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

import com.example.homeworkers2.backend.NetworkChangeReceiver;
import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.sms.SmsCodeHandle;

public class CodeActivity extends AppCompatActivity {

    private EditText smsCodeEdit;
    private Button smsCodeButton;
    private TextView smsCodeError;

    private Urls urls;
    private NetworkChangeReceiver networkChangeReceiver;

    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        urls = new Urls(this);
        networkChangeReceiver = new NetworkChangeReceiver(urls);
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        Intent thisIntent = getIntent();

        phone = thisIntent.getStringExtra(SmsCodeHandle.TELEPHONE_TO_SMS_CODE_EXTRA);

        SmsCodeHandle.sendSms(urls, phone);

        smsCodeButton = findViewById(R.id.send_sms_code);
        smsCodeError = findViewById(R.id.sms_code_error);
        smsCodeEdit = findViewById(R.id.sms_code);

        smsCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(networkChangeReceiver.isConnectedAndMessage(CodeActivity.this)){
                    checkSms();
                }
            }
        });

    }

    private void checkSms(){
        SmsCodeHandle.SmsCallback callback = new SmsCodeHandle.SmsCallback() {
            @Override
            public void onSuccess(boolean isCode) {

                if(!isCode){
                    runOnUiThread(() -> {
                        smsCodeError.setVisibility(View.VISIBLE);
                    });

                }else {
                    runOnUiThread(() ->{

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra(SmsCodeHandle.SMS_CODE_RESULT_EXTRA, isCode);
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        };

        SmsCodeHandle.checkSmsCode(urls, phone, Integer.parseInt(smsCodeEdit.getText().toString()), callback);
    }
}