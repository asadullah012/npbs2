package com.galib.natorepbs2.gsheet

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import java.io.IOException
import java.util.concurrent.Executors


class SpreadsheetViewModel(private val context: Context) : ViewModel() {
    private val sheetData: MutableLiveData<List<List<Any>>> = MutableLiveData()
    private var account: GoogleSignInAccount? = null
    private val TAG = "SpreadsheetViewModel"

    fun setAccount(account: GoogleSignInAccount?){
        this.account = account
    }

    fun getSheetData(): LiveData<List<List<Any>>> {
        return sheetData
    }

    fun fetchSheetData(spreadsheetId: String?, range: String?) {
        Log.d(TAG, "fetchSheetData: ")
        try {
            if(account != null){
                val executor = Executors.newSingleThreadExecutor()
                val handler = Handler(Looper.getMainLooper())
                executor.execute {
                    val data = SpreadsheetManager.readSheet(context, account!!, spreadsheetId, range)
                    handler.post {
                        sheetData.value = data
                    }
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "fetchSheetData: ${e.localizedMessage}")
        }
    }
    
}

class SpreadsheetViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpreadsheetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SpreadsheetViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}