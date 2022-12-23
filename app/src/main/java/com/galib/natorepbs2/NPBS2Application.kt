package com.galib.natorepbs2

import android.app.Application
import com.galib.natorepbs2.db.NPBS2DB
import com.galib.natorepbs2.db.NPBS2Repository

class NPBS2Application : Application() {
    private val database by lazy { NPBS2DB.getDatabase(this) }
    val repository by lazy { NPBS2Repository(database) }
}