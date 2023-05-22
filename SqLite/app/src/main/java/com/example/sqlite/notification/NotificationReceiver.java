package com.example.sqlite.notification;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.sqlite.R;

public class NotificationReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "your_channel_id";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int itemCount = bundle.getInt("itemCount", 0);
            for (int i = 0; i < itemCount; i++) {
                String itemTitle = bundle.getString("itemTitle" + i, "");
                String itemPrice = bundle.getString("itemPrice" + i, "");
                createNoti(context, itemTitle, itemPrice);
            }
        }
        int money = intent.getIntExtra("money",0);
        if(money > 0){
            createNoti1(context,String.valueOf(money));
        }
    }

    private void createNoti(Context context, String title, String money) {
        int notificationId = generateUniqueNotificationId();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_noti)
                .setContentTitle(title)
                .setContentText("Chi tieu cua ban vao " + title + " la " + money)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Chi tieu cua ban vao " + title + " la " + money))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        managerCompat.notify(notificationId, builder.build());
    }
    private void createNoti1(Context context,String money) {
        int notificationId = generateUniqueNotificationId();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.alert_icon)
                .setContentTitle("Thông báo lạm chi")
                .setContentText("Cảnh báo lạm chí tháng này")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Chi tiêu của bạn đã quá "+ money+" trong tháng này"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        managerCompat.notify(notificationId, builder.build());
    }
    private int generateUniqueNotificationId() {
        // Tạo một NOTIFICATION_ID duy nhất bằng cách sử dụng thời gian hiện tại
        return (int) System.currentTimeMillis();
    }
}
