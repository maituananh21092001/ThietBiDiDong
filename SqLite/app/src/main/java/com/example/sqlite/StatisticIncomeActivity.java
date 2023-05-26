package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.example.sqlite.dal.SqLiteHelper;
import com.example.sqlite.model.Income;
import com.example.sqlite.model.User;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticIncomeActivity extends AppCompatActivity {

    private BarChart chartIncome;
    SqLiteHelper db;
    User user;
    @Override
    public void onBackPressed() {
        // Thực hiện hành động back của bạn ở đây
        // Ví dụ: quay về Activity trước đó, đóng Activity hiện tại, vv.
        super.onBackPressed(); // Hãy chắc chắn gọi super.onBackPressed() để thực hiện hành động back mặc định
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_income);
        db = new SqLiteHelper(this);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        user = db.getUser(username);

        chartIncome = findViewById(R.id.chartIncome);

        // Example data
        List<Income> incomeList = getIncomeData();
        Collections.sort(incomeList, new Comparator<Income>() {
            @Override
            public int compare(Income o1, Income o2) {
                return Integer.compare(o1.getMonth(), o2.getMonth());
            }
        });

        // Prepare data for chart
        HashMap<Integer, Integer> incomeMap = new HashMap<>(); // Sử dụng HashMap để lưu trữ thu nhập cho mỗi tháng
        List<String> labels = new ArrayList<>();
        List<BarEntry> entries = new ArrayList<>();


        for(int i = 0;i<12;i++){
            incomeMap.put(i+1,0);
            labels.add(monthLabels[i]);
            entries.add(new BarEntry(i, 0));
        }
        for (Income income : incomeList) {
            int month = income.getMonth();
            int salary = income.getSalary();

            if (incomeMap.containsKey(month)) {
                // Nếu đã có mục nhập cho tháng này, thì cộng dồn thu nhập
                int totalIncome = incomeMap.get(month) + salary;
                incomeMap.put(month, totalIncome);

                // Cập nhật giá trị của cột trong danh sách entries
                int entryIndex = month - 1; // Vị trí cột trong danh sách entries
                entries.get(entryIndex).setY(totalIncome);
            } else {
                // Nếu chưa có mục nhập cho tháng này, thì thêm mới
                incomeMap.put(month, salary);
                labels.add(monthLabels[month - 1]);
                entries.add(new BarEntry(month - 1, salary));
            }
        }

        // Create bar dataset
        BarDataSet dataSet = new BarDataSet(entries, "Monthly Income");
        dataSet.setColor(Color.BLUE);

        List<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);

        // Create bar data and set it to chart
        BarData barData = new BarData(dataSets);
        chartIncome.setData(barData);

        // Set X-axis labels
        XAxis xAxis = chartIncome.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labels.size());

        // Set Y-axis
        YAxis yAxisLeft = chartIncome.getAxisLeft();
        YAxis yAxisRight = chartIncome.getAxisRight();
        yAxisLeft.setDrawGridLines(false);
        yAxisRight.setDrawGridLines(false);

        // Refresh chart
        chartIncome.invalidate();
    }

    private List<Income> getIncomeData() {
        List<Income> list = db.getAllIncome(user.getId());
        return list;
    }

    // Mảng các nhãn tháng
    private String[] monthLabels = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};

}
