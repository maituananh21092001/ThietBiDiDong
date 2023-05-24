package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlite.dal.SqLiteHelper;

import java.util.Random;

public class VerifyPhoneNumber extends AppCompatActivity {
     EditText ePass,eConfirm,eCode;
     Button btSave,btCancel;
     String verifyCode;

     SqLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);
        db = new SqLiteHelper(this);
        toSendSms(getIntent().getStringExtra("phone"));
        initView();
        btSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String code = eCode.getText().toString().trim();
                String pass = ePass.getText().toString().trim();
                String confirm = eConfirm.getText().toString().trim();
                if(!pass.equals(confirm)){
                    Toast.makeText(VerifyPhoneNumber.this, "Xac nhan mat khau khong trung nhau", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(code.equals(verifyCode)){
                    db.updateForgotPassword(getIntent().getStringExtra("phone"),ePass.getText().toString().trim());
                    Toast.makeText(VerifyPhoneNumber.this, "Doi mat khau thanh cong", Toast.LENGTH_SHORT).show();
                    Intent t = new Intent(VerifyPhoneNumber.this,LoginActivity.class);
                    startActivity(t);
                }else{
                    Toast.makeText(VerifyPhoneNumber.this, "Ma xac thuc khong đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerifyPhoneNumber.this, PhoneVerifyInput.class);
                startActivity(intent);
            }
        });
    }

    private void toSendSms(String phone) {
        if (ContextCompat.checkSelfPermission(VerifyPhoneNumber.this, android.Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {
        verifyCode = generateVerificationCode();
            sendSMS(phone, verifyCode);
        } else {
            ActivityCompat.requestPermissions(VerifyPhoneNumber.this,
                    new String[]{Manifest.permission.SEND_SMS}, 123);
        }
    }

    private void initView() {
        ePass = findViewById(R.id.editTextNewPassword);
        eConfirm = findViewById(R.id.editTextConfirmPassword);
        eCode = findViewById(R.id.editTextCode);
        btSave = findViewById(R.id.btSave);
        btCancel = findViewById(R.id.buttonCancel);


    }
    private void sendSMS(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Toast.makeText(this, "SMS sent successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                sendSMS("+84817420095", generateVerificationCode());
            } else {
                Toast.makeText(this, "Permission denied to send SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return Integer.toString(code);
    }
}