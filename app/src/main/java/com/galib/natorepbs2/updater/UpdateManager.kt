package com.galib.natorepbs2.updater

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.galib.natorepbs2.R
import com.galib.natorepbs2.notifications.NotificationManager
import com.galib.natorepbs2.sync.SyncConfig
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability

object UpdateManager {
    private const val REQUEST_CODE_UPDATE = 998
    private const val TAG = "UpdateManager"
    fun checkForUpdatesAndNotify(context: Context) {
        val appUpdateManager = AppUpdateManagerFactory.create(context)
        appUpdateManager.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                // check if flexible update is available
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                    // show notification to prompt user to update
                    NotificationManager.createNotification(context, context.getString(R.string.update_available_title), context.getString(R.string.update_available_content), getUpdatePendingIntent(context))
                } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    // start immediate update flow
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        context as Activity,
                        REQUEST_CODE_UPDATE
                    )
                }
            } else {
                Log.d(TAG, "checkForUpdatesAndNotify: No update available ${appUpdateInfo.updateAvailability()}")
            }
        }
    }

    // function to get pending intent to open app update page on Play Store
    private fun getUpdatePendingIntent(context: Context): PendingIntent {
        var intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + context.packageName))
        intent.setPackage("com.android.vending")
        if (intent.resolveActivity(context.packageManager) != null) {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(SyncConfig.getUrl("PLAY_STORE_PREFIX",context) + context.packageName))
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

}