package com.example.ezetaptask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.example.ezetaptask.R
import com.example.ezetaptask.databinding.ActivitySecondBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var params: LinearLayout.LayoutParams
    private lateinit var linearLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRenderingBaseData()
        getIntentData()
    }

    private fun initRenderingBaseData() {
        //Create params for views---------------
        params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
    }

    private fun getIntentData() {
        val uiMap = intent.getSerializableExtra("MAP") as HashMap<String, String>
        for (item in uiMap) {
            val horizontalLinearLayout = LinearLayout(this)
            horizontalLinearLayout.orientation = LinearLayout.HORIZONTAL

            val textView = TextView(this)
            textView.text = item.key + " : "
            horizontalLinearLayout.addView(textView)

            val tvValue = TextView(this)
            tvValue.text = item.value
            horizontalLinearLayout.addView(tvValue)

            linearLayout.addView(horizontalLinearLayout)
        }
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ActionBar.LayoutParams.FILL_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )
        this.addContentView(linearLayout, layoutParams)
    }
}