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


import com.google.android.material.navigation.NavigationView;

public class Homepage extends AppCompatActivity {

    private ImageButton imageButton2;
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

        accauntButton = navigationView.getHeaderView(0).findViewById(R.id.account_ac);

        imageButton2.setOnClickListener(v -> drawer_layout.openDrawer(GravityCompat.START));

        accauntButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, Account.class); //переход в класс Catalog
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_pod) {
                Intent intent = new Intent(Homepage.this, Support.class);
                startActivity(intent);
            } else if (id == R.id.nav_opr) {
                Intent intent = new Intent(Homepage.this, Aplication.class); //переход в класс Catalog
                startActivity(intent);
            } else if (id == R.id.nav_exit) {
                // Закрываем все активности приложения
                finishAffinity();
                // Альтернативный способ: System.exit(0)
            }

            drawer_layout.closeDrawer(GravityCompat.START);
            return true;
        });

        Button dom1Button = findViewById(R.id.dom1);
        dom1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход к другой активности
                Intent intent = new Intent(Homepage.this, Homepage.class);
                startActivity(intent);
            }
        });
        NavigationView navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_pod) {
                showCallDialog();  // Вызов метода для показа диалога
            }
            // Закрыть drawer после выбора элемента
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void showCallDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Появился вопрос?");
        builder.setMessage("Вы хотите позвонить?");
        builder.setPositiveButton("Позвонить", (dialog, which) -> {
            callNumber();  // Метод для начала звонка
        });
        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void callNumber() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+77057163397"));  // Укажите нужный номер
        startActivity(intent);
    }

    }
