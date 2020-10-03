package com.stream.app.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stream.app.R
import com.stream.app.viewModel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        tv_errors_Login.visibility = View.GONE

        viewModel.getAllowLogin().observe(this, Observer {
            if (it) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })

        viewModel.getErrors().observe(this, Observer { errors ->
            displayErrors(errors)
        })

        tv_createAccount_Login.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btn_Login.setOnClickListener {
            val email = et_email_Login.text.toString()
            val password = et_password_Login.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // login
                viewModel.validasiInputLogin(email, password)
            }
        }
    }

    private fun displayErrors(errors: List<String>) {
        tv_errors_Login.visibility = View.GONE
        tv_errors_Login.text = ""
        errors.forEach { error ->
            tv_errors_Login.append("$error\n")
        }
        tv_errors_Login.visibility = View.VISIBLE
    }
}
