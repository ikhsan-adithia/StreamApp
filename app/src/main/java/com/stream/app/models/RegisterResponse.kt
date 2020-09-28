package com.stream.app.models

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
	val message: String? = null,
	val errors: Errors? = null
)

data class Errors(
	@SerializedName("fullname")
	val fullname: List<String?>? = null,
	@SerializedName("username")
	val username: List<String?>? = null,
	@SerializedName("email")
	val email: List<String?>? = null,
	@SerializedName("password")
	val password: List<String?>? = null,
	@SerializedName("jenis_kelamin")
	val jenisKelamin: List<String?>? = null,
	@SerializedName("tgl_lahir")
	val tglLahir: List<String?>? = null,
	@SerializedName("nomor_hp")
	val nomorHp: List<String?>? = null
)

