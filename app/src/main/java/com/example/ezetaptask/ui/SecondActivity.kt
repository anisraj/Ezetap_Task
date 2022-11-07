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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
    }

    private fun getIntentData() {
        val uiMap = intent.getSerializableExtra("MAP") as HashMap<String, String>
        for (item in uiMap) {
            val view = layoutInflater.inflate(R.layout.acitivity_second_item, null)
            val header = view.findViewById<TextView>(R.id.tv_label)
            val value =  view.findViewById<TextView>(R.id.tv_value)

            header.text = item.key.substring(item.key.lastIndexOf("_") + 1).uppercase() + " : "
            value.text = item.value

            binding.llContainer.addView(view)
        }
    }
}