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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homeworkers2.accaunt.AccauntData;
import com.example.homeworkers2.accaunt.AccauntHandle;
import com.example.homeworkers2.backend.Auth;
import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.backend.UrlsRequestMethod;
import com.example.homeworkers2.backend.UrlsType;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import okhttp3.Response;

public class Homepage extends AppCompatActivity {

    public static final String EXTRA_ACCAUNT_DATA = "EXTRA_ACCAUNT_DATA";

    private Urls urlsBackend;

    private ImageButton imageButton2;
    private Button addOrderButton;
    private Button ordersListButton;
    private Button services;
    DrawerLayout drawer_layout;
    NavigationView navigationView;
    ImageButton accauntButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        urlsBackend = new Urls(this);

        drawer_layout = findViewById(R.id.drawer_layout);
        imageButton2 = findViewById(R.id.imageButton2);
        navigationView = findViewById(R.id.navigationview);

        services = findViewById(R.id.human1);

        addOrderButton = findViewById(R.id.plus1);
        ordersListButton = findViewById(R.id.catalog1);

        accauntButton = navigationView.getHeaderView(0).findViewById(R.id.account_ac);

        imageButton2.setOnClickListener(v -> drawer_layout.openDrawer(GravityCompat.START));

        //----------------------------------------------------------------

        String userId = Auth.getAuthUserId();

        AccauntHandle.getAccaunt(userId, urlsBackend, new AccauntHandle.AccauntCallback() {
            @Override
            public void onSuccess(AccauntData data) {
                runOnUiThread(() -> {
                    setAccauntInNavigationMenu(navigationView, data);

                    accauntButton.setOnClickListener(v -> {
                        Intent intent = new Intent(Homepage.this, Account.class);

                        intent.putExtra(EXTRA_ACCAUNT_DATA, data);

                        startActivity(intent);
                    });
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });


        //----------------------------------------------------------------

        ordersListButton.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, OrdersList.class); // Переход в класс Catalog
            startActivity(intent);
        });


        addOrderButton.setOnClickListener(v -> {
            Intent intent = new Intent(Homepage.this, Categories.class); // Переход в класс Catalog
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
                urlsBackend.sendRequestNonCallback(UrlsType.POST_LOGOUT, "", UrlsRequestMethod.POST);

                Auth.clearAuth();

                Intent intent = new Intent(Homepage.this, MainActivity.class);
                startActivity(intent);
                finish();
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

    private void setAccauntInNavigationMenu(NavigationView view, AccauntData data){

        View header = view.getHeaderView(0);

        TextView nameText = header.findViewById(R.id.name_accaunt_menu);
        TextView phoneText = header.findViewById(R.id.phone_accaunt_menu);

        ImageView avatar = header.findViewById(R.id.avatar);

        String name = data.getFirstName() + " " + data.getLastName();

        nameText.setText(name);
        phoneText.setText(data.getPhoneNumber());

        String avatarUrl = data.getAvatarUrl();

        if (avatarUrl != null){
            Picasso.get()
                    .load(avatarUrl)
                    .into(avatar);
        }
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
