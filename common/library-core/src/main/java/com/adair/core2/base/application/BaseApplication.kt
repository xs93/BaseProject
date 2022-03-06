package com.adair.core2.base.application

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.adair.core.crash.CrashHandler
import com.adair.core2.utils.AppInject

/**
 * 基础Application
 *
 * @author XuShuai
 * @version v1.0
 * @date 2021/11/5 9:58
 */
abstract class BaseApplication : Application() {

    private val mComponentAppClassNameList = mutableListOf<String>()
    private val mHelper = ComponentApplicationHelper()


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        addComponentApplication(mComponentAppClassNameList)
        mHelper.initAppObjects(mComponentAppClassNameList)
        mHelper.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        AppInject.init(this)
        CrashHandler.getInstance().init(this)
        mHelper.onCreate(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        mHelper.onTerminate(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mHelper.onLowMemory(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        mHelper.onTrimMemory(this, level)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mHelper.onConfigurationChanged(this, newConfig)
    }

    abstract fun addComponentApplication(classNames: MutableList<String>)
}