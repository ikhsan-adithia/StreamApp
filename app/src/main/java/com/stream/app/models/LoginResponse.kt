package com.stream.app.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("verification")
	val verification: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("expires_in")
	val expiresIn: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	val errors: Errors? = null
)

data class User(

	@field:SerializedName("provinsi")
	val provinsi: String? = null,

	@field:SerializedName("kota")
	val kota: String? = null,

	@field:SerializedName("is_active")
	val isActive: String? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("is_verified")
	val isVerified: String? = null,

	@field:SerializedName("tgl_lahir")
	val tglLahir: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("nomor_hp")
	val nomorHp: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
