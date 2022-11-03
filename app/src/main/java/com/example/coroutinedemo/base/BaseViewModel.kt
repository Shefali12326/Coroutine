package com.example.coroutinedemo.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

open class BaseViewModel :ViewModel() {
    var showMessage = MutableLiveData<String>()
    var apiError = MutableLiveData<String>()
    var onFailure = MutableLiveData<String>()
    var isLoading = MutableLiveData<Boolean>()
}