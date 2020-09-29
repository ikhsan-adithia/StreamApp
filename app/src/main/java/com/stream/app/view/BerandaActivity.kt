package com.stream.app.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.iid.FirebaseInstanceId
import com.stream.app.R
import com.stream.app.models.DummyNotifModel
import com.stream.app.models.NotifItems
import com.stream.app.repository.SessionManager
import com.stream.app.viewModel.BerandaViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.bottomsheet_notif.*
import kotlinx.android.synthetic.main.content_main.*

class BerandaActivity : AppCompatActivity() {

    private lateinit var viewModel: BerandaViewModel
    private lateinit var sessionManager: SessionManager

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

        tv_email_Beranda.setText(email)

        viewModel.getUserDetails().observe(this, Observer { details ->
            showUserProfile(details)
        })

//        viewModel.populateNotif()
//        viewModel.getNotification().observe(this, Observer {
//            if (it.size < 0) {
//                Log.d(TAG, "it Size${it.size}")
//                val adapter = GroupAdapter<ViewHolder>()
//                it.forEach {  dummyNotif ->
//                    Log.d(TAG, "dummyNotif ${dummyNotif.time}")
//                    adapter.add(NotifItems(dummyNotif))
//                }
//                rv_notification.adapter = adapter
//            }
//        })
        foo()

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

        val sheetBehavior = BottomSheetBehavior.from(bottomsheet_notif)
//        sheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED
        btn_notifBell_Beranda.setOnClickListener {
            Log.d(TAG, sheetBehavior.state.toString())
//            val dialogBottom =layoutInflater.inflate(R.layout.bottomsheet_notif, null)
//            val bottomSheetDialog = BottomSheetDialog(this)
//            bottomSheetDialog.setContentView(dialogBottom)
//            bottomSheetDialog.show()
            if (sheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
                sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }
    }

    private fun foo() {
        // TODO: Tes #1 => observe mutableListOf<Notifitems>()

        val fooList = mutableListOf<DummyNotifModel>()
        fooList.add(DummyNotifModel("tes #3", "2 day(s) ago"))
        fooList.add(DummyNotifModel("tes #4", "2 day(s) ago"))
        val fiiList = mutableListOf<NotifItems>()
        fooList.forEach {
            fiiList.add(NotifItems(it))
        }
//        fooList.forEach {
//            adapter.add(NotifItems(it))
//        }
//        adapter.add(NotifItems(dummyNotifModel("tes #1", "1 day(s) ago")))
//        adapter.add(NotifItems(dummyNotifModel("tes #2", "1 day(s) ago")))
//
//        rv_notification.adapter = adapter
        val adapter = GroupAdapter<ViewHolder>().apply {
            this.addAll(fiiList)
        }

        rv_notification.apply {
            this.adapter = adapter
        }
    }

    private fun showUserProfile(details: List<String>) {
        val userDetails = mutableListOf<String>()
        var id = 0
        details.forEach {
//            Log.d(TAG, it)
            userDetails.add(it)
    //                Log.d(TAG, fooList.toString())
            id++
            if (id == details.size) {
//                Log.d(TAG, id.toString())
//                Log.d(TAG, details.size.toString())

                Glide.with(this)
                    .load(userDetails[0])
                    .circleCrop()
                    .into(img_profile_Beranda)

                tv_fullname_Beranda.setText(userDetails[1])
                tv_username_Beranda.setText(userDetails[2])
                tv_myName_Beranda.text = userDetails[2]
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
    }

    companion object {
        const val TAG = "BerandaActivity"
    }

}
