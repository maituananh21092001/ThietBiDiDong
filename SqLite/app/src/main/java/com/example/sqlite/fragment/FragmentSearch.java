package com.example.sqlite.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.sqlite.model.Item;
import com.example.sqlite.model.User;

import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private TextView tvTong;
    private Button btSerach;
    private SearchView searchView;
    private EditText eFrom, eTo;
    private Spinner spCategory;
    private RecycleViewAdapter adapter;
    private SqLiteHelper db;
    User user;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        adapter = new RecycleViewAdapter();
        db = new SqLiteHelper(getContext());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        user = db.getUser(username);
        List<Item> list = db.getAll(user.getId());
        adapter.setList(list);
        tvTong.setText("Tong tien"+ tong(list)+"K");
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Item> list= db.searchByTitle(s,user.getId());
                tvTong.setText("Tong tien"+ tong(list)+"K");
                adapter.setList(list);
                return false;
            }
        });
        eFrom.setOnClickListener(this);
        eTo.setOnClickListener(this);
        btSerach.setOnClickListener(this);
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String cate = spCategory.getItemAtPosition(position).toString();
                List<Item> list;
                if(!cate.equalsIgnoreCase("all")){
                    list = db.searchByCategory(cate,user.getId());
                }else{
                    list = db.getAll(user.getId());
                }
                adapter.setList(list);
                tvTong.setText("Tong tien"+ tong(list)+"K");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycleView);
        tvTong = view.findViewById(R.id.tvTong);
        btSerach = view.findViewById(R.id.btSearch);
        searchView = view.findViewById(R.id.search);
        eFrom = view.findViewById(R.id.eFrom);
        eTo = view.findViewById(R.id.eTo);
        spCategory = view.findViewById(R.id.spCategory);
        String arr[] = getResources().getStringArray(R.array.category);
        String arr1[] = new String[arr.length+1];
        arr1[0]="All";
        for(int i =0;i<arr.length;i++){
            arr1[i+1] = arr[i];
        }
        spCategory.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.item_spinner,arr1));


    }
    private int tong(List<Item> list){
        int t = 0;
        for(Item i:list){
            t+= Integer.parseInt(i.getPrice());
        }
        return t;
    }

    @Override
    public void onClick(View view) {
        if(view == eFrom){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if(m+1>9){
                        date = d+"/"+(m+1)+"/"+y;
                    }else{
                        date = d+"/0"+(m+1)+"/"+y;
                    }
                    if(d<=9) date = "0"+date;
                    eFrom.setText(date);
                }
            },year,month,day);
            dialog.show();
        }
        if(view == eTo){
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                    String date = "";
                    if(m+1>9){
                        date = d+"/"+(m+1)+"/"+y;
                    }else{
                        date = d+"/0"+(m+1)+"/"+y;
                    }
                    if(d+1<=9){
                        date = "0"+date;
                    }
                    if(d<=9) date = "0"+date;
                    eTo.setText(date);
                }
            },year,month,day);
            dialog.show();
        }
        if(view == btSerach){
            String from = eFrom.getText().toString();
            String to = eTo.getText().toString();
            if(!from.isEmpty()&&!to.isEmpty()){
                List<Item> list = db.searchByDateFromTo(from, to,user.getId());
                adapter.setList(list);
                tvTong.setText("Tong tien"+ tong(list)+"K");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Item> list = db.getAll(user.getId());
        adapter.setList(list);
        super.onResume();
    }
}
