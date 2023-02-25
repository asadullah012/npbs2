package com.galib.natorepbs2.sync

import android.content.Context
import com.galib.natorepbs2.db.NPBS2Repository

interface SyncManagerInterface {
    fun startSync(context: Context, repository: NPBS2Repository, forceSync: Boolean)
}