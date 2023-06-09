package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sqlite.dal.SqLiteHelper;
import com.example.sqlite.model.Item;
import com.example.sqlite.model.User;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    public Spinner sp;
    private EditText eTitle,ePrice, eDate;
    private Button btUpdate,btCancel;
    SqLiteHelper db;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        db = new SqLiteHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        user = db.getUser(username);
        initView();
        btCancel.setOnClickListener(this);
        btUpdate.setOnClickListener(this);
        eDate.setOnClickListener(this);
    }

    private void initView() {
        sp = findViewById(R.id.spCategory);
        eTitle = findViewById(R.id.tvTitle);
        ePrice = findViewById(R.id.tvPrice);
        eDate = findViewById(R.id.tvDate);
        btUpdate = findViewById(R.id.btUpdate);
        btCancel = findViewById(R.id.btCancel);
        sp.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner,getResources().getStringArray(R.array.category)));


    }

    @Override
    public void onClick(View view) {
        if(view==eDate){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if(m+1>9){
                        date = d+"/"+(m+1)+"/"+y;
                    }else{
                        date = d+"/0"+(m+1)+"/"+y;
                    }
                    if(d<=9) date = "0"+ date;
                    eDate.setText(date);
                }
            },year,month,day);
            dialog.show();

        }
        if(view == btCancel){
            finish();
        }
        if(view== btUpdate){
            String t = eTitle.getText().toString();
            String p = ePrice.getText().toString();
            String c = sp.getSelectedItem().toString();
            String d = eDate.getText().toString();
            if(!t.isEmpty()&&p.matches("\\d+")){
                Item i = new Item(t,p,d,c,user);
                db = new SqLiteHelper(this);
                db.addItem(i);
                finish();
                eTitle.setText("");
                ePrice.setText("");
                eDate.setText("");


            }

        }
    }
}