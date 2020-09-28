package com.stream.app.repository

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.stream.app.view.BerandaActivity
import com.stream.app.view.LoginActivity
import java.util.*

@SuppressLint("CommitPrefEdits")
class SessionManager constructor(private val context: Context) {
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor
    var PRIVATE_MODE = 0

    fun createSession(email: String?,
                      fullname: String?,
                      username: String?,
                      userId: String?,
                      accessToken: String?) {
        editor.putBoolean(LOGIN, true)
        editor.putString(EMAIL, email)
        editor.putString(FULLNAME, fullname)
        editor.putString(USERNAME, username)
        editor.putString(USERID, userId)
        editor.putString(ACCESS_TOKEN, accessToken)
        editor.apply()
    }

    val isLoggin: Boolean
        get() = sharedPreferences.getBoolean(
            LOGIN,
            false
        )

    fun checkLogin(context: Context) {
        if (!isLoggin) {
            val i = Intent(context, LoginActivity::class.java)
            context.startActivity(i)
            (context as BerandaActivity).finish()
        }
    }

    val userDetail: HashMap<String, String?>
        get() {
            val user =
                HashMap<String, String?>()
            user[EMAIL] = sharedPreferences.getString(
                EMAIL,
                null
            )
            user[FULLNAME] = sharedPreferences.getString(
                FULLNAME,
                null
            )
            user[USERNAME] = sharedPreferences.getString(
                USERNAME,
                null
            )
            user[USERID] = sharedPreferences.getString(
                USERID,
                null
            )
            user[ACCESS_TOKEN] = sharedPreferences.getString(
                ACCESS_TOKEN,
                null
            )
            return user
        }

    fun logout() {
        editor.clear()
        editor.commit()
    }

    companion object {
        private const val PREF_NAME = "LOGIN"
        private const val LOGIN = "IS_LOGIN"
        const val EMAIL = "EMAIL"
        const val FULLNAME = "FULLNAME"
        const val USERNAME = "USERNAME"
        const val USERID = "USERID"
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
    }

    init {
        sharedPreferences = context.getSharedPreferences(
            PREF_NAME,
            PRIVATE_MODE
        )
        editor = sharedPreferences.edit()
    }
}