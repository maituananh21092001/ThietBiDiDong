package com.example.sqlite;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlite.dal.SqLiteHelper;
import com.example.sqlite.model.User;

public class RegisterActivity extends Activity {
    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mHovatenEditText;
    private EditText mPasswordEditText,mPhone;
    private Button registerButton;
    private TextView loginLinkTextView;
    private SqLiteHelper sqlHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        initView();
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameEditText.getText().toString().trim();
                String email = mEmailEditText.getText().toString().trim();
                String hovaten = mHovatenEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                // Kiểm tra xem các trường thông tin có bị bỏ trống hay không
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(hovaten) || TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo đối tượng User và gọi hàm addUser để thêm vào database
                User user = new User(name, password, email, hovaten,phone);
                boolean success = sqlHelper.addUser(user);
                if (success) {
                    Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    finish(); // Quay lại trang đăng nhập
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginLinkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến activity đăng nhập
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mNameEditText = findViewById(R.id.register_name);
        mEmailEditText = findViewById(R.id.register_email);
        mHovatenEditText = findViewById(R.id.register_hovaten);
        mPasswordEditText = findViewById(R.id.register_password);
        mPhone = findViewById(R.id.register_phone);
        registerButton = findViewById(R.id.register_button);
        loginLinkTextView = findViewById(R.id.register_login_link);
        sqlHelper = new SqLiteHelper(this);

    }

}
