package com.stream.app.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import com.stream.app.Extensions
import com.stream.app.models.ProfileResponse
import com.stream.app.repository.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BerandaViewModel(application: Application): AndroidViewModel(application) {
    val gson = Gson()

    fun getProfile(userDetail: String) {
        val profile = ApiInterface.create(Extensions.API_ALPHA).getProfile(userDetail)

        profile.enqueue(object : Callback<ProfileResponse> {
            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
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
                Log.d(TAG, response.isSuccessful.toString())
                Log.d(TAG, response.message().toString())

                if (response.isSuccessful) {
                    Log.d(TAG, "response.body()" + response.body().toString())
                } else {
                    val errorResponse =
                        gson.fromJson(response.errorBody()!!.charStream(), ProfileResponse::class.java)
                    Log.d(TAG, "errorResponse.status: ${errorResponse.status}")
                    Log.d(TAG, "errorResponse.message: ${errorResponse.message}")
                }
            }
        })
    }

    companion object {
        const val TAG = "Beranda"
    }
}