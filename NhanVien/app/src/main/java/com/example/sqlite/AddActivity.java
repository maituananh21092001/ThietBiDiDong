package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextName,editTextPhone, editTextYearOfBirth;
    RadioGroup radioGroupGender;
    RadioButton radioButtonMale, radioButtonFemale;
    CheckBox checkBoxWeb, checkBoxAndroid, checkBoxIOS;
    Button btCancel,btUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        btCancel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
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
        btCancel = findViewById(R.id.btCancel);
        radioButtonMale.setChecked(true);

    }

    @Override
    public void onClick(View view) {

        if(view == btCancel){
            finish();
        }
        if(view== btUpdate){
            String name = editTextName.getText().toString();
            String phone = editTextPhone.getText().toString();
            String yearOfBirth = editTextYearOfBirth.getText().toString();
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
                    Employee e = new Employee(name,phone,Integer.parseInt(yearOfBirth),gender,skills);
                    SqLiteHelper db = new SqLiteHelper(this);
                    db.addItem(e);
                    finish();
                }else{
                    Toast.makeText(this, "Nam sinh sai", Toast.LENGTH_SHORT).show();
                }



            }else{
                Toast.makeText(this, "Dien lai thong tin", Toast.LENGTH_SHORT).show();
            }

        }
    }
}