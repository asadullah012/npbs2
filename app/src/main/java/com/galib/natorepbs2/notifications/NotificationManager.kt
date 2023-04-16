package com.galib.natorepbs2.notifications

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.galib.natorepbs2.R

object NotificationManager {
    const val REQUEST_CODE_PERMISSIONS = 999
    private val channelId = "notification_channel"
    private val notifyID: Int = 1
    private val importance = NotificationManagerCompat.IMPORTANCE_DEFAULT

    fun createNotification(context:Context, title:String, content:String, pendingIntent: PendingIntent){
        val mChannel = NotificationChannelCompat.Builder(channelId, importance).apply {
            setName("Notifications") // Must set! Don't remove
            setDescription("Default Notifications")
            setLightsEnabled(true)
            setLightColor(Color.RED)
            setVibrationEnabled(true)
            setVibrationPattern(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
        }.build()
        NotificationManagerCompat.from(context).createNotificationChannel(mChannel)
        // create notification to inform user about update
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .addAction(R.mipmap.ic_launcher, "Update", pendingIntent)


        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_CODE_PERMISSIONS
                )
                return
            }
            notify(notifyID, notificationBuilder.build())
        }
    }
}