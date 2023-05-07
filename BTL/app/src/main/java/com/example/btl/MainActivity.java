package com.example.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button loginButton;
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        if (username != null) {
            // Đã đăng nhập trước đó, chuyển đến HomeActivity
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish(); // Đóng MainActivity để người dùng không thể quay trở lại
        }
        setContentView(R.layout.activity_main);
        initView();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
    }
}