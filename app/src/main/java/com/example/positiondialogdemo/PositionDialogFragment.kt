/*
 * Created by hieu.nguyen.minh@paradox.ai on 2022
 *  Copyright (c) 2016-2022 by Paradox.Ai . All rights reserved.
 *  Last modified 3/30/22, 8:14 AM
 */

package com.example.positiondialogdemo

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.positiondialogdemo.AppUtils.dp
import com.example.positiondialogdemo.AppUtils.getWidthHeight
import com.example.positiondialogdemo.AppUtils.getXY

abstract class PositionDialogFragment<VB : ViewBinding>(
    private val sourceView: View?,
    private val defaultWidth: Int,
    private val horizontalGravity: Int = Gravity.END,
) :
    DialogFragment(), InitLayoutInterface<VB> {
    var binding: VB? = null
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) {
            binding = getViewBinding()
        }
        return binding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = getViewBinding()
        val dialog = AlertDialog.Builder(requireActivity())
            .setView(binding!!.root)
            .create()
        val inset = InsetDrawable(ColorDrawable(resources.getColor(R.color.white)), 0, -30, 0, 10)
        dialog.window?.setBackgroundDrawable(inset)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dialog?.window?.setDecorFitsSystemWindows(false)
        }
    }

    // TODO (Hieu Nguyen) Missing case: Dialog is above sourceView when sourceView is near bottom layout
    override fun onResume() {
        super.onResume()
        sourceView?.let { view ->
            val sourceX = view.getXY().first
            val sourceY = view.getXY().second
            val window = dialog?.window
            val params = window?.attributes
            val height = binding?.root?.getWidthHeight()?.second

            val verticalGravity = if (AppUtils.getScreenHeight(activity) - sourceY.plus(view.height) > (height ?: 0)) {
                params?.x = sourceX
                params?.y = sourceY.plus(view.height - 6.dp)
                Gravity.TOP
            } else {
                params?.x = sourceX
                params?.y = sourceY.minus(height ?: 0).minus(30.dp)
                Gravity.TOP
            }

            window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window?.setGravity(verticalGravity or horizontalGravity)
            window?.setLayout(
                defaultWidth,
                height ?: WindowManager.LayoutParams.WRAP_CONTENT,
            )
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dialog?.window?.setDecorFitsSystemWindows(true)
        }
    }
}
