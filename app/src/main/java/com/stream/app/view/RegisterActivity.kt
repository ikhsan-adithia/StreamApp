package com.stream.app.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.DatePicker
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stream.app.R
import com.stream.app.toast
import com.stream.app.viewModel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var viewModel: RegisterViewModel

    private var myDate: Int = 0
    private var myMonth: Int = 0
    private var myYear: Int = 0

    var show = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        viewModel.getErrors().observe(this, Observer { errors ->
            tv_errors_Register.visibility = View.GONE
            tv_errors_Register.text = ""
            errors.forEach { error ->
                tv_errors_Register.append("$error\n")
            }
            tv_errors_Register.visibility = View.VISIBLE
            overlay_Register.visibility = View.GONE
        })

        viewModel.getAllowRegister().observe(this, Observer { allowed ->
            if (allowed) {
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                Handler().postDelayed({ show = false }, 100)
                overlay_Register.visibility = View.GONE
            }
        })

        if (show) overlay_Register.visibility = View.VISIBLE
        else overlay_Register.visibility = View.GONE

//        viewModel.getShowOverlay().observe(this, Observer { show ->
//            if (show) {
//                overlay_Register.visibility = View.VISIBLE
//            } else {
//                overlay_Register.visibility = View.GONE
//            }
//        })

        btn_PilihTanggal.setOnClickListener {
            val calendar = Calendar.getInstance()
            myDate = calendar.get(Calendar.DAY_OF_MONTH)
            myMonth = calendar.get(Calendar.MONTH)
            myYear = calendar.get(Calendar.YEAR)

            val datePickerDialog = DatePickerDialog(this, this, myYear, myMonth, myDate)
            datePickerDialog.show()
        }

        tv_haveAccount_Register.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btn_createAccount_Register.setOnClickListener {
            val fullname = et_fullname_Registrasi.text.toString()
            val username = et_username_Registrasi.text.toString()
            val password = et_password_Registrasi.text.toString()
            val confirmPassword = et_confirmPassword_Registrasi.text.toString()

            val checkRbGender = rb_gender_Registrasi.checkedRadioButtonId
            val rb: RadioButton = this.findViewById(checkRbGender)
            val gender = if (rb.text.toString() == "Male") {
                "laki-laki"
            } else {
                "perempuan"
            }

            val email = et_email_Registrasi.text.toString()
            val dob = tv_DOB_Register.text.toString()
            val phone = et_phoneNumber_Registrasi.text.toString()
            val cbKetentuan = cb_syaratKetentuan_Register.isChecked

            if (cbKetentuan) {
                show = true
                overlay_Register.visibility = View.VISIBLE
                viewModel.clearErrors()
                viewModel.validasiInput(
                    fullname,
                    username,
                    password,
                    confirmPassword,
                    gender,
                    email,
                    dob,
                    phone
                )

//                Log.d("Reg", fullname)
//                Log.d("Reg", username)
//                Log.d("Reg", password)
//                Log.d("Reg", confirmPassword)
//                Log.d("Reg", gender)
//                Log.d("Reg", email)
//                Log.d("Reg", dob)
//                Log.d("Reg", phone)
            } else {
                toast("Mohon centang Syarat dan Ketentuan", Toast.LENGTH_LONG)
            }

        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val finalmonth = month + 1
        val parser =  SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val formattedDate = formatter.format(parser.parse("$year-$finalmonth-$dayOfMonth")!!)
        tv_DOB_Register.text = formattedDate.toString()
    }

}
