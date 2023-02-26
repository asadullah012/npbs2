package com.galib.natorepbs2.sync

import android.content.Context
import android.util.Log
import com.galib.natorepbs2.db.NPBS2Repository
import kotlinx.coroutines.*

object SyncManager: SyncManagerInterface {
    const val TAG = "SyncManager"
    private var syncJob: Job? = null
//    private var job: Job = Job()
//    override val coroutineContext: CoroutineContext = Dispatchers.Main + job

    private lateinit var syncConfig: SyncConfig

    fun isSyncRunning():Boolean{
        return syncJob?.isActive == true
    }
    override fun startSync(context: Context, repository: NPBS2Repository, forceSync:Boolean) {
        if (syncJob?.isActive == true) {
            // A sync job is already running, do nothing
            return
        }

        syncJob = CoroutineScope(Dispatchers.IO).launch {

            if (SyncConfig.getSyncFailedAttempts() >= 3) {
                // Sync has failed 3 times or more, do nothing
                SyncConfig.setSyncFailedAttempts(0)
                return@launch
            }

            if (!isSyncAvailable(context) && !forceSync) {
                // Sync is not currently available, update config and return
                SyncConfig.setLastSyncTime(context, System.currentTimeMillis())
                return@launch
            }

            try {
                syncData(repository, context)
                // Sync successful, reset failed attempts in config
                SyncConfig.setLastSyncTime(context, System.currentTimeMillis())
                SyncConfig.setSyncFailedAttempts(0)
            } catch (e: Exception) {
                // Sync failed, update config with failed attempt and last sync time
                SyncConfig.setLastSyncTime(context, System.currentTimeMillis())
                SyncConfig.setSyncFailedAttempts(SyncConfig.getSyncFailedAttempts()+1)
                delay(3000) // Add a delay of 5 seconds before trying again
                startSync(context, repository, forceSync) // Call startSync() again after the delay
            }
        }
    }

    private fun isSyncAvailable(context: Context): Boolean {
        val lastUpdateTime = Sync.getLastUpdateTime()
        return if(lastUpdateTime != 0L){
            val lastSyncTime = SyncConfig.getLastSyncTime(context)
            Log.d(TAG, "isSyncAvailable: last sync time- $lastSyncTime last update time- $lastUpdateTime")
            lastSyncTime < lastUpdateTime
        } else{
            Log.d(TAG, "isSyncAvailable: unable to get last updated time")
            false
        }
    }

    private suspend fun syncData(repository: NPBS2Repository, context: Context) {
        // Make network call to sync data
        withContext(Dispatchers.IO) {
            Sync.syncBanners(repository)
            Sync.syncImportantNotice(repository)
            Sync.syncAtAGlance(repository)
            Sync.syncAchievement(repository)
            Sync.syncComplainCentre(repository)
            Sync.syncOfficerList(repository)
            Sync.syncJuniorOfficers(repository)
            Sync.syncBoardMember(repository)
            Sync.syncPowerOutageContact(repository)
            Sync.syncOfficeData(repository,context)
            Sync.syncTenderData(repository)
            Sync.syncNoticeData(repository)
            Sync.syncNewsData(repository)
            Sync.syncJobData(repository)
            Sync.syncBankInformation(repository,context)
            Sync.syncOtherOfficeInformation(repository, context)
            Sync.syncBREBContacts(repository)
        }
    }
}