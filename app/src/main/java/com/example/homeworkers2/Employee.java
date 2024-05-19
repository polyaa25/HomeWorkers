package com.example.homeworkers2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.homeworkers2.accaunt.AccauntData;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

public class Employee extends AppCompatActivity {
    private ImageButton stopButton;

    private AccauntData employee;

    private ImageView avatar;
    private TextView nameText;
    private TextView score;
    private TextView services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        employee = (AccauntData) getIntent().getSerializableExtra(OrdersList.EXTRA_ACCAUNT_EMPLOYEE);

        stopButton = findViewById(R.id.stopButton2);

        avatar = findViewById(R.id.avatar_employee);
        nameText = findViewById(R.id.employee_name);
        score = findViewById(R.id.employee_score);
        services = findViewById(R.id.employee_service);

        nameText.setText(employee.getFirstName() + " " + employee.getLastName());
        score.setText(employee.getArithmeticMeanScore());
        services.setText(employee.getServices().getName());

        Picasso.get()
                .load(employee.getAvatarUrl())
                .into(avatar);

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button callButton = findViewById(R.id.employee_button);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
    }

    public void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);

        Button callButton = bottomSheetDialog.findViewById(R.id.call_button);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Button whatsappButton = bottomSheetDialog.findViewById(R.id.whatsapp_button);

        callButton.setOnClickListener(v -> {
            // Вызов телефона
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:+77057163397"));
            startActivity(intent);
            bottomSheetDialog.dismiss();
        });

        whatsappButton.setOnClickListener(v -> {
            // Отправка сообщения в WhatsApp
            String phoneNumber = "+77057163397";
            String url = "https://api.whatsapp.com/send?phone=" + phoneNumber;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }
}
