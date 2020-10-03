package com.stream.app.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stream.app.Extensions
import com.stream.app.Extensions.Beranda_TAG
import com.stream.app.models.DataItem
import com.stream.app.models.DummyNotifModel
import com.stream.app.models.NotifItems
import com.stream.app.models.NotificationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BerandaRespository(private val application: Application) {

    // DummyNotifModel
    private var dummyList = mutableListOf<DataItem>()
    private var notifList = mutableListOf<NotifItems>()

    private var _notifList = MutableLiveData<List<NotifItems>>()

    fun populateNotification(accessToken: String)
//            : LiveData<List<NotifItems>>
    {
//        dummyList.add(DummyNotifModel("tes #1", "1 day(s) ago"))
//        dummyList.add(DummyNotifModel("tes #2", "2 day(s) ago"))
//        for (i in 3 .. 10) {
//            dummyList.add(DummyNotifModel("tes #$i", "${i + 2} day(s) ago"))
//        }

        val notif = ApiInterface.create(Extensions.API_ALPHA)
        notif.getNotifications(accessToken).enqueue(object : Callback<NotificationResponse> {
            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                Log.e(Beranda_TAG, t.message.toString())
                Log.e(Beranda_TAG, t.cause.toString())
                t.printStackTrace()
                Log.e(Beranda_TAG, "~~~~~~~~~~Error~~~~~~~")
            }

            override fun onResponse(
                call: Call<NotificationResponse>,
                response: Response<NotificationResponse>
            ) {
                Log.d(Beranda_TAG, "Response: ${response.code()}")
                Log.d(Beranda_TAG, "ResponseBody: ${response.body().toString()}")
                val responseData = response.body()!!.data!!
                if (response.code() == 200) {
                    responseData.forEach { dataItem ->
                        dummyList.add(dataItem!!)
                    }

                    var id = 0
                    dummyList.forEach {
                        id++
                        notifList.add(NotifItems(it, application))
                        if (id == dummyList.size) {
                            _notifList.value = notifList
                        }
                    }
                }
            }
        })

//        var id = 0
//        dummyList.forEach {
//            id++
//            notifList.add(NotifItems(it))
//            if (id == dummyList.size) {
//                _notifList.value = notifList
//            }
//        }

//        return _notifList
    }

    fun getNotification() = _notifList as LiveData<List<NotifItems>>
}