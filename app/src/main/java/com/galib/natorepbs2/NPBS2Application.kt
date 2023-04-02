package com.galib.natorepbs2

import android.app.Application
import com.galib.natorepbs2.db.NPBS2DB
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.sync.SyncConfig
import com.galib.natorepbs2.sync.SyncManager
import com.galib.natorepbs2.updater.UpdateManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NPBS2Application : Application() {
    private val database by lazy { NPBS2DB.getDatabase(this) }
    val repository by lazy { NPBS2Repository(database) }
    override fun onCreate() {
        super.onCreate()
        val job = CoroutineScope(Dispatchers.IO).launch{
            SyncConfig.updateConfigFile(applicationContext)
            SyncConfig.updateDataFile(applicationContext)
        }
        CoroutineScope(Dispatchers.IO).launch{
            job.join()
            SyncManager.startSync(applicationContext, repository, false)
        }

    }
}