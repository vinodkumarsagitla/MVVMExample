package com.vks.mvvmexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class NetworkViewModel @Inject constructor() :ViewModel() {
    lateinit var connectedStatus: LiveData<Boolean>
}