package com.stream.app.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.stream.app.Extensions
import com.stream.app.models.LoginResponse
import com.stream.app.repository.ApiInterface
import com.stream.app.repository.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application): AndroidViewModel(application) {

    private var sessionManager: SessionManager = SessionManager(application)

    private var errorList = mutableListOf<String>()
    private var errors = MutableLiveData<List<String>>()

    private var allowLogin: Boolean = false
    private var _allowLogin = MutableLiveData<Boolean>()

    init {
        errorList = mutableListOf()
        this.errors.value = errorList
        this._allowLogin.value = allowLogin
    }

    private val gson = Gson()

    fun validasiInputLogin(email: String, password: String) {
        errorList = mutableListOf()
        errors.value = errorList

        val login = ApiInterface.create(Extensions.API_ALPHA).loginUser(email, password)

        login.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, t.message!!)
                Log.e(TAG, t.cause!!.message.toString())
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d(TAG, "Response: ${response.code()}")
                if (response.isSuccessful) {
                    val loginResponse = response.body()!!
                    Log.d(TAG, "response.body(): $loginResponse")

                    handleResponseSuccess(response, loginResponse, email)
                } else {
                    // error response code
                    handleResponseError(response)
                }
            }
        })

    }

    private fun handleResponseError(response: Response<LoginResponse>) {
        val errorResponse =
            gson.fromJson(response.errorBody()!!.charStream(), LoginResponse::class.java)
        Log.e(TAG, "errorResponse: $errorResponse")

        errorList.add(errorResponse.message.toString())
        val emailError = errorResponse.errors?.email
        if (!emailError.isNullOrEmpty()) {
            emailError.forEach { errorList.add(it.toString()) }
        }

        val passError = errorResponse.errors?.password
        if (!passError.isNullOrEmpty()) {
            passError.forEach { errorList.add(it.toString()) }
        }

        errors.value = errorList
    }

    private fun handleResponseSuccess(
        response: Response<LoginResponse>,
        loginResponse: LoginResponse,
        email: String
    ) {
        if (loginResponse.status!!.toBoolean()) {
            val getFullname = loginResponse.user?.fullname
            val getUsername = loginResponse.user?.username
            val getAccessToken = loginResponse.accessToken
            val getUID = loginResponse.user?.userId!!
            sessionManager.createSession(
                email,
                getFullname,
                getUsername,
                getUID,
                getAccessToken
            ).let {
                allowLogin = true
                _allowLogin.value = allowLogin
            }
            Log.d(TAG, "email: $email")
            Log.d(TAG, "fullname: $getFullname")
            Log.d(TAG, "username; $getUsername")
            Log.d(TAG, "UID: $getUID")
        } else {
            val message = loginResponse.message!!
            val status = loginResponse.status!!
            Log.d(TAG, message)
//            Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
            errorList.add(message)
            Log.d(TAG, status)
        }
        errors.value = errorList
    }

    fun getAllowLogin() = _allowLogin as LiveData<Boolean>

    fun getErrors() = errors as LiveData<List<String>>

    companion object {
        const val TAG = "LoginActivity"
    }
}