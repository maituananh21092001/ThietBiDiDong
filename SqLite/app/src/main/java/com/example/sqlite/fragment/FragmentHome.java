package com.example.sqlite.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.IncomeManagement;
import com.example.sqlite.R;
import com.example.sqlite.StatisticIncomeActivity;
import com.example.sqlite.adapter.IncomeAdapter;

public class FragmentHome extends Fragment {

    Button incomeManagement, incomeStatistic;
    TextView hello;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        incomeManagement = view.findViewById(R.id.income_management_button);
        incomeStatistic = view.findViewById(R.id.income_statistics_button);
        hello = view.findViewById(R.id.welcome_message);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        hello.setText("Hello "+username);
        incomeManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IncomeManagement.class);
                startActivity(intent);
            }
        });

        incomeStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StatisticIncomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
