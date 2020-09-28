package com.stream.app.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.stream.app.Extensions
import com.stream.app.models.RegisterResponse
import com.stream.app.repository.ApiInterface
import com.stream.app.repository.RegisterRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(application: Application): AndroidViewModel(application) {

    private val repository = RegisterRepository(application)
    private var errorList = mutableListOf<String>()

    private var registerStatus = false
    private var _registerStatus = MutableLiveData<Boolean>()

//    private var showOverlay = false
//    private var _showOverlay = MutableLiveData<Boolean>()

    fun validasiInput(
        fullname: String,
        username: String,
        password: String,
        confirmPassword: String,
        gender: String,
        email: String,
        dob: String,
        phone: String
    ) {
        if (fullname.isEmpty()) {
            errorList.add("Fullname masih kosong.")
        }
        if (username.isEmpty()) {
            errorList.add("Username masih kosong.")
        }
        if (password.isEmpty()) {
            errorList.add("Password masih kosong.")
        }
        if (confirmPassword.isEmpty()) {
            errorList.add("Repeat Password masih kosong.")
        }
        if (password != confirmPassword) {
            errorList.add("Password tidak sesuai.")
        }
        if (dob.isEmpty()) {
            errorList.add("Tanggal Lahir masih kosong.")
        }
        if (email.isEmpty()) {
            errorList.add("Email masih kosong.")
        }

        if (errorList.size == 0) {
//            Toast.makeText(getApplication(), "Registrasi", Toast.LENGTH_SHORT).show()

            val apiInterface = ApiInterface.create(Extensions.API_ALPHA).registerUser(
                fullname,
                username,
                password,
                confirmPassword,
                gender,
                email,
                dob,
                "",
                "",
                "",
                "",
                phone
            )

            apiInterface.enqueue(object : Callback<RegisterResponse> {
                override fun onFailure(
                    call: Call<RegisterResponse>,
                    t: Throwable
                ) {
                    Log.e(TAG, "ErrorResponse: ${t.message}")
                }

                override fun onResponse(
                    call: Call<RegisterResponse>,
                    registerResponse: Response<RegisterResponse>
                ) {
                    Log.d(TAG, "Response: ${registerResponse.code()}")
                    try {
                        if (registerResponse.isSuccessful) {
                            val resp = registerResponse.body().toString()
                            Log.d(TAG, resp)

                            registerStatus = true
                            _registerStatus.value = registerStatus
                        } else {
                            printErrorsMessage(registerResponse)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Log.e(TAG, e.cause.toString())
                    }

                    apiInterface.cancel()
                }
            })
        }

//        clearErrors()
        repository.validasiInput(errorList)
    }

    private fun printErrorsMessage(registerResponse: Response<RegisterResponse>) {
        val gson = Gson()
        val errorRegisterResponse: RegisterResponse =
            gson.fromJson(registerResponse.errorBody()!!.charStream(), RegisterResponse::class.java)

        errorRegisterResponse.message!!.let { errorList.add(it) }

        errorRegisterResponse.errors?.fullname.let {
            it?.forEach { fullname -> errorList.add(fullname.toString()) } }

        errorRegisterResponse.errors?.username.let {
            it?.forEach { username -> errorList.add(username.toString()) } }

        errorRegisterResponse.errors?.email.also {
            it?.forEach { email -> errorList.add(email.toString()) } }

        errorRegisterResponse.errors?.password.let {
            it?.forEach { password -> errorList.add(password.toString()) } }

        errorRegisterResponse.errors?.jenisKelamin.let {
            it?.forEach { gender -> errorList.add(gender.toString()) } }

        errorRegisterResponse.errors?.tglLahir.let {
            it?.forEach { tgl -> errorList.add(tgl.toString()) } }

        errorRegisterResponse.errors?.nomorHp.let {
            it?.forEach { nomor -> errorList.add(nomor.toString()) } }

        repository.validasiInput(errorList)

        Log.e(TAG, errorRegisterResponse.toString())
    }

    fun getErrors() = repository.getErrors()

    fun clearErrors() = repository.clearErrors()

    fun getAllowRegister() = _registerStatus as LiveData<Boolean>

    companion object {
        const val TAG = "RegisterActivity"
    }

}