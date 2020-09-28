package com.stream.app.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
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

        val email = sessionManager.userDetail["EMAIL"]!!
        val userAccessToken = sessionManager.userDetail["ACCESS_TOKEN"]
        val accessToken = "Bearer $userAccessToken"
        viewModel.getProfile(accessToken)

//        viewModel.getUserDetails().observe(this, Observer { data ->
//            Log.d(TAG, "picture: ${data.picture}")
//            Log.d(TAG, "fullname: ${data.fullname}")
//            Log.d(TAG, "username: ${data.username}")
//            Log.d(TAG, "bio: ${data.bio}")
//            Log.d(TAG, "jeniskelamin: ${data.jenisKelamin}")
//            Log.d(TAG, "tglLahir: ${data.tglLahir}")
//            Log.d(TAG, "status: ${data.status}")
//            Log.d(TAG, "social media: ${data.socmed}")
////            Glide.with(this)
////                .load(data.picture)
////                .error(R.drawable.common_google_signin_btn_icon_light)
////                .circleCrop()
////                .into(img_profile_Beranda)
////
////            tv_fullname_Beranda.setText(data.fullname.toString())
////            tv_username_Beranda.setText(data.username.toString())
////            tv_email_Beranda.setText(email)
////            tv_DOB_Beranda.text = data.tglLahir.toString()
////            tv_bio_Beranda.setText(data.bio)
////            tv_status_Beranda.setText(data.status)
////            if (data.jenisKelamin == "laki-laki") {
////                rb_Male_Beranda.isChecked = true
////                rb_Female_Beranda.isChecked = false
////            } else {
////                rb_Male_Beranda.isChecked = false
////                rb_Female_Beranda.isChecked = false
////            }
//        })

        tv_email_Beranda.setText(email)

        viewModel.getUserDetails().observe(this, Observer { details ->
//            Glide.with(this)
//                .load(details[0])
//                .into(img_profile_Beranda)
//            tv_fullname_Beranda.setText(details[1])
//            tv_username_Beranda.setText(details[2])
//            tv_email_Beranda.setText(email)
//            tv_bio_Beranda.setText(details[3])
//            tv_DOB_Beranda.text = details[4]
//            tv_status_Beranda.setText(details[5])
//            val picture = details[0]
//            val fullname = details[1]
//            val username = details[2]
//            val bio = details[3]
//            val jenisKelamin = details[4]
//            val tglLahir = details[5]
//            val status = details[6]
//            tv_fullname_Beranda.setText(fullname)
//            tv_username_Beranda.setText(username)
//            tv_bio_Beranda.setText(bio)
//            if (jenisKelamin == "laki-laki") {
//                rb_Female_Beranda.isChecked = false
//                rb_Male_Beranda.isChecked = true
//            } else {
//                rb_Female_Beranda.isChecked = true
//                rb_Male_Beranda.isChecked = false
//            }
//            tv_DOB_Beranda.text = tglLahir
//            tv_status_Beranda.setText(status)
            val userDetails = mutableListOf<String>()
            var id = 0
            details.forEach {
                Log.d(TAG, it)
                userDetails.add(it)
//                Log.d(TAG, fooList.toString())
                id++
                if (id == details.size) {
                    Log.d(TAG, id.toString())
                    Log.d(TAG, details.size.toString())

                    Glide.with(this)
                        .load(userDetails[0])
                        .circleCrop()
                        .into(img_profile_Beranda)

                    tv_fullname_Beranda.setText(userDetails[1])
                    tv_username_Beranda.setText(userDetails[2])
                    tv_bio_Beranda.setText(userDetails[3])
                    if (userDetails[4] == "laki-laki") {
                        rb_Female_Beranda.isChecked = false
                        rb_Male_Beranda.isChecked = true
                    } else {
                        rb_Female_Beranda.isChecked = true
                        rb_Male_Beranda.isChecked = false
                    }
                    tv_DOB_Beranda.text = userDetails[5]
                    tv_status_Beranda.setText(userDetails[6])
                }
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
