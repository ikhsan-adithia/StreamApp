package com.stream.app.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class RegisterRepository(val application: Application) {

    private var errorMsg = mutableListOf<String>()
    private val errors = MutableLiveData<List<String>>()

    init {
        this.errors.value = errorMsg
    }

    fun validasiInput(msg: MutableList<String>) {
        errors.value = mutableListOf()
        errorMsg = msg
        errors.value = errorMsg
    }

    fun getErrors(): LiveData<List<String>> {
        return errors
    }

    fun clearErrors() {
        errorMsg.clear()
        errors.value = errorMsg
    }
}