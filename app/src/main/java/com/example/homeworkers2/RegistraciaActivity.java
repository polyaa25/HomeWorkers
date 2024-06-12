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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Response;

public class RegistraciaActivity extends AppCompatActivity {


    private EditText phoneText;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText passwordText;
    private EditText password2Text;

    private TextView phoneErrorText;
    private TextView passwordErrorText;

    private NetworkChangeReceiver networkChangeReceiver;

    private Urls urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracia);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        urls = new Urls(this);

        networkChangeReceiver = new NetworkChangeReceiver(urls);
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        phoneText = findViewById(R.id.phone);
        firstNameText = findViewById(R.id.firstName);
        lastNameText = findViewById(R.id.lastName);
        passwordText = findViewById(R.id.password1);
        password2Text = findViewById(R.id.password2);

        phoneErrorText = findViewById(R.id.register_phone_error);
        passwordErrorText = findViewById(R.id.register_password_error);

        Button registerButton = findViewById(R.id.register1);

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent data = result.getData();
                    if(data == null){ return; }

                    boolean smsResult = data.getBooleanExtra(SmsCodeHandle.SMS_CODE_RESULT_EXTRA, false);

                    if(smsResult && networkChangeReceiver.isConnectedAndMessage(RegistraciaActivity.this)){
                        registration();
                    }
                }
        );

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegistraciaActivity.this, CodeActivity.class);
                intent.putExtra(SmsCodeHandle.TELEPHONE_TO_SMS_CODE_EXTRA, phoneText.getText().toString());

                resultLauncher.launch(intent);
            }
        });
    }

    private void registration(){
        AccauntHandle.UserCallback callback = new AccauntHandle.UserCallback() {
            @Override
            public void onSuccess(Response response) {
                if(response.code() == 400){
                    try {
                        JSONObject object = new JSONObject(response.body().string());

                        boolean telephone = object.getBoolean("telephone");
                        boolean password = object.getBoolean("password");

                        if(!telephone){

                            runOnUiThread(() -> {
                                phoneErrorText.setText("Пользователь с таким номером уже существует");
                                phoneErrorText.setVisibility(View.VISIBLE);
                            });
                        }else{
                            runOnUiThread(() -> {
                                phoneErrorText.setVisibility(View.GONE);
                            });
                        }

                        if(!password){
                            runOnUiThread(() -> {
                                passwordErrorText.setVisibility(View.VISIBLE);
                            });
                        }else{
                            runOnUiThread(() -> {
                                passwordErrorText.setVisibility(View.GONE);
                            });
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    runOnUiThread(()->{
                        finish();
                    });
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        };

        AccauntHandle.accauntCreate(
                urls,
                phoneText.getText().toString(),
                firstNameText.getText().toString(),
                lastNameText.getText().toString(),
                passwordText.getText().toString(),
                password2Text.getText().toString(),
                callback
        );
    }
}