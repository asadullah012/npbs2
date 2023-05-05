package com.galib.natorepbs2.logger

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

object LogUtils{
    fun v(tag: String?, msg: String): Int {
        return Log.v(tag,msg)
    }

    fun v(tag: String?, msg: String?, tr: Throwable?): Int {
        return Log.v(tag,msg,tr)
    }

    fun d(tag: String?, msg: String): Int {
        return Log.d(tag, msg)
    }
    
    fun d(tag: String?, msg: String?, tr: Throwable?): Int {
        return Log.d(tag,msg,tr)
    }

    fun i(tag: String?, msg: String): Int {
        return Log.i(tag,msg)
    }

    fun i(tag: String?, msg: String?, tr: Throwable?): Int {
        return Log.i(tag,msg,tr)
    }

    fun w(tag: String?, msg: String): Int {
        return Log.w(tag,msg)
    }

    fun w(tag: String?, msg: String?, tr: Throwable?): Int {
        return Log.w(tag,msg,tr)
    }

    fun w(tag: String?, tr: Throwable?): Int {
        return Log.w(tag,tr)
    }

    fun e(tag: String?, msg: String): Int {
        val exception = Exception("Error log - $msg")
//        FirebaseCrashlytics.getInstance().recordException(exception)
        return Log.e(tag,msg)
    }

    fun e(tag: String?, msg: String?, tr: Throwable?): Int {
        return Log.e(tag,msg,tr)
    }

    fun wtf(tag: String?, msg: String?): Int {
        return Log.wtf(tag,msg)
    }

    fun wtf(tag: String?, tr: Throwable): Int {
        return Log.wtf(tag,tr)
    }

    fun wtf(tag: String?, msg: String?, tr: Throwable?): Int {
        return Log.wtf(tag,msg,tr)
    }

    fun getStackTraceString(tr: Throwable?): String {
        return Log.getStackTraceString(tr)
    }

    fun println(priority: Int, tag: String?, msg: String): Int {
        return Log.println(priority, tag, msg)
    }
}