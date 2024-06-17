package com.example.homeworkers2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.widget.ImageButton;
import android.content.pm.PackageManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homeworkers2.accaunt.AccauntData;
import com.example.homeworkers2.accaunt.AccauntHandle;
import com.example.homeworkers2.backend.Auth;
import com.example.homeworkers2.backend.NetworkChangeReceiver;
import com.example.homeworkers2.backend.Urls;
import com.example.homeworkers2.backend.UrlsRequestMethod;
import com.example.homeworkers2.backend.UrlsType;
import com.google.android.material.navigation.NavigationView;

public class HomepageActivity extends AppCompatActivity {

    public static final String EXTRA_ACCAUNT_DATA = "EXTRA_ACCAUNT_DATA";

    private Urls urlsBackend;
    private NetworkChangeReceiver networkChangeReceiver;

    private SwipeRefreshLayout swipeRefreshLayout;

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

        networkChangeReceiver = new NetworkChangeReceiver(urlsBackend);
        registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright);

        update();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update();
            }
        });


        setupUI();
    }

    private void update(){
        networkChangeReceiver.observe(this, isConnected -> {
            if (isConnected) {
                updateAccaunt();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private  void setupUI(){

        drawer_layout = findViewById(R.id.drawer_layout);
        imageButton2 = findViewById(R.id.imageButton2);
        navigationView = findViewById(R.id.navigationview);

        services = findViewById(R.id.human1);

        addOrderButton = findViewById(R.id.plus1);
        ordersListButton = findViewById(R.id.catalog1);
        services = findViewById(R.id.human1);

        accauntButton = navigationView.getHeaderView(0).findViewById(R.id.account_ac);

        imageButton2.setOnClickListener(v -> drawer_layout.openDrawer(GravityCompat.START));

        services.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, CatalogActivity.class); // Переход в класс Catalog
            startActivity(intent);
        });

        ordersListButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, OrdersListActivity.class); // Переход в класс Catalog
            startActivity(intent);
        });


        addOrderButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, CategoriesActivity.class); // Переход в класс Catalog
            startActivity(intent);
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_pod) {
                showContactOptions();
            } else if (id == R.id.nav_opr) {
                Intent intent = new Intent(HomepageActivity.this, AplicationActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_exit) {
                if(networkChangeReceiver.isConnectedAndMessage(HomepageActivity.this)) {
                    urlsBackend.sendRequestNonCallback(UrlsType.POST_LOGOUT, "", UrlsRequestMethod.POST);
                }

                Auth.clearAuth();

                Intent intent = new Intent(HomepageActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            drawer_layout.closeDrawer(GravityCompat.START);
            return true;
        });

        Button dom1Button = findViewById(R.id.dom1);
        dom1Button.setOnClickListener(v -> {
            Intent intent = new Intent(HomepageActivity.this, HomepageActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void updateAccaunt(){
        String userId = Auth.getAuthUserId();

        System.out.println("Hello World");

        AccauntHandle.getAccaunt(userId, urlsBackend, new AccauntHandle.AccauntCallback() {
            @Override
            public void onSuccess(AccauntData data) {

                runOnUiThread(() -> {
                    setAccauntInNavigationMenu(navigationView, data);

                    accauntButton.setOnClickListener(v -> {

                        Intent intent = new Intent(HomepageActivity.this, AccountActivity.class);

                        intent.putExtra(EXTRA_ACCAUNT_DATA, data);

                        startActivity(intent);
                    });
                });
            }

            @Override
            public void onFailure(Exception e) {

            }
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

        AccauntHandle.setAvatar(data, avatar);
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
