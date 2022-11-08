package com.dida.android.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dida.android.R

/**
 * Android 8.0 이상에서 알림을 제공하려면 먼저 NotificationChannel
 * 인스턴스를 createNotificationChannel()에 전달하여 앱의 알림 채널을 시스템에 등록해야 합니다.
 * https://developer.android.com/training/notify-user/build-notification?hl=ko
 * 앱이 실행되면 createAlarmNotificationChannel / createFixNotificationChannel 메서드를 통해 채널을 등록합니다.
 *
 * 스와이프로 지우기를 막을 알림을 생성합니다.
 * createFixNotification() 으로 생성합니다.
 */
class NotificationUtil {

    lateinit var pendingIntent: PendingIntent

    fun createNotification(id: Int, context: Context, title: String, message: String, autoCancel: Boolean) {

        val mNotificationCompat = NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
            .setContentTitle(title)
            .setSmallIcon(R.mipmap.ic_dida_logo_foreground)
            .setStyle(NotificationCompat.BigTextStyle())
            .setContentIntent(pendingIntent)
            .setAutoCancel(autoCancel)
            .setOngoing(!autoCancel)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOnlyAlertOnce(true)
            .setShowWhen(false)
            .setContentText(message)

        NotificationManagerCompat.from(context).notify(id, mNotificationCompat.build())
    }

    fun initPendingIntent(id: Int, context: Context, intent: Intent, activity: Class<*>) {
        pendingIntent = TaskStackBuilder.create(context).run {
            addParentStack(activity)
            addNextIntentWithParentStack(intent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getPendingIntent(id, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
            } else {
                getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT)

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = context.getString(R.string.notification_channel_name)
        val descriptionText = context.getString(R.string.app_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(
            context.getString(R.string.notification_channel_id),
            name,
            importance)
            .apply {
                description = descriptionText
                setShowBadge(false) // 뱃지 안보이게 설정
            }
        // Register the channel with the system
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}