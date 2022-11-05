package com.example.ezetaptask.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.network.data.ApiService
import com.example.network.model.CustomUIResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val customUIResponse = MutableLiveData<CustomUIResponse>()
    val routeCustomUIResponse: LiveData<CustomUIResponse> = customUIResponse
    private val apiError = MutableLiveData<String>()
    val routeApiError: LiveData<String> = apiError

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        onError(exception)
    }

    fun getCustomUIComponents() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val apiResult = apiService.fetchCustomUI()
            if (apiResult.isSuccessful) {
                customUIResponse.postValue(apiResult.body())
            }
        }
    }

    private fun onError(throwable: Throwable) {
        apiError.postValue(throwable.message)
    }

}