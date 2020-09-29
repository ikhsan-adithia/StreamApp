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

//    private var userDetailList = Data()
//    private var _userDetails = MutableLiveData<Data>()

    init {
        this.errors.value = errorList
        this._userDetails.value = userDetailList
    }

    fun getProfile(userDetail: String) {
        if (_userDetails.value!!.isEmpty()) {
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
                    Log.d(TAG, response.isSuccessful.toString())
                    Log.d(TAG, response.message().toString())

                    if (response.isSuccessful) {
                        Log.d(TAG, "response.body()" + response.body().toString())
                        val responseSuccess = response.body()!!
                        val picture = responseSuccess.data!!.picture ?: ""
                        val fullname = responseSuccess.data.fullname ?: ""
                        val username = responseSuccess.data.username ?: ""
                        val bio = responseSuccess.data.bio ?: "  "
                        val jenisKelamin = responseSuccess.data.jenisKelamin ?: ""
                        val tglLahir = responseSuccess.data.tglLahir ?: ""
                        val status = responseSuccess.data.status ?: ""
                        val socmed = responseSuccess.data.socmed.toString()
//                        Log.d(TAG, "picture: $picture")
//                        Log.d(TAG, "fullname: $fullname")
//                        Log.d(TAG, "username: $username")
//                        Log.d(TAG, "bio: $bio")
//                        Log.d(TAG, "jeniskelamin: $jenisKelamin")
//                        Log.d(TAG, "tglLahir: $tglLahir")
//                        Log.d(TAG, "status: $status")
//                        Log.d(TAG, "social media: $socmed")
                        userDetailList.addAll(listOf(
                            picture,
                            fullname,
                            username,
                            bio,
                            jenisKelamin,
                            tglLahir,
                            status
                        ))

                        _userDetails.value = userDetailList
//                    Log.d(TAG, "userDetail: $_userDetails")
                    } else {
                        handleErrorResponse(response)
                    }
                }
            })
        }

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

    // Idea to scrap
    // TODO: Fix
//    private var mutableAdapter = GroupAdapter<ViewHolder>()
//    private var _mutableAdapter = MutableLiveData<GroupAdapter<ViewHolder>>()
//    fun getNotification(): LiveData<GroupAdapter<ViewHolder>> {
//        val adapter = GroupAdapter<ViewHolder>()
//        adapter.add(NotifItems(
//            dummyNotifModel(
//                "Tes notif #1",
//                "1 day(s) ago")))
//        adapter.add(NotifItems(
//            dummyNotifModel(
//                "Tes notif #2",
//                "5 day(s) ago")))
//        mutableAdapter = adapter
//        _mutableAdapter.value = mutableAdapter
//        return _mutableAdapter
//    }

//    private var dummyList = mutableListOf<dummyNotifModel>()
//    private var _dummyList = MutableLiveData<List<dummyNotifModel>>()
//    fun populateNotif() {
//        val dumdum = dummyNotifModel()
//        dumdum.text = "Tes #1"
//        dumdum.time = "1 day(s) ago"
//
//        dummyList.add(dumdum)
//        dummyList.add(dumdum)
//
//        Log.d(TAG, dummyList.size.toString())
//
//        _dummyList.value = dummyList
//    }

//    private var dummyList = mutableListOf<dummyNotifModel>()
//    private var notifList = MutableLiveData<List<NotifItems>>()
//    fun populateNotif() {
//        dummyList.add(dummyNotifModel("tes #1", "1 day(s) ago"))
//        dummyList.add(dummyNotifModel("tes #2", "2 day(s) ago"))
//
//        dummyList.forEach {
//
//        }
//    }

    companion object {
        const val TAG = "Beranda"
    }

//    fun getNotification(): LiveData<List<dummyNotifModel>> = _dummyList
}