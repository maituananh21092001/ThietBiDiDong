package com.example.sqlite.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.AddActivity;
import com.example.sqlite.R;
import com.example.sqlite.adapter.RecycleViewAdapter;
import com.example.sqlite.dal.SqLiteHelper;
import com.example.sqlite.model.Employee;
import com.example.sqlite.model.ThongKe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private TextView tvTong,tvListTk;
    private Button btSerach;
    private SearchView searchView;

    CheckBox checkBoxWeb, checkBoxAndroid, checkBoxIOS;
    private Spinner spCategory;
    private RecycleViewAdapter adapter, adapter1;
    private SqLiteHelper db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter = new RecycleViewAdapter();
        db = new SqLiteHelper(getContext());
        List<Employee> list = db.getAll();
        adapter.setList(list);
        tvTong.setText("Tong nhan vien:"+ tong(list));

        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        List<Employee> list1= db.searchBtSkills("Web");
        List<Employee> list2= db.searchBtSkills("Android");
        List<Employee> list3= db.searchBtSkills("iOS");

        ThongKe tkWeb = new ThongKe("Thong ke so nhan vien co ki nang Web",list1.size());
        ThongKe tkAndroid = new ThongKe("Thong ke so nhan vien co ki nang android",list2.size());
        ThongKe tkios = new ThongKe("Thong ke so nhan vien co ki nang ios",list3.size());

        List<ThongKe> listTk = new ArrayList<>();
        listTk.add(tkWeb);
        listTk.add(tkAndroid);
        listTk.add(tkios);
        Collections.sort(listTk, Comparator.comparingInt(ThongKe::getLen));
        String thongke = "";
        for(int i=0;i<listTk.size();i++){
            thongke+= listTk.get(i).getName()+":"+listTk.get(i).getLen() ;
            thongke +="\n";
        }
        tvListTk.setText(thongke);









        btSerach.setOnClickListener(this);

    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycleView);
        tvTong = view.findViewById(R.id.tvTong);
        btSerach = view.findViewById(R.id.btSearch);
        checkBoxWeb = view.findViewById(R.id.checkBoxWeb);
        checkBoxAndroid = view.findViewById(R.id.checkBoxAndroid);
        checkBoxIOS = view.findViewById(R.id.checkBoxIOS);
        tvListTk = view.findViewById(R.id.tvListThongKe);


    }
    private int tong(List<Employee> list){
        int t = 0;
        for(Employee i:list){
            t+= 1;
        }
        return t;
    }

    @Override
    public void onClick(View view) {

        if(view == btSerach){
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
            if(skills.length()>0){
                List<Employee> list = db.searchBtSkills(skills);
                adapter.setList(list);
                tvTong.setText("Tong so nhan vien:"+ tong(list));
            }
            List<Employee> list1= db.searchBtSkills("Web");
            List<Employee> list2= db.searchBtSkills("Android");
            List<Employee> list3= db.searchBtSkills("iOS");

            ThongKe tkWeb = new ThongKe("Thong ke so nhan vien co ki nang Web",list1.size());
            ThongKe tkAndroid = new ThongKe("Thong ke so nhan vien co ki nang android",list2.size());
            ThongKe tkios = new ThongKe("Thong ke so nhan vien co ki nang ios",list3.size());

            List<ThongKe> listTk = new ArrayList<>();
            listTk.add(tkWeb);
            listTk.add(tkAndroid);
            listTk.add(tkios);
            Collections.sort(listTk, Comparator.comparingInt(ThongKe::getLen));
            String thongke = "";
            for(int i=0;i<listTk.size();i++){
                thongke+= listTk.get(i).getName()+":"+listTk.get(i).getLen() ;
                thongke +="\n";
            }
            tvListTk.setText(thongke);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Employee> list1= db.searchBtSkills("Web");
        List<Employee> list2= db.searchBtSkills("Android");
        List<Employee> list3= db.searchBtSkills("iOS");

        ThongKe tkWeb = new ThongKe("Thong ke so nhan vien co ki nang Web",list1.size());
        ThongKe tkAndroid = new ThongKe("Thong ke so nhan vien co ki nang android",list2.size());
        ThongKe tkios = new ThongKe("Thong ke so nhan vien co ki nang ios",list3.size());

        List<ThongKe> listTk = new ArrayList<>();
        listTk.add(tkWeb);
        listTk.add(tkAndroid);
        listTk.add(tkios);
        Collections.sort(listTk, Comparator.comparingInt(ThongKe::getLen));
        String thongke = "";
        for(int i=0;i<listTk.size();i++){
            thongke+= listTk.get(i).getName()+":"+listTk.get(i).getLen() ;
            thongke +="\n";
        }
        tvListTk.setText(thongke);
    }
}
