package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sqlite.adapter.IncomeAdapter;
import com.example.sqlite.dal.SqLiteHelper;
import com.example.sqlite.model.Income;
import com.example.sqlite.model.User;

import java.util.ArrayList;
import java.util.List;

public class IncomeManagement extends AppCompatActivity implements IncomeAdapter.IncomeListener {
    private Spinner spMonth;
    private EditText etSalary, etType;
    private Button btAdd, btUpdate;
    private RecyclerView recyclerView;
    private IncomeAdapter incomeAdapter;
    private List<Income> incomeList = new ArrayList<>();
    SqLiteHelper db;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_management);
        db = new SqLiteHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        user = db.getUser(username);
        incomeAdapter = new IncomeAdapter(this);
        incomeList = db.getAllIncome(user.getId());
        incomeAdapter.setList(incomeList);
        initView();

        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(incomeAdapter);
        incomeAdapter.setIncomeListener(this);


        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Income income = new Income();

                double price = 0;
                try {
                    int month = Integer.valueOf(spMonth.getSelectedItem().toString());
                    String typeIncome = etType.getText().toString();
                    int salary = Integer.valueOf(etSalary.getText().toString());

                   income.setMonth(month);
                   income.setTypeIncome(typeIncome);
                   income.setSalary(salary);
                   income.setUser(user);
                   db.addIncome(income);
                }catch(NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "Nhap lai", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void initView() {
        spMonth = findViewById(R.id.spMonth);
        etSalary = findViewById(R.id.salary);
        etType = findViewById(R.id.type);
        btAdd = findViewById(R.id.btAdd);
        btUpdate = findViewById(R.id.btUpdate);
        recyclerView = findViewById(R.id.recycleView);
        spMonth.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,getResources().getStringArray(R.array.month)));
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}