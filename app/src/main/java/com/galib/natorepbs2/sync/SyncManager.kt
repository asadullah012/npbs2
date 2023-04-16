package com.galib.natorepbs2.sync

import android.content.Context
import com.galib.natorepbs2.logger.LogUtils
import com.galib.natorepbs2.db.NPBS2Repository
import kotlinx.coroutines.*

object SyncManager: SyncManagerInterface {
    private const val TAG = "SyncManager"
    private var syncJob: Job? = null

    fun isSyncRunning():Boolean{
        return syncJob?.isActive == true
    }
    override fun startSync(context: Context, repository: NPBS2Repository, forceSync:Boolean) {
        var needToSyncAgain = false
        if (syncJob?.isActive == true) {
            // A sync job is already running, do nothing
            return
        }
        LogUtils.d(TAG, "startSync: called $forceSync")
        syncJob = CoroutineScope(Dispatchers.IO).launch {

            if (SyncConfig.getSyncFailedAttempts() >= 3) {
                // Sync has failed 3 times or more, do nothing
                LogUtils.d(TAG, "startSync: sync failed more than 3 times")
                SyncConfig.setSyncFailedAttempts(0)
                return@launch
            }

            if (!isSyncAvailable(context) && !forceSync) {
                // Sync is not currently available, update config and return
                LogUtils.d(TAG, "startSync: sync not available")
                SyncConfig.setLastSyncTime(context, System.currentTimeMillis())
                return@launch
            }

            try {
                SyncConfig.updateConfigFile(context)
                syncData(repository, context)
                // Sync successful, reset failed attempts in config
                SyncConfig.setLastSyncTime(context, System.currentTimeMillis())
                SyncConfig.setSyncFailedAttempts(0)
                LogUtils.d(TAG, "startSync: complete")
            } catch (e: Exception) {
                // Sync failed, update config with failed attempt and last sync time
                LogUtils.e(TAG, "startSync: ${e.localizedMessage}")
                SyncConfig.setLastSyncTime(context, 0)
                SyncConfig.setSyncFailedAttempts(SyncConfig.getSyncFailedAttempts()+1)
                SyncConfig.updateConfigFile(context)
                delay(3000) // Add a delay of 5 seconds before trying again
                needToSyncAgain = true
                LogUtils.d(TAG, "startSync: sync failed. trying again")
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            syncJob?.join()
            if(needToSyncAgain) {
                startSync(context, repository, forceSync) // Call startSync() again after the delay
            }
        }

    }

    private fun isSyncAvailable(context: Context): Boolean {
        val lastUpdateTime = Sync.getLastUpdateTime(context)
        return if(lastUpdateTime != 0L){
            val lastSyncTime = SyncConfig.getLastSyncTime(context)
            LogUtils.d(TAG, "isSyncAvailable: last sync time- $lastSyncTime last update time- $lastUpdateTime")
            lastSyncTime < lastUpdateTime
        } else{
            LogUtils.d(TAG, "isSyncAvailable: unable to get last updated time")
            false
        }
    }

    private suspend fun syncData(repository: NPBS2Repository, context: Context) {
        // Make network call to sync data
        withContext(Dispatchers.IO) {
            Sync.syncBanners(repository, context)
            Sync.syncImportantNotice(repository, context)
            Sync.syncAtAGlance(repository, context)
            Sync.syncAchievement(repository, context)
            Sync.syncComplainCentre(repository, context)
            Sync.syncOfficerList(repository, context)
            Sync.syncJuniorOfficers(repository, context)
            Sync.syncBoardMember(repository, context)
            Sync.syncPowerOutageContact(repository, context)
            Sync.syncOfficeData(repository, context)
            Sync.syncTenderData(repository, context)
            Sync.syncNoticeData(repository, context)
            Sync.syncNewsData(repository, context)
            Sync.syncJobData(repository, context)
            Sync.syncBankInformation(repository, context)
            Sync.syncOtherOfficeInformation(repository, context)
            Sync.syncBREBContacts(repository)
        }
    }
}