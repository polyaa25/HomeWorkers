package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.homeworkers2.accaunt.AccauntData;
import com.example.homeworkers2.accaunt.AccauntHandle;
import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.order.OrderHandle;

public class CreateOrder extends AppCompatActivity {
    private ImageButton stopButton;

    private EditText editTelephoneText;
    private EditText editAddressText;
    private EditText editOrderText;
    private Urls urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        String idService = getIntent().getStringExtra(Services.EXTRA_SERVICE_ID);

        urls = new Urls(this);

        stopButton = findViewById(R.id.stopButton);

        editTelephoneText = findViewById(R.id.editTelephoneText);
        editAddressText = findViewById(R.id.editAddressText);
        editOrderText = findViewById(R.id.editOrderText);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button borderButton = findViewById(R.id.button_border);

        borderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AccauntHandle.AccauntCallback callback = new AccauntHandle.AccauntCallback() {
                    @Override
                    public void onSuccess(AccauntData data) {
                        runOnUiThread(() -> {
                            Intent intent = new Intent(CreateOrder.this, Employee.class);

                            intent.putExtra(OrdersList.EXTRA_ACCAUNT_EMPLOYEE, data);

                            startActivity(intent);
                            finish();
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                };

                OrderHandle.createOrder(
                        urls,
                        editTelephoneText.getText().toString(),
                        editAddressText.getText().toString(),
                        editOrderText.getText().toString(),
                        idService,
                        callback
                );

                finish();
            }
        });
    }
}