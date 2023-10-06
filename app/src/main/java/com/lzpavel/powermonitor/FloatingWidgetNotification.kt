package com.lzpavel.powermonitor

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

object FloatingWidgetNotification {

    val CHANNEL_ID = "FloatingWidget"


    fun build(context: Context): Notification {
        val pendingIntent: PendingIntent =
            Intent(context, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(
                    context, 0, notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            }

        return Notification.Builder(context, CHANNEL_ID)
            .setContentTitle("Floating Widget")
            .setContentText("Showing...")
            .setSmallIcon(R.drawable.ic_fw_notification)
            .setContentIntent(pendingIntent)
            .setTicker("Floating Widget")
            .setOngoing(true)
            .build()
    }

    fun createNotificationChannel(notificationManager: NotificationManager) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)


        val name = "Floating Widget"
        val descriptionText = "Floating Widget"
        val importance = NotificationManager.IMPORTANCE_MIN
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        //val notificationManager: NotificationManager =
        //    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

// Notification ID cannot be 0.
//startForeground(ONGOING_NOTIFICATION_ID, notification)
}