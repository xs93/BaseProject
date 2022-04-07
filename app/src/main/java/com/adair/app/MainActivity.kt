package com.adair.app

import android.graphics.Color
import android.os.Bundle
import com.adair.app.databinding.ActivityMainBinding
import com.adair.core2.base.viewbinding.ui.BaseViewBindingActivity
import com.adair.utils.ui.StatusBarUtils
import com.adair.utils.ui.SystemUiUtils

class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

        mBinding.btnShowStatusBar.setOnClickListener {
            StatusBarUtils.showStatusBar(window)
            mBinding.root.requestLayout()
        }

        mBinding.btn1.setOnClickListener {
            StatusBarUtils.hideStatusBar(window)
            mBinding.root.requestLayout()
        }
        mBinding.btn2.setOnClickListener {
            StatusBarUtils.setImmersionStatusBar(window)
            mBinding.root.requestLayout()
        }
        mBinding.btn3.setOnClickListener {
            StatusBarUtils.setFirSystemWindows(window, true)
            mBinding.root.requestLayout()
        }
        mBinding.btn4.setOnClickListener {
            SystemUiUtils.setStatusBarLightFont(this, true)
            mBinding.root.requestLayout()
        }
        mBinding.btn5.setOnClickListener {
            SystemUiUtils.setStatusBarLightFont(this, false)
            mBinding.root.requestLayout()
        }

        mBinding.btn6.setOnClickListener {
            StatusBarUtils.setStatusBarColor(window, Color.RED)
            mBinding.root.requestLayout()
        }
        mBinding.btn7.setOnClickListener {
            StatusBarUtils.setFirSystemWindows(window, true)
            mBinding.root.requestLayout()
        }
    }


    private fun showDialog() {
        val dialog = DemoDialog.newInstance().apply {
            setFullScreen(true)
            setHidNavigationBar(true)
            setBackgroundDimEnable(true)
            setWindowSize(-1, -1)
            setBackgroundDimAmount(0.7f)
//            setWindowPadding(20.dp2Px, 20.dp2Px, 20.dp2Px, 20.dp2Px)
            show(supportFragmentManager, "DemoDialog")
        }
    }

    override fun isHideStatusBar(): Boolean {
        return false
    }

    override fun isHideNavigationBar(): Boolean {
        return false
    }

    override fun isLandscape(): Boolean {
        return false
    }
}