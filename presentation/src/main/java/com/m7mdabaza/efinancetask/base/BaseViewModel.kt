package com.m7mdabaza.efinancetask.base

import android.util.Log
import androidx.lifecycle.ViewModel
import com.m7mdabaza.efinancetask.utils.Constants
import com.m7mdabaza.efinancetask.utils.NetworkState
import com.m7mdabaza.efinancetask.utils.SingleLiveEvent
import com.m7mdabaza.efinancetask.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() :
    ViewModel() {


    fun <T> runApi(
        _apiStateFlow: SingleLiveEvent<NetworkState>,
        block: Response<T>
    ) {
        _apiStateFlow.postValue(NetworkState.Loading)
        try {
            if (Utils.isInternetAvailable())
                kotlin.runCatching {
                    block
                }.onSuccess {
                    if (it.body() != null)
                        _apiStateFlow.postValue(NetworkState.Result(it.body()))
                    else {
                        _apiStateFlow.postValue(NetworkState.Error(Constants.Codes.UNKNOWN_CODE))
                    }
                }.onFailure {
                    when (it) {
                        is java.net.UnknownHostException ->
                            _apiStateFlow.postValue(NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE))

                        is java.net.ConnectException ->
                            _apiStateFlow.postValue(NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE))

                        else -> _apiStateFlow.postValue(NetworkState.Error(Constants.Codes.UNKNOWN_CODE))
                    }
                }
            else
                _apiStateFlow.postValue(NetworkState.Error(Constants.Codes.EXCEPTIONS_CODE))

        } catch (e: Exception) {
            Log.e(TAG, "runApi: ${e.message}")
        }


    }

    companion object {
        private val TAG = this::class.java.name

    }

}