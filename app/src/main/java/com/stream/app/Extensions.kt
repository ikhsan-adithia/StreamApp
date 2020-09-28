package com.stream.app

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

object Extensions {
    const val API_ALPHA = "https://alpha.streamgaming.id"
//    const val API_LOCALHOST = "http://localhost:8000/"
}

fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun showSnackbar(view: View, msg: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, msg, duration).show()
}