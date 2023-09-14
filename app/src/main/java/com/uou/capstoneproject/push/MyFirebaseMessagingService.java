//package com.uou.capstoneproject.push;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//
//import androidx.annotation.NonNull;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//
//import com.uou.capstoneproject.R;
//import com.uou.capstoneproject.activity.MainActivity;
//import com.uou.capstoneproject.activity.SplashActivity;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//
//
//    /**
//     * Firebase Cloud Messaging에서 메시지를 수신받았을 때 호출되는 메서드
//     * 수신된 메시지의 데이터를 확인하고 알림을 생성하여 보여준다.
//     * 또한, 메시지를 수신하면 MainActivity를 실행
//     *
//     * @param remoteMessage 수신된 원격 메시지 객체
//     */
//    @Override
//    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//
//        // 수신된 메시지의 데이터가 비어있지 않은 경우
//        if(!remoteMessage.getData().isEmpty()){
//            sendNotification(
//                    remoteMessage.getData().get("title").toString(),
//                    remoteMessage.getData().get("content").toString()
//            );
//        }else {
//            // 수신된 메시지에 알림이 포함되어 있는 경우
//            if (remoteMessage.getNotification() != null){
//                sendNotification(
//                        remoteMessage.getData().get("title").toString(),
//                        remoteMessage.getData().get("content").toString()
//                );
//            }
//        }
//
//        // MainActivity 실행
//        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 액티비티 밖에서 startActivity를 콜하려면 Flag_ACTIVITY_NEW_TASK flag가 필요하다
//        startActivity(intent);
//    }
//
//    /**
//     * 수신된 메시지의 내용을 기반으로 알림을 생성하고 표시하는 메서드
//     *
//     * @param title 알림 제목
//     * @param body 알림 내용
//     */
//    private void sendNotification(String title, String body) {
//        int notifyId = Long.valueOf(System.currentTimeMillis() / 7).intValue(); // 고유한 알림 ID를 생성하기 위해 현재 시간을 사용
//        Intent intent = new Intent(this, SplashActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 액티비티 스택을 정리하여 중복 액티비티 인스턴스를 제거
//
//        PendingIntent pendingIntent;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            // 안드로이드 M 이상인 경우 PendingIntent에 FLAG_IMMUTABLE 플래그를 추가하여 불변성을 설정
//            pendingIntent = PendingIntent.getActivity(this,
//                    notifyId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
//        }else {
//            pendingIntent = PendingIntent.getActivity(this,
//                    notifyId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        }
//
//        String channelId = getString(R.string.firebase_notificaiton_channel_id);
//        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,channelId)
//                .setSmallIcon(R.drawable.main_logo) // 알림 아이콘을 설정
//                .setContentTitle(title) // 알림 제목을 설정
//                .setContentText(body) // 알림 내용을 설정
//                .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH) // 알림의 우선순위를 설정
//                .setAutoCancel(true) // 사용자가 알림을 탭하면 자동으로 알림이 사라지도록 설정
//                .setSound(soundUri) // 알림 소리를 설정
//                .setContentIntent(pendingIntent); // 알림을 클릭했을 때 실행될 PendingIntent를 설정
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            // Android O 이상인 경우 알림 채널을 생성합니다.
//            NotificationChannel channel = new NotificationChannel(
//                    channelId,
//                    channelId,
//                    NotificationManager.IMPORTANCE_HIGH
//            );
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        notificationManager.notify(notifyId, notificationBuilder.build()); // 알림을 표시
//    }
//
//    @Override
//    public void onNewToken(@NonNull String token) {
//        super.onNewToken(token);
//    }
//}
