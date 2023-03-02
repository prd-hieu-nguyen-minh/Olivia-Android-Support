package com.example.positiondialogdemo

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Filter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.positiondialogdemo.AppUtils.getCurrentLineText

class AutoCompleteTextActivity : AppCompatActivity() {
    private lateinit var autoCompleteText: AutoCompleteTextView
    private lateinit var sourceView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auto_complete_text)

        autoCompleteText = findViewById(R.id.autoCompleteText)
        sourceView = findViewById(R.id.sourceView)

        var dataList =
            List(10) { "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd $it" }
        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList) {
            override fun getFilter(): Filter {
                return object : Filter() {
                    override fun performFiltering(p0: CharSequence?): FilterResults {
                        val searchText = p0?.toString() ?: ""
                        val filteredList = dataList
                        return FilterResults().apply {
                            values = filteredList
                        }
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                        try {
                            if (p1?.values is MutableList<*>) {
                                dataList = (p1.values as List<String>) // TODO (Hieu Minh): update later
                                notifyDataSetChanged()
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        autoCompleteText.run {
            setAdapter(adapter)
            threshold = 0
        }

        autoCompleteText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val currentLineText = autoCompleteText.getCurrentLineText()
                val bounds = Rect()
                autoCompleteText.paint.getTextBounds(currentLineText, 0, currentLineText.length, bounds)
                val width = autoCompleteText.measuredWidth - bounds.width() - 16
                val layoutParams = sourceView.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.width = width
                autoCompleteText.dropDownWidth = 600
                sourceView.layoutParams = layoutParams
            }

        })
    }
}
