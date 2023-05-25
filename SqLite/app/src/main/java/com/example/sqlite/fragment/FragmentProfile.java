package com.example.sqlite.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sqlite.HomeActivity;
import com.example.sqlite.R;
import com.example.sqlite.dal.SqLiteHelper;
import com.example.sqlite.model.User;

public class FragmentProfile extends Fragment {
    private SqLiteHelper db;
    private EditText eEmail,eYourName,eYourPhone;
    private Button logoutButton,editButton,saveButton,btChangePassword;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eEmail = view.findViewById(R.id.eEmail);
        eYourName = view.findViewById(R.id.eYour_name);
        eYourPhone = view.findViewById(R.id.eYour_phone);
        logoutButton = view.findViewById(R.id.logout_button);
        editButton = view.findViewById(R.id.edit_profile);
        saveButton = view.findViewById(R.id.save_profile);
        btChangePassword = view.findViewById(R.id.change_password);
        saveButton.setEnabled(false);
        saveButton.setVisibility(View.GONE);
        db = new SqLiteHelper(getContext());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        User user = db.getUser(username);
        eEmail.setText(user.getEmail() == null ? "" : user.getEmail());
        eYourName.setText(user.getYourName() == null? "": user.getYourName());
        eYourPhone.setText(user.getYourName() == null? "": user.getPhoneNumber());
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("username");
                editor.apply();

                // Chuyển hướng người dùng về trang đăng nhập
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                getActivity().finish();
                // Xử lý sự kiện đăng xuất ở đây
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editableEdit();
                saveButton.setEnabled(true);
                saveButton.setVisibility(View.VISIBLE);

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumberRegex = "^0[0-9]{9}$";
                String phone1 = eYourPhone.getText().toString().trim();
                String email1 = eEmail.getText().toString().trim();
                String name1 = eYourName.getText().toString().trim();
                if(!name1.equals("")) {
                    disableEdit();
                    saveButton.setVisibility(View.GONE);
                    saveButton.setEnabled(false);
                    user.setEmail(eEmail.getText().toString().trim());
                    user.setYourName(eYourName.getText().toString().trim());
                    user.setPhoneNumber(eYourPhone.getText().toString().trim());
                    db.updateUser(user);
                    Toast.makeText(getContext(), "Thay doi thanh cong", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Nhap lai", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popupView = getLayoutInflater().inflate(R.layout.change_password_popup, null);

                // Create the popup window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // Show the popup window
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                // Get references to the popup view's widgets
                EditText editTextOldPassword = popupView.findViewById(R.id.editTextOldPassword);
                EditText editTextNewPassword = popupView.findViewById(R.id.editTextNewPassword);
                EditText editTextConfirmPassword = popupView.findViewById(R.id.editTextConfirmPassword);
                Button buttonSave = popupView.findViewById(R.id.buttonSave);
                Button buttonCancel = popupView.findViewById(R.id.buttonCancel);
                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newPassword = editTextNewPassword.getText().toString();
                        String oldPassword = editTextOldPassword.getText().toString();
                        String confirmPassword = editTextConfirmPassword.getText().toString();
                        if (!user.getPassword().equals(oldPassword)) {
                            Toast.makeText(getContext(), "Incorrect old password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!newPassword.equals(confirmPassword)) {
                            Toast.makeText(getContext(), "Confirm password wrong", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        // get the new password from the edit text in the pop-up dialog
                        // update the user's password in the database
                        db.updatePassword(user, newPassword);

                        // dismiss the pop-up dialog
                        popupWindow.dismiss();

                        // show a toast message to confirm the password update
                        Toast.makeText(getContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

    }
    void editableEdit(){

        eYourName.setEnabled(true);
        eYourName.requestFocus();
//        eYourPhone.setEnabled(true);
//        eYourPhone.requestFocus();
    }

    void disableEdit(){
        eEmail.setEnabled(false);
        eEmail.requestFocus();
        eYourName.setEnabled(false);
        eYourName.requestFocus();
        eYourPhone.setEnabled(false);
        eYourPhone.requestFocus();
    }
    @Override
    public void onResume() {
        super.onResume();
        db = new SqLiteHelper(getContext());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        User user = db.getUser(username);
        eEmail.setText(user.getEmail() == null ? "" : user.getEmail());
        eYourName.setText(user.getYourName() == null? "": user.getYourName());
    }
}
