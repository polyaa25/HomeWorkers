package com.example.homeworkers2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.homeworkers2.accaunt.AccauntData;
import com.example.homeworkers2.backend.Auth;
import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.backend.UrlsRequestMethod;
import com.example.homeworkers2.backend.UrlsType;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Future;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Account extends AppCompatActivity {

    private ImageView avatar;

    private EditText firstNameText;
    private EditText lastNameText;
    private EditText phoneNumberText;

    private Urls urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Intent thisIntent = getIntent();
        AccauntData accauntData = (AccauntData) thisIntent.getSerializableExtra(Homepage.EXTRA_ACCAUNT_DATA);

        urls = new Urls(this);

        //------------------------------------------------

        avatar = findViewById(R.id.avatar_accaunt);

        firstNameText = findViewById(R.id.account_first_name);
        lastNameText = findViewById(R.id.account_last_name);
        phoneNumberText = findViewById(R.id.account_phone_number);

        String avatarUrl = accauntData.getAvatarUrl();

        if (avatarUrl != null){
            Picasso.get()
                    .load(avatarUrl)
                    .into(avatar);
        }

        firstNameText.setText(accauntData.getFirstName());
        lastNameText.setText(accauntData.getLastName());
        phoneNumberText.setText(accauntData.getPhoneNumber());

        //-----------------------------------------------------------------

        ImageButton bakeButton2Button = findViewById(R.id.bakeButton2);
        bakeButton2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button account_zaregButton = findViewById(R.id.account_zareg);
        account_zaregButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstname = firstNameText.getText().toString();
                String lastname = lastNameText.getText().toString();
                String phoneNumber = phoneNumberText.getText().toString();

                if(firstname != accauntData.getFirstName() ||
                        lastname != accauntData.getLastName() ||
                        phoneNumber != accauntData.getPhoneNumber()){
                    JSONObject body = new JSONObject();

                    try {
                        addObjectToJson(body, "first_name", firstname, accauntData.getFirstName());
                        addObjectToJson(body, "last_name", lastname, accauntData.getFirstName());
                        addObjectToJson(body, "telephone", phoneNumber, accauntData.getPhoneNumber());

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    Callback callback = new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        }
                    };

                    urls.sendRequest(
                            UrlsType.getUrlToUserResetParams(Auth.getAuthUserId()),
                            body.toString(),
                            UrlsRequestMethod.PATCH,
                            callback
                    );

                }

            }
        });
    }

    private static void addObjectToJson(JSONObject body, String key, String name, String nameCompared) throws JSONException {
        if(name != nameCompared){
            body.put(key, name);
        }
    }
}