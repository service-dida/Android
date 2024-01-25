package com.dida.android.util

import android.content.Intent
import android.os.Build
import com.dida.android.presentation.activities.NavHostActivity
import com.dida.common.util.AppLog
import com.dida.data.DataApplication
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.runBlocking

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        AppLog.e("Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        AppLog.e("Remote Message", remoteMessage.to)
        remoteMessage.notification?.let { notification ->
            showDefaultNotification(notification.title.toString(), notification.body.toString())
        }
    }

    private fun showDefaultNotification(title: String, body: String) {
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationUtil().createNotificationChannel(applicationContext)
        }

        NotificationUtil().apply {
            runBlocking {
                val notificationIndex = DataApplication.dataStorePreferences.getFcmIndex()
                if (notificationIndex != null) {
                    DataApplication.dataStorePreferences.setFcmIndex(notificationIndex+1)
                }

                val mIntent = Intent(applicationContext, NavHostActivity::class.java).apply {
                    putExtra("fcm", true)
                }
                initPendingIntent(notificationIndex!!, applicationContext, mIntent, NavHostActivity::class.java)

                createNotification(notificationIndex, applicationContext, title, body, true)
            }
        }
    }
}

