package com.example.sqlite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

//import javax.mail.MessagingException;
//import javax.mail.Session;
//import javax.mail.Message;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import javax.mail.PasswordAuthentication;
//import java.util.Properties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.sqlite.dal.SqLiteHelper;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword, etEmail;
    private Button btnLogin;
    private TextView tvRegister,tvForgot;

    private SqLiteHelper sqlHelper;
    private static final int PERMISSION_SEND_SMS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Kiểm tra thông tin đăng nhập
                boolean loginSuccessful = sqlHelper.checkUser(username, password);

                if (loginSuccessful) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", username);
                    editor.apply();
                    // Đăng nhập thành công, chuyển đến trang chính
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Đăng nhập thất bại, hiển thị thông báo lỗi
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến trang đăng ký
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PhoneVerifyInput.class);
                startActivity(intent);

//                    if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.SEND_SMS)
//                            == PackageManager.PERMISSION_GRANTED) {
//
//                        sendSMS("+84817420095", generateVerificationCode());
//                    } else {
//                        ActivityCompat.requestPermissions(LoginActivity.this,
//                                new String[]{Manifest.permission.SEND_SMS}, PERMISSION_SEND_SMS);
//                    }

            }
        });

    }


    // sends the verification code to the user's email address


    private void initView() {
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);
        tvForgot = findViewById(R.id.tv_forgot);
        sqlHelper = new SqLiteHelper(this);
    }



}
