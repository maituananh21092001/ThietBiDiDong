package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    Button loginButton;
   // Intent m = new Intent(Intent.ACTION_VIEW);
    //                  m.setData(Uri.parse("sms:"+"0817420095"));
//                  m.putExtra("sms_body","");
//                  startActivity(m);
    Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        if (username != null) {
            // Đã đăng nhập trước đó, chuyển đến MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish(); // Đóng MainActivity để người dùng không thể quay trở lại
        }
        setContentView(R.layout.activity_home);
        initView();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initView() {
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
    }
}