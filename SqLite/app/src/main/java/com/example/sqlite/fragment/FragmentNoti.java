package com.example.sqlite.fragment;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.R;
import com.example.sqlite.UpdateDeleteActivity;
import com.example.sqlite.adapter.RecycleViewAdapter;
import com.example.sqlite.adapter.WarningAdapter;
import com.example.sqlite.dal.SqLiteHelper;
import com.example.sqlite.model.Item;
import com.example.sqlite.model.User;
import com.example.sqlite.notification.NotificationReceiver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentNoti extends Fragment implements RecycleViewAdapter.ItemListener {
    RecycleViewAdapter adapter;
    WarningAdapter warningAdapter;
    private RecyclerView recyclerView,warningRecycleriew;
    private SqLiteHelper db;
    private TextView tvTong,tvTongThang,tvChiHienTai;
    private static final String CHANNEL_ID = "your_channel_id";
    private static final int NOTIFICATION_ID = 1;

    private int thu;
    private int chi;
    User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_noti, container, false);
    }

    @Override
    public void onItemClick(View view, int position) {
        Item item = adapter.getItem(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.recycleView);
        warningRecycleriew = view.findViewById(R.id.recycleViewWarning);
        tvTong = view.findViewById(R.id.tvTong);
        tvTongThang = view.findViewById(R.id.tvTongThang);
        tvChiHienTai = view.findViewById(R.id.tvChiHienTai);

        adapter = new RecycleViewAdapter();
        warningAdapter = new WarningAdapter();
        db = new SqLiteHelper(getContext());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        user = db.getUser(username);
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        String date = f.format(d);
        List<Item> list = db.getByDate(date, user.getId());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 22); // Đặt giờ là 0
        calendar.set(Calendar.MINUTE, 47); // Đặt phút là 0
        calendar.set(Calendar.SECOND, 0); // Đặt giây là 0
        Bundle bundle = new Bundle();
        bundle.putInt("itemCount", list.size());
        for (int i = 0; i < list.size(); i++) {
            bundle.putString("itemTitle" + i, list.get(i).getTitle());
            bundle.putString("itemPrice" + i, list.get(i).getPrice());
        }
        Intent intent = new Intent(getContext(), NotificationReceiver.class);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);



        //db.deleteAllItem(1);
        Calendar calendar1 = Calendar.getInstance();

        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        String formattedDate = f.format(calendar1.getTime());


        adapter.setList(list);
        tvTong.setText("Tong tien: " + tong(list));
        thu = db.getSumIncomeByMonth(user.getId(),5);
        chi = tong(db.searchByDateFromTo(formattedDate,f.format(d),user.getId()));
        tvChiHienTai.setText("So tien da chi tieu thang nay "+chi );
        tvTongThang.setText("Tong thu trong thang nay " + thu);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
        getWarningList();
        if(thu<chi){
            intent.putExtra("money",(chi-thu));
            pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null && !calendar.before(Calendar.getInstance())) {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
        }

    }

    private void getWarningList() {
        List<Integer> l = new ArrayList<>();
        if(thu < chi){

            l.add(chi-thu);

        }
        warningAdapter.setList(l);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        warningRecycleriew.setLayoutManager(manager);
        warningRecycleriew.setAdapter(warningAdapter);


    }

    private int tong(List<Item> list) {
        int t = 0;
        for (Item i : list) {
            t += Integer.parseInt(i.getPrice());
        }
        return t;
    }



    @Override
    public void onResume() {
        super.onResume();
        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        List<Item> list = db.getByDate(f.format(d),user.getId());
        adapter.setList(list);
        tvTong.setText("Tong tien: "+tong(list));
        Calendar calendar1 = Calendar.getInstance();

        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        String formattedDate = f.format(calendar1.getTime());
        thu = db.getSumIncomeByMonth(user.getId(),5);
        chi = tong(db.searchByDateFromTo(formattedDate,f.format(d),user.getId()));
        tvChiHienTai.setText("So tien da chi tieu thang nay "+ tong(db.searchByDateFromTo(formattedDate,f.format(d),user.getId())));

        tvTongThang.setText("Tong thu trong thang nay " + db.getSumIncomeByMonth(user.getId(),5));
        getWarningList();
    }
}
