package com.galib.natorepbs2.notifications

import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.galib.natorepbs2.R

object NotificationManager {
    const val REQUEST_CODE_PERMISSIONS = 999
    fun createNotification(context:Context, title:String, content:String, pendingIntent: PendingIntent){
        // create notification to inform user about update
        val notificationId = 0
        val notificationBuilder = NotificationCompat.Builder(context, "default_channel")
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
            notify(notificationId, notificationBuilder.build())
        }
    }
}