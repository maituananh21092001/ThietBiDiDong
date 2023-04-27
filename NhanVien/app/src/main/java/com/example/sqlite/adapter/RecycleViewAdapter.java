package com.example.sqlite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.R;
import com.example.sqlite.model.Employee;
import com.example.sqlite.model.Item;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends  RecyclerView.Adapter<RecycleViewAdapter.HomeViewHolder> {
    private List<Employee> list;
    private ItemListener itemListener;


    public RecycleViewAdapter() {
        list = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setList(List<Employee> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Employee getItem(int position){
        return  list.get(position);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Employee e = list.get(position);
        holder.name.setText(e.getName());
        holder.phone.setText(e.getPhone());
        holder.dob.setText(e.getDob()+"");
        holder.gender.setText(e.getGender());
        holder.skills.setText(e.getSkill());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name, phone, dob, gender,skills;
        public HomeViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.tvName);
            phone = view.findViewById(R.id.tvPhone);
            dob = view.findViewById(R.id.tvDob);
            gender = view.findViewById(R.id.tvGender);
            skills = view.findViewById(R.id.tvSkill);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(itemListener!=null){
                itemListener.onItemClick(view,getAdapterPosition());
            }
        }
    }

    public interface ItemListener{
        void onItemClick(View view, int position);
    }

}
