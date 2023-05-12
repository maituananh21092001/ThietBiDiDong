package com.example.sqlite.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sqlite.HomeActivity;
import com.example.sqlite.R;
import com.example.sqlite.dal.SqLiteHelper;
import com.example.sqlite.model.User;

public class FragmentProfile extends Fragment {
    private SqLiteHelper db;
    private EditText eEmail,eYourName;
    private Button logoutButton;
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
        logoutButton = view.findViewById(R.id.logout_button);
        db = new SqLiteHelper(getContext());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        User user = db.getUser(username);
        eEmail.setText(user.getEmail() == null ? "" : user.getEmail());
        eYourName.setText(user.getYourName() == null? "": user.getYourName());

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

    }
}
