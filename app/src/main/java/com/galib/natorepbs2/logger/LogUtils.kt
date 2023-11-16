package com.galib.natorepbs2.logger

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

object LogUtils{
    fun v(tag: String, msg: String) {
         Timber.tag(tag).v(msg)
    }

    fun v(tag: String, msg: String?, tr: Throwable?) {
         Timber.tag(tag).v(tr, msg)
    }

    fun d(tag: String, msg: String) {
        Timber.tag(tag).d(msg)
        Log.d(tag, msg)
    }
    
    fun d(tag: String, msg: String, tr: Throwable?) {
        Timber.tag(tag).d(tr, msg)
    }

    fun i(tag: String, msg: String) {
        Timber.tag(tag).i(msg)
    }

    fun i(tag: String, msg: String, tr: Throwable?) {
        Timber.tag(tag).i(tr, msg)
    }

    fun w(tag: String, msg: String) {
        Timber.tag(tag).w(msg)
    }

    fun w(tag: String, msg: String, tr: Throwable?) {
        Timber.tag(tag).w(tr, msg)
    }

    fun w(tag: String, tr: Throwable?) {
        Timber.tag(tag).w(tr)
    }

    fun e(tag: String, msg: String) {
        val exception = Exception("Error log - $msg")
        FirebaseCrashlytics.getInstance().recordException(exception)
        Timber.tag(tag).e(msg)
        Log.e(tag, msg)
    }

    fun e(tag: String, msg: String?, tr: Throwable?) {
        Timber.tag(tag).e(tr, msg)
    }

    fun wtf(tag: String, msg: String?) {
        Timber.tag(tag).wtf(msg)
    }

    fun wtf(tag: String, tr: Throwable) {
        Timber.tag(tag).wtf(tr)
    }

    fun wtf(tag: String, msg: String?, tr: Throwable?) {
        Timber.tag(tag).wtf(tr, msg)
    }

    fun getStackTraceString(tr: Throwable?): String {
         return Log.getStackTraceString(tr)
    }

    fun println(priority:Int, tag: String?, msg: String) {
         Log.println(priority, tag, msg)
    }
}