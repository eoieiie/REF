package com.fsof.project.controller.alarm

import android.app.NotificationChannel
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.app.NotificationManager
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import com.fsof.project.R
import com.fsof.project.utils.Alarm.CHANNEL_ID
import com.fsof.project.utils.Alarm.NOTIFICATION_ID
import com.fsof.project.view.activity.MainActivity

class AlarmReceiver : BroadcastReceiver() {

    /*private*/ lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent?) {
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        deliverNotification(context)
        Toast.makeText(context, "Alarm Triggered", Toast.LENGTH_SHORT).show()
    }

    // 노티 등록
    private fun deliverNotification(context: Context){
        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // PendingIntent.FLAG_MUTABLE
            /*
            1. FLAG_UPDATE_CURRENT : 현재 PendingIntent를 유지하고, 대신 인텐트의 extra data는 새로 전달된 Intent로 교체
            2. FLAG_CANCEL_CURRENT : 현재 인텐트가 이미 등록되어있다면 삭제, 다시 등록
            3. FLAG_NO_CREATE : 이미 등록된 인텐트가 있다면, null
            4. FLAG_ONE_SHOT : 한번 사용되면, 그 다음에 다시 사용하지 않음
             */
        )
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon) // 아이콘
            .setContentTitle("타이틀 입니다.") // 제목
            .setContentText("내용 입니다.") // 내용
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    // 채널 등록
    private fun createNotificationChannel(){
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID, // 채널의 아이디
            "채널 이름입니다.", // 채널의 이름
            NotificationManager.IMPORTANCE_HIGH
            /*
            1. IMPORTANCE_HIGH = 알림음이 울리고 헤드업 알림으로 표시
            2. IMPORTANCE_DEFAULT = 알림음 울림
            3. IMPORTANCE_LOW = 알림음 없음
            4. IMPORTANCE_MIN = 알림음 없고 상태줄 표시 X
            */
        )
        notificationChannel.enableLights(true)
//            notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = "채널의 상세정보입니다."
        notificationManager.createNotificationChannel(
            notificationChannel)
//        }
    }
}