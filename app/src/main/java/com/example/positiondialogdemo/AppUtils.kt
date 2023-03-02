package com.example.positiondialogdemo

import android.app.Activity
import android.content.res.Resources
import android.graphics.Insets
import android.os.Build
import android.text.Layout
import android.text.Selection
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout


object AppUtils {
    fun View.getXY(): Pair<Int, Int> {
        return try {
            val location = IntArray(2)
            this.getLocationOnScreen(location)
            val sourceX = location[0]
            val sourceY = location[1]
            Pair(sourceX, sourceY)
        } catch (e: Exception) {
            Pair(0, 0)
        }
    }

    val Float.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    val Int.dp: Int
        get() = this.toFloat().dp

    fun setMargins(v: View?, l: Int, t: Int, r: Int, b: Int) {
        val layoutParams = v?.layoutParams
        layoutParams?.let {
            when (it) {
                is ViewGroup.MarginLayoutParams -> {
                    it.setMargins(l, t, r, b)
                }
                is LinearLayout.LayoutParams -> {
                    it.setMargins(l, t, r, b)
                }
                is RelativeLayout.LayoutParams -> {
                    it.setMargins(l, t, r, b)
                }
            }
            v.layoutParams = it
            v.requestLayout()
        }
    }

    fun View.getWidthHeight(): Pair<Int, Int> {
        measure(0, 0)
        val width = measuredWidth
        val height = measuredHeight
        return Pair(width, height)
    }

    fun getScreenHeight(activity: Activity?): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity?.windowManager?.currentWindowMetrics
            val insets: Insets? = windowMetrics?.windowInsets?.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            (windowMetrics?.bounds?.height() ?: 0) - (insets?.top ?: 0) - (insets?.bottom ?: 0)
        } else {
            val displayMetrics = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }

    fun getScreenWidth(activity: Activity?): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity?.windowManager?.currentWindowMetrics
            val insets: Insets? = windowMetrics?.windowInsets?.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            (windowMetrics?.bounds?.width() ?: 0) - (insets?.left ?: 0) - (insets?.right ?: 0)
        } else {
            val displayMetrics = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    fun EditText.getCurrentLineText(): String {
        val lines = text.toString().split("\\r?\\n".toRegex()).toTypedArray()
        return try {
            lines[getCurrentCursorLine()]
        } catch (e: Exception) {
            ""
        }
    }

    fun EditText.getCurrentCursorLine(): Int {
        val selectionStart = Selection.getSelectionStart(text)
        val layout: Layout = this.layout
        return if (selectionStart != -1) {
            layout.getLineForOffset(selectionStart)
        } else -1
    }
}
