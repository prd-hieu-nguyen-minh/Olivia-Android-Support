/*
 * Created by hieu.nguyen.minh@paradox.ai on 2022
 *  Copyright (c) 2016-2022 by Paradox.Ai . All rights reserved.
 *  Last modified 3/29/22, 9:59 AM
 */

package com.example.positiondialogdemo

import androidx.viewbinding.ViewBinding

interface InitLayoutInterface<VB: ViewBinding> {
    fun getViewBinding(): VB
    fun initView()
    fun initListener()
}
