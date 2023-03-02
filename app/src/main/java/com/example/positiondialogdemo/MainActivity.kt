package com.example.positiondialogdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun showDialog(view: View) {
        val demoDialogFragment = DemoDialogFragment(view)
        demoDialogFragment.show(supportFragmentManager, null)
    }
}
