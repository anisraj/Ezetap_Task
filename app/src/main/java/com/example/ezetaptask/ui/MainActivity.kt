package com.example.ezetaptask.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.ezetaptask.databinding.ActivityMainBinding
import com.example.network.model.CustomUIResponse
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var params: LinearLayout.LayoutParams
    private lateinit var linearLayout: LinearLayout
    private val map = HashMap<String, String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRenderingBaseData()
        subscribeApiError()
        subscribeCustomUIResponse()
        fetchCustomUI()
    }

    private fun initRenderingBaseData() {
        //Create params for views---------------
        params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
    }

    private fun fetchCustomUI() {
        viewModel.getCustomUIComponents()
    }

    private fun subscribeCustomUIResponse() {
        viewModel.routeCustomUIResponse.observe(this) {
            renderUI(it.uidata)
        }
    }

    private fun subscribeApiError() {
        viewModel.routeApiError.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun renderUI(uiDataList: List<CustomUIResponse.Uidata?>?) {
        if (uiDataList != null) {
            for (uiItem in uiDataList) {
                when(uiItem?.uitype) {
                    "label" -> {
                        createTextView(uiItem)
                    }
                    "edittext" -> {
                        createEditText(uiItem)
                    }
                    "button" -> {
                        createButton(uiItem)
                    }
                }
            }
            val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                ActionBar.LayoutParams.FILL_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            )
            binding.progressBar.visibility = View.GONE
            this.addContentView(linearLayout, layoutParams)
        }
    }

    private fun createTextView(uiItem: CustomUIResponse.Uidata) {
        val textView = TextView(this)
        textView.text = uiItem.value
        textView.layoutParams = params
        linearLayout.addView(textView)
    }

    private fun createEditText(uiItem: CustomUIResponse.Uidata) {
        val editText = EditText(this)
        editText.hint = uiItem.hint
        editText.layoutParams = params
        editText.addTextChangedListener {
            uiItem.key?.let { it1 -> map[it1] = it.toString() }
        }
        linearLayout.addView(editText)
    }

    private fun createButton(uiItem: CustomUIResponse.Uidata) {
        val button = Button(this)
        button.text = uiItem.value
        button.layoutParams = params
        linearLayout.addView(button)
        button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("MAP", map)
            startActivity(intent)
        }
    }
}