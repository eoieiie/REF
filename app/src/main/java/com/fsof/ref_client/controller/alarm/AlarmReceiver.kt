package com.fsof.ref_client.controller.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.app.NotificationCompat

import com.fsof.ref_client.R
import com.fsof.ref_client.utils.Alarm.CHANNEL_ID
import com.fsof.ref_client.utils.Alarm.CHANNEL_NAME
import com.fsof.ref_client.utils.Alarm.NOTIFICATION_ID
import com.fsof.ref_client.view.activity.AlarmActivity

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var ingredients: String

    override fun onReceive(context: Context, intent: Intent?) {
        ingredients = intent?.getStringArrayListExtra("ingredients")?.joinToString(", ").toString()
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
        deliverNotification(context, ingredients)
    }

    private fun deliverNotification(context: Context, ingredients: String) {
        val contentIntent = Intent(context, AlarmActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("소비기한 알림")
            .setContentText("${ingredients}의 소비기한이 3일 남았습니다.")
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.enableVibration(true)
        notificationChannel.description = "채널 세부정보입니다."
        notificationManager.createNotificationChannel(notificationChannel)
    }
}