package com.example.homeworkers2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.widget.ImageButton;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Homepage extends AppCompatActivity {

    private ImageButton imageButton2;
    private Button addOrderButton;
    private Button ordersListButton;
    DrawerLayout drawer_layout;
    NavigationView navigationView;

    ImageButton accauntButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        drawer_layout = findViewById(R.id.drawer_layout);
        imageButton2 = findViewById(R.id.imageButton2);
        navigationView = findViewById(R.id.navigationview);

        addOrderButton = findViewById(R.id.plus1);
        ordersListButton = findViewById(R.id.catalog1);

        accauntButton = navigationView.getHeaderView(0).findViewById(R.id.account_ac);

        imageButton2.setOnClickListener(v -> drawer_layout.openDrawer(GravityCompat.START));

        ordersListButton.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, OrdersList.class); // Переход в класс Catalog
            startActivity(intent);
        });


        addOrderButton.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, Categories.class); // Переход в класс Catalog
            startActivity(intent);
        });

        accauntButton.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, Account.class);
            startActivity(intent);
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_pod) {
                showContactOptions();
            } else if (id == R.id.nav_opr) {
                Intent intent = new Intent(Homepage.this, Aplication.class);
                startActivity(intent);
            } else if (id == R.id.nav_exit) {
                finishAffinity();  // Закрыть все активности приложения
            }
            drawer_layout.closeDrawer(GravityCompat.START);
            return true;
        });

        Button dom1Button = findViewById(R.id.dom1);
        dom1Button.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, Homepage.class);
            startActivity(intent);
        });
    }

    private void showContactOptions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Возникли вопросы?");
        builder.setMessage("Выберите действие:");
        builder.setPositiveButton("Позвонить", (dialog, which) -> callNumber());
        builder.setNeutralButton("Написать в WhatsApp", (dialog, which) -> openWhatsApp());
        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void callNumber() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+77057163397"));
        startActivity(intent);
    }

    private void openWhatsApp() {
        String phoneNumber = "+77057163397";
        String url = "https://api.whatsapp.com/send?phone=" + phoneNumber;
        try {
            PackageManager packageManager = getPackageManager();
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp не установлен", Toast.LENGTH_SHORT).show();
        }
    }
}
