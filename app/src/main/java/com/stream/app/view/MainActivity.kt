package com.stream.app.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.stream.app.R
import com.stream.app.repository.SessionManager

class MainActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        Handler().postDelayed({
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }, 1000)

        sessionManager = SessionManager(this)

        Handler().postDelayed({
            if (sessionManager.isLoggin) {
                startActivity(Intent(this, BerandaActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 1000)
    }
}
