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

import com.example.capstoneproject.R;
import com.example.capstoneproject.activity.LoginActivity;
import com.example.capstoneproject.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public void showDataMessage(String msgTitle, String msgContent) {
        Log.i("### data msgTitle : ", msgTitle);
        Log.i("### data msgContent : ", msgContent);
        String toastText = String.format("[Data 메시지] title: %s => content: %s", msgTitle, msgContent);
        Looper.prepare();
        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();

        String channelId = "My channel ID";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.main_logo)
                        .setContentTitle(msgTitle)
                        .setContentText(msgContent)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        Looper.loop();
    }

    /**
     * 수신받은 메시지를 Toast로 보여줌
     * @param msgTitle
     * @param msgContent
     */
    public void showNotificationMessage(String msgTitle, String msgContent) {
        Log.i("### noti msgTitle : ", msgTitle);
        Log.i("### noti msgContent : ", msgContent);
        String toastText = String.format("[Notification 메시지] title: %s => content: %s", msgTitle, msgContent);
        Looper.prepare();
        Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG).show();

        String channelId = "My channel ID";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.main_logo)
                        .setContentTitle(msgTitle)
                        .setContentText(msgContent)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        Looper.loop();
    }
//
//    /**
//     * 메시지 수신받는 메소드
//     * @param msg
//     */
//    @Override
//    public void onMessageReceived(RemoteMessage msg) {
//        Log.i("### msg : ", msg.toString());
//        if (msg.getData().isEmpty()) {
//            showNotificationMessage(msg.getNotification().getTitle(), msg.getNotification().getBody());  // Notification으로 받을 때
//        } else {
//            showDataMessage(msg.getData().get("title"), msg.getData().get("content"));  // Data로 받을 때
//        }
//    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
    super.onMessageReceived(remoteMessage);
        // token을 서버로 전송한다.
        // 클라우드 서버에 등록될 시 호출, token이 앱을 구분하기 위한 고유 키가 됨.
        System.out.println("From : " + remoteMessage.getFrom());

        //알림 payload가 담겨있는지 확인
        if (remoteMessage.getNotification() != null) {
            System.out.println("Message Notification Body : " + remoteMessage.getNotification().getBody());
        }
        if (remoteMessage.getData().isEmpty()) {
            showNotificationMessage(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());  // Notification으로 받을 때
        } else {
            showDataMessage(remoteMessage.getData().get("title"), remoteMessage.getData().get("content"));  // Data로 받을 때
        }


//        sendNotification(remoteMessage.getFrom(), remoteMessage.getNotification().getBody());
//        sendNotification(remoteMessage.getNotification().getBody());
    }

    private void sendNotification(String messageBody) {
        Intent intent;
        if (getJwt().equals("")) {
            intent = new Intent(this, LoginActivity.class);
        }else {
            intent = new Intent(this, MainActivity.class);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "My channel ID";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.main_logo)
                        .setContentTitle("My new notification")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        // 수신한 메시지를 처리한다.
        // 클라우드 서버에서 메시지 전송시 자동호출, 메시지 처리해 알림 보낼 수 있음.
    }

    private void sendNotification(String from, String body) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyFirebaseMessagingService.this.getApplicationContext(), from + " -> " + body, Toast.LENGTH_SHORT).show();
//                if (msg.getData().isEmpty()) {
//                    showNotificationMessage(msg.getNotification().getTitle(), msg.getNotification().getBody());  // Notification으로 받을 때
//                } else {
//                    showDataMessage(msg.getData().get("title"), msg.getData().get("content"));  // Data로 받을 때
//                }
            }
        });
    }

    // jwt 토큰 get
    private String getJwt(){
        SharedPreferences spf = this.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt","");
    }

}
