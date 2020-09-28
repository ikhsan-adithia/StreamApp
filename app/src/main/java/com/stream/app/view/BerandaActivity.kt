package com.stream.app.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.iid.FirebaseInstanceId
import com.stream.app.R
import com.stream.app.repository.SessionManager
import com.stream.app.viewModel.BerandaViewModel
import kotlinx.android.synthetic.main.activity_beranda.*

class BerandaActivity : AppCompatActivity() {

    private lateinit var viewModel: BerandaViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beranda)

        viewModel = ViewModelProvider(this).get(BerandaViewModel::class.java)

        sessionManager = SessionManager(this)

        sessionManager.checkLogin(this)

        val userDetail = sessionManager.userDetail["ACCESS_TOKEN"]
//        Log.d("Beranda", userDetail.toString())
        // TODO: fetch data profile with access token

        viewModel.getProfile(userDetail!!)

        btn_token_Beranda.setOnClickListener {
            FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("Beranda", "getInstanceId failed", task.exception)
                    return@addOnCompleteListener
                }

                val token = task.result?.token
//                val msg = getString(R.string.token_prefix, token)
                et_token_Beranda.setText(token)
                Log.d("Beranda", token.toString())
            }
        }

        btn_logout_Beranda.setOnClickListener {
            sessionManager.logout()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}
