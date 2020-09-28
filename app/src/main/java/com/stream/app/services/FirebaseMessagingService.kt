package com.stream.app.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.stream.app.toast

class FirebaseMessagingService: FirebaseMessagingService() {

    private var broadcaster: LocalBroadcastManager? = null

    private var messageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val message = intent?.extras?.getString("MESSAGE")
            toast(message.toString())
        }
    }

    override fun onCreate() {
//        currentToken()
        broadcaster = LocalBroadcastManager.getInstance(baseContext)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(messageReceiver, IntentFilter("MyData"))
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        handleMessage(remoteMessage)
    }

    private fun handleMessage(remoteMessage: RemoteMessage) {
        val handler = Handler(Looper.getMainLooper())

        handler.post {
//            toast("Handle Notification", Toast.LENGTH_LONG)
            remoteMessage.notification?.let {
                val intent = Intent("MyData")
                intent.putExtra("MESSAGE", it.body)
                broadcaster?.sendBroadcast(intent)
            }
        }
    }

    private fun currentToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Token", "getInstanceId failed", task.exception)
                return@addOnCompleteListener
            }

            val token = task.result?.token
//                val msg = getString(R.string.token_prefix, token)
//            et_token_Beranda.setText(token)
            Log.d("Token", token.toString())
        }
    }
}