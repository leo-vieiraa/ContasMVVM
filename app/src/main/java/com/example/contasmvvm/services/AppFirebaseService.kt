package com.example.contasmvvm.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.contasmvvm.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val CHANNEL_ID = "mvvm_firebase_app"

class AppFirebaseService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        println("FCM Token")
    }



    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        val params = remoteMessage.data

        val NOTIFICATION_CHANNEL_ID = "mvvm_app_channel"

        val pattern = longArrayOf(0, 1000, 500, 1000)

        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID, "App Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.description = ""
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.vibrationPattern = pattern
            notificationChannel.enableVibration(true)
            mNotificationManager.createNotificationChannel(notificationChannel)
        }

        // to diaplay notification in DND Mode

        // to diaplay notification in DND Mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = mNotificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID)
            channel.canBypassDnd()
        }

        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)

        notificationBuilder.setAutoCancel(true)
            .setColor(ContextCompat.getColor(this, R.color.purple_200))
            .setContentTitle(getString(R.string.app_name))
            .setContentText(remoteMessage.notification!!.body)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setAutoCancel(true)


        mNotificationManager.notify(1000, notificationBuilder.build())
    }

}