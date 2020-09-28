package com.stream.app.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.iid.FirebaseInstanceId
import com.stream.app.R
import com.stream.app.repository.SessionManager
import com.stream.app.viewModel.BerandaViewModel
import kotlinx.android.synthetic.main.activity_beranda.*

class BerandaActivity : AppCompatActivity() {

    private lateinit var viewModel: BerandaViewModel
    private lateinit var sessionManager: SessionManager

//    private val imgProfileView = findViewById<ImageView>(R.id.img_profile_Beranda)
//    private val fullnameView = findViewById<TextView>(R.id.tv_fullname_Beranda)
//    private val usernameView = findViewById<TextView>(R.id.tv_username_Beranda)
//    private val bioView = findViewById<TextView>(R.id.tv_bio_Beranda)
//    private val genderView = findViewById<RadioGroup>(R.id.rg_gender_Beranda)
//    private val tglLahirView = findViewById<TextView>(R.id.tv_DOB_Beranda)
    // out of loop
//    private val emailProfile = findViewById<View>(R.id.tv_email_Beranda)

//    val viewList = listOf<View>(
//        imgProfileView,
//        fullnameView,
//        usernameView,
//        bioView,
//        genderView,
//        tglLahirView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beranda)

        viewModel = ViewModelProvider(this).get(BerandaViewModel::class.java)

        sessionManager = SessionManager(this)

        sessionManager.checkLogin(this)

        val userDetail = sessionManager.userDetail["ACCESS_TOKEN"]
        val accessToken = "Bearer $userDetail"
        viewModel.getProfile(accessToken)

        viewModel.getUserDetails().observe(this, Observer { details ->
            //            for (i in 0..it.size) {
//                viewList[i]
//            }
            details.forEach {
                Log.d(TAG, it)
            }
        })

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

    companion object {
        const val TAG = "BerandaActivity"
    }

}
