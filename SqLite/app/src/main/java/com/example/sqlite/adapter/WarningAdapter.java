package com.example.sqlite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.R;
import com.example.sqlite.model.Item;

import java.util.ArrayList;
import java.util.List;

public class WarningAdapter extends  RecyclerView.Adapter<WarningAdapter.HomeViewHolder> {

    private List<Integer> list;


    public WarningAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<Integer> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_warning, parent, false);

        return new HomeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.alert_message.setText("Hiện tại bạn đã chi lớn hơn" + list.get(position) + " VND so với thu nhập");
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        private TextView alert_message;

        public HomeViewHolder(@NonNull View view) {
            super(view);
            alert_message = view.findViewById(R.id.alert_message);


        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}
