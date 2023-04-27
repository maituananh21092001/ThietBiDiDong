package com.example.sqlite.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.R;
import com.example.sqlite.UpdateDeleteActivity;
import com.example.sqlite.adapter.RecycleViewAdapter;
import com.example.sqlite.dal.SqLiteHelper;
import com.example.sqlite.model.Employee;
import com.example.sqlite.model.Item;

import java.util.List;

public class FragmentHistory extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecycleViewAdapter adapter;
    private RecyclerView recycleView;
    private SqLiteHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history,container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycleView = view.findViewById(R.id.recycleView);
        adapter = new RecycleViewAdapter();
        db = new SqLiteHelper(getContext());


        List<Employee> list = db.getAll();
       // db.deleteAllItem(1);
        adapter.setList(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recycleView.setLayoutManager(manager);
        adapter.setItemListener(this);
        recycleView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        Employee employee = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("employee",employee);
        startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
        List<Employee> list = db.getAll();
        adapter.setList(list);
    }
}
