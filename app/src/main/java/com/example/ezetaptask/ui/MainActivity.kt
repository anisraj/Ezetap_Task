package com.example.ezetaptask.ui

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.example.ezetaptask.R
import com.example.ezetaptask.databinding.ActivityMainBinding
import com.example.network.model.CustomUIResponse
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val map = HashMap<String, String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeApiError()
        subscribeCustomUIResponse()
        fetchCustomUI()
    }

    private fun fetchCustomUI() {
        viewModel.getCustomUIComponents()
    }

    private fun subscribeCustomUIResponse() {
        viewModel.routeCustomUIResponse.observe(this) {
            updateEzetapLogo(it.logoUrl)
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
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun createTextView(uiItem: CustomUIResponse.Uidata) {
        val view = layoutInflater.inflate(R.layout.textview_item, null)
        val header = view.findViewById<TextView>(R.id.tv_label)
        header.text = uiItem.value

        binding.llContainer.addView(view)
    }

    private fun createEditText(uiItem: CustomUIResponse.Uidata) {
        val view = layoutInflater.inflate(R.layout.edittext_item, null)
        val editText = view.findViewById<EditText>(R.id.ed_label)
        editText.hint = uiItem.hint
        if (uiItem.key == "text_phone") {
            editText.inputType = InputType.TYPE_CLASS_PHONE
        }
        editText.addTextChangedListener {
            uiItem.key?.let { it1 -> map[it1] = it.toString() }
        }

        binding.llContainer.addView(view)
    }

    private fun createButton(uiItem: CustomUIResponse.Uidata) {
        val view = layoutInflater.inflate(R.layout.button_item, null)
        val btn = view.findViewById<TextView>(R.id.button)
        btn.text = uiItem.value

        binding.llContainer.addView(view)

        btn.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("MAP", map)
            startActivity(intent)
        }
    }

    private fun updateEzetapLogo(logoUrl: String?) {
        if (!logoUrl.isNullOrEmpty() && logoUrl.isNotBlank()) {
            Glide.with(binding.ivEzetap.context)
                .load(logoUrl)
                .into(binding.ivEzetap)
        }
    }
}