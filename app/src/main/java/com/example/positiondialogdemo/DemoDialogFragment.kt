package com.example.positiondialogdemo

import android.view.Gravity
import android.view.View
import com.example.positiondialogdemo.AppUtils.dp
import com.example.positiondialogdemo.databinding.FragmentDemoDialogBinding

class DemoDialogFragment(sourceView: View?) : PositionDialogFragment<FragmentDemoDialogBinding>(sourceView, 400.dp, Gravity.START) {
    override fun getViewBinding() = FragmentDemoDialogBinding.inflate(layoutInflater)

    override fun initView() {
    }

    override fun initListener() {
    }
}
