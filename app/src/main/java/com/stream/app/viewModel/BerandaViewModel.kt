package com.stream.app.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.stream.app.Extensions
import com.stream.app.models.ProfileResponse
import com.stream.app.repository.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BerandaViewModel(application: Application): AndroidViewModel(application) {
    private val gson = Gson()

    private var errorList = mutableListOf<String>()
    private var errors = MutableLiveData<List<String>>()

    private var userDetailList = mutableListOf<String>()
    private var _userDetails = MutableLiveData<List<String>>()

    init {
        this.errors.value = errorList
        this    ._userDetails.value = userDetailList
    }

    fun getProfile(userDetail: String) {
        val profile = ApiInterface.create(Extensions.API_ALPHA)
            .getProfile(userDetail)

        profile.enqueue(object : Callback<ProfileResponse> {
            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                Toast.makeText(
                    getApplication(),
                    t.message.toString(),
                    Toast.LENGTH_SHORT).show()
                Log.e(TAG, t.cause.toString())
                t.printStackTrace()
                t.stackTrace
                Log.e(TAG, "~~~~~~~~~~~~~~ERRORR~~~~~~~~~~~~~")
            }

            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                Log.d(TAG, response.code().toString())
                Toast.makeText(
                    getApplication(),
                    response.code().toString(),
                    Toast.LENGTH_SHORT).show()
                Log.d(TAG, response.isSuccessful.toString())
                Log.d(TAG, response.message().toString())

                if (response.isSuccessful) {
                    Log.d(TAG, "response.body()" + response.body().toString())
                    val responseSuccess = response.body()!!
                    val picture = responseSuccess.data!!.picture ?: ""
                    val fullname = responseSuccess.data.fullname ?: ""
                    val username = responseSuccess.data.username ?: ""
                    val bio = responseSuccess.data.bio ?: ""
                    val jenisKelamin = responseSuccess.data.jenisKelamin ?: ""
                    val tglLahir = responseSuccess.data.tglLahir ?: ""
                    val socmed = responseSuccess.data.socmed.toString()
                    Log.d(TAG, "social media: $socmed")
//                    userDetailList.add(picture)
//                    userDetailList.add(fullname)
//                    userDetailList.add(username)
//                    userDetailList.add(bio)
//                    userDetailList.add(jenisKelamin)
//                    userDetailList.add(tglLahir)
                    userDetailList.addAll(listOf(
                        picture,
                        fullname,
                        username,
                        bio,
                        jenisKelamin,
                        tglLahir
                    ))
                    _userDetails.value = userDetailList
                    Log.d(TAG, "userDetail: $userDetailList")
                } else {
                    handleErrorResponse(response)
                }
            }
        })
    }

    fun getUserDetails() = _userDetails as LiveData<List<String>>

    private fun handleErrorResponse(response: Response<ProfileResponse>) {
        val errorResponse =
            gson.fromJson(response.errorBody()!!.charStream(), ProfileResponse::class.java)
        Log.d(TAG, "errorResponse.status: ${errorResponse.status}")
        Log.d(TAG, "errorResponse.message: ${errorResponse.message}")
        val errorMessage = errorResponse.message.toString()
        errorList.add(errorMessage)
    }

    companion object {
        const val TAG = "Beranda"
    }
}