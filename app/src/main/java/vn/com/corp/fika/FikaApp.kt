package vn.com.corp.fika

import android.content.Context

class FikaApp : androidx.multidex.MultiDexApplication() {


    init {
        instance = this
    }

    companion object {
        private var instance: FikaApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}