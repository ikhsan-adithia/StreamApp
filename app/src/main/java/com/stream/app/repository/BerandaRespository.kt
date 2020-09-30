package com.stream.app.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stream.app.models.DummyNotifModel
import com.stream.app.models.NotifItems

class BerandaRespository(private val application: Application) {

    private var dummyList = mutableListOf<DummyNotifModel>()
    private var notifList = mutableListOf<NotifItems>()

    private var _notifList = MutableLiveData<List<NotifItems>>()

    fun populateNotification()
//            : LiveData<List<NotifItems>>
    {
        dummyList.add(DummyNotifModel("tes #1", "1 day(s) ago"))
        dummyList.add(DummyNotifModel("tes #2", "2 day(s) ago"))
        for (i in 3 .. 10) {
            dummyList.add(DummyNotifModel("tes #$i", "${i + 2} day(s) ago"))
        }
        var id = 0
        dummyList.forEach {
            id++
            notifList.add(NotifItems(it))
            if (id == dummyList.size) {
                _notifList.value = notifList
            }
        }

//        return _notifList
    }

    fun getNotification() = _notifList as LiveData<List<NotifItems>>
}