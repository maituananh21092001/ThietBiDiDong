package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
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
    private Button btAdd, btUpdate,btDelete;
    private RecyclerView recyclerView;
    private IncomeAdapter incomeAdapter;
    private List<Income> incomeList = new ArrayList<>();
    private int pcurr;
    private Income incomeUpdate;
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

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(incomeAdapter);
        incomeAdapter.setIncomeListener(this);


        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Income income = new Income();

                double price = 0;
                try {
                    int month = Integer.valueOf(spMonth.getSelectedItem().toString().trim());
                    String typeIncome = etType.getText().toString().trim();
                    int salary = Integer.valueOf(etSalary.getText().toString().trim());

                    income.setMonth(month);
                    income.setTypeIncome(typeIncome);
                    income.setSalary(salary);
                    income.setUser(user);
                    db.addIncome(income);
                    incomeList = db.getAllIncome(user.getId());
                    incomeAdapter.setList(incomeList);
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Nhap lai", Toast.LENGTH_SHORT).show();

                }


            }
        });
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Income income = new Income();

                double price = 0;
                try {
                    int month = Integer.valueOf(spMonth.getSelectedItem().toString().trim());
                    String typeIncome = etType.getText().toString().trim();
                    int salary = Integer.valueOf(etSalary.getText().toString().trim());
                    income.setId(incomeUpdate.getId());
                    income.setMonth(month);
                    income.setTypeIncome(typeIncome);
                    income.setSalary(salary);
                    income.setUser(user);

                    db.updateIncome(income);
                    btAdd.setEnabled(true);
                    btUpdate.setEnabled(false);
                    btDelete.setEnabled(false);
                    incomeList = db.getAllIncome(user.getId());
                    incomeAdapter.setList(incomeList);
                    etType.setText("");
                    etSalary.setText("");
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Nhap lai", Toast.LENGTH_SHORT).show();

                }


            }
        });
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeManagement.this);
                builder.setTitle("Thong bao xoa");
                builder.setMessage("Ban có muốn xóa hok? " + incomeUpdate.getTypeIncome());
                builder.setIcon(R.drawable.remove);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteIncome(incomeUpdate.getId());
                        incomeList = db.getAllIncome(user.getId());
                        incomeAdapter.setList(incomeList);
                        btAdd.setEnabled(true);
                        btUpdate.setEnabled(false);
                        btDelete.setEnabled(false);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                etType.setText("");
                etSalary.setText("");
            }
        });
    }
    private void initView() {
        spMonth = findViewById(R.id.spMonth);
        etSalary = findViewById(R.id.salary);
        etType = findViewById(R.id.type);
        btAdd = findViewById(R.id.btAdd);
        btUpdate = findViewById(R.id.btUpdate);
        btDelete = findViewById(R.id.btDelete);
        btUpdate.setEnabled(false);
        btDelete.setEnabled(false);
        recyclerView = findViewById(R.id.recycleView);
        spMonth.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,getResources().getStringArray(R.array.month)));
    }

    @Override
    public void onItemClick(View view, int position) {
        btAdd.setEnabled(false);
        btUpdate.setEnabled(true);
        btDelete.setEnabled(true);
        pcurr = position;
       incomeUpdate = incomeAdapter.getItem(position);
        int month = incomeUpdate.getMonth();
        spMonth.setSelection(month-1);
        etSalary.setText(incomeUpdate.getSalary()+" ");
        etType.setText(incomeUpdate.getTypeIncome()+" ");


    }
}