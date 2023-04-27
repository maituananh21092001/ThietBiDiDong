package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sqlite.dal.SqLiteHelper;
import com.example.sqlite.model.Employee;
import com.example.sqlite.model.Item;

import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextName,editTextPhone, editTextYearOfBirth;
    RadioGroup radioGroupGender;
    RadioButton radioButtonMale, radioButtonFemale;
    CheckBox checkBoxWeb, checkBoxAndroid, checkBoxIOS;
    private Button btUpdate,btBack,btRemove;
    private Employee employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);
        initView();
        btBack.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        btRemove.setOnClickListener(this);

        Intent intent = getIntent();
        employee = (Employee) intent.getSerializableExtra("employee");
        editTextName.setText(employee.getName());
        editTextPhone.setText(employee.getPhone());
        editTextYearOfBirth.setText(employee.getDob()+" ");
        if(employee.getGender().equalsIgnoreCase("Nam")){
            radioButtonMale.setChecked(true);
            radioButtonFemale.setChecked(false);
        }else if(employee.getGender().equalsIgnoreCase("Nữ")){
            radioButtonMale.setChecked(false);
            radioButtonFemale.setChecked(true);
        }

        if(employee.getSkill().contains("Web")){
            checkBoxWeb.setChecked(true);
        }
        if(employee.getSkill().contains("iOS")){
            checkBoxIOS.setChecked(true);
        }
        if(employee.getSkill().contains("Android")){
            checkBoxAndroid.setChecked(true);
        }


    }

    private void initView() {
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextYearOfBirth = findViewById(R.id.editTextYearOfBirth);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        checkBoxWeb = findViewById(R.id.checkBoxWeb);
        checkBoxAndroid = findViewById(R.id.checkBoxAndroid);
        checkBoxIOS = findViewById(R.id.checkBoxIOS);
        btUpdate = findViewById(R.id.btUpdate);
        btBack = findViewById(R.id.btBack);
        btRemove = findViewById(R.id.btRemove);

    }

    @Override
    public void onClick(View view) {
        SqLiteHelper db = new SqLiteHelper(this);

        if(view == btBack){
            finish();
        }
        if(view == btUpdate){
            String name = editTextName.getText().toString();
            String phone = editTextPhone.getText().toString();

            String yearOfBirth = editTextYearOfBirth.getText().toString().trim();
            String gender = radioButtonMale.isChecked() ? "Nam" : "Nữ";
            String skills = "";
            if (checkBoxWeb.isChecked()) {
                skills += "Web, ";
            }
            if (checkBoxAndroid.isChecked()) {
                skills += "Android, ";
            }
            if (checkBoxIOS.isChecked()) {
                skills += "iOS, ";
            }
            if (skills.length() > 0) {
                skills = skills.substring(0, skills.length() - 2);
            }
            if(yearOfBirth.matches("\\d+")&&name.length()>0&&phone.length()>0){
                if(Integer.parseInt(yearOfBirth)>1980&&Integer.parseInt(yearOfBirth)<1995){
                    Employee e = new Employee(employee.getId(),name,phone,Integer.parseInt(yearOfBirth),gender,skills);
                    db = new SqLiteHelper(this);
                    db.updateItem(e);
                    finish();
                }else{
                    Toast.makeText(this, "Nam sinh sai", Toast.LENGTH_SHORT).show();
                }



            }else{
                Toast.makeText(this, "Dien lai thong tin", Toast.LENGTH_SHORT).show();
            }

        }
        if(view == btRemove){
            int id =employee.getId();
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Thong bao xoa");
            builder.setMessage("ban co chac muon xoa khong"+ employee.getName()+"khong");
            builder.setIcon(R.drawable.remove);
            builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SqLiteHelper db = new SqLiteHelper(getApplicationContext());
                    db.deleteItem(id);
                    finish();
                }
            });
            builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }
}