package com.example.capstoneproject.push;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.capstoneproject.R;
import com.example.capstoneproject.activity.LoginActivity;
import com.example.capstoneproject.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(!remoteMessage.getData().isEmpty()){
            sendNotification(
                    remoteMessage.getData().get("title").toString(),
                    remoteMessage.getData().get("content").toString()
            );
        }else {
            if (remoteMessage.getNotification() != null){
                sendNotification(
                        remoteMessage.getData().get("title").toString(),
                        remoteMessage.getData().get("content").toString()
                );
            }
        }
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 액티비티 밖에서 startActivity를 콜하려면 Flag_ACTIVITY_NEW_TASK flag가 필요하다
        startActivity(intent);
    }

    private void sendNotification(String title, String body) {
        int notifyId = Long.valueOf(System.currentTimeMillis() / 7).intValue();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(this,
                    notifyId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        }else {
            pendingIntent = PendingIntent.getActivity(this,
                    notifyId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        }
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, notifyId, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = getString(R.string.firebase_notificaiton_channel_id);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,channelId)
                .setSmallIcon(R.drawable.main_logo)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    channelId,
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(notifyId, notificationBuilder.build());



    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }
}
