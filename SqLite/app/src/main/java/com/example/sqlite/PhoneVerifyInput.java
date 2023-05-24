package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;
import android.Manifest;

import com.example.sqlite.dal.SqLiteHelper;

public class PhoneVerifyInput extends AppCompatActivity {
    EditText txtPhone;
    Button btSubmit,btCancel;
    SqLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify_input);
        db = new SqLiteHelper(this);
        txtPhone = findViewById(R.id.editTextPhone);
        btSubmit = findViewById(R.id.buttonSubmit);
        btCancel = findViewById(R.id.buttonCancel);
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = txtPhone.getText().toString().trim();
                if(db.getUserByPhone(txtPhone.getText().toString().trim()) !=null){

                    Intent intent = new Intent(PhoneVerifyInput.this, VerifyPhoneNumber.class);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                }else{
                    Toast.makeText(PhoneVerifyInput.this, "Khong co tai khoan ton tai", Toast.LENGTH_SHORT).show();
                }

            }
        });






    }


}