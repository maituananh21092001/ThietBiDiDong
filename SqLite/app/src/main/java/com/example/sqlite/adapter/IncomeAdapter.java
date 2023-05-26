package com.example.sqlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.R;
import com.example.sqlite.model.Income;
import com.example.sqlite.model.Item;

import java.util.ArrayList;
import java.util.List;

public class IncomeAdapter extends  RecyclerView.Adapter<IncomeAdapter.HomeViewHolder>{
    private Context context;
    private List<Income> list;
    private IncomeAdapter.IncomeListener incomeListener;


    public IncomeAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setIncomeListener(IncomeAdapter.IncomeListener incomeListener) {
        this.incomeListener = incomeListener;
    }

    public void setList(List<Income> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Income getItem(int position){
        return  list.get(position);
    }
    @NonNull
    @Override
    public IncomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_income,parent,false);

        return new IncomeAdapter.HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeAdapter.HomeViewHolder holder, int position) {
        Income income = list.get(position);
        holder.tvMonth.setText("Luong thang "+income.getMonth());
        holder.tvType.setText("Loai thu nhap "+ income.getTypeIncome());
        holder.tvSalary.setText("Thu nhap "+ income.getSalary()+"VND");


    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvMonth,tvSalary,tvType;
        public HomeViewHolder(@NonNull View view)  {
            super(view);
            tvMonth = view.findViewById(R.id.tvMonth);
            tvSalary = view.findViewById(R.id.tvSalary);
            tvType = view.findViewById(R.id.tvType);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(incomeListener != null){
                incomeListener.onItemClick(view,getAdapterPosition());
            }
        }
    }


    public interface IncomeListener{
        void onItemClick(View view, int position);
    }
}
