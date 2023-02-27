package com.galib.natorepbs2

import android.app.Application
import com.galib.natorepbs2.db.NPBS2DB
import com.galib.natorepbs2.db.NPBS2Repository
import com.galib.natorepbs2.sync.SyncConfig
import com.galib.natorepbs2.sync.SyncManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NPBS2Application : Application() {
    private val database by lazy { NPBS2DB.getDatabase(this) }
    val repository by lazy { NPBS2Repository(database) }
    override fun onCreate() {
        super.onCreate()
        val job = CoroutineScope(Dispatchers.IO).launch{
            SyncConfig.updateConfigFile(applicationContext)
        }
        runBlocking{
            job.join()
            SyncManager.startSync(applicationContext, repository, false)
        }
    }
}