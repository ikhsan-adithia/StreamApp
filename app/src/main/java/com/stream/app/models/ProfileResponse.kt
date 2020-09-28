package com.stream.app.models

data class ProfileResponse(
	val data: Data? = null,
	val status: Boolean? = null
)

data class Data(
	val bio: Any? = null,
	val id: String? = null,
	val fullname: String? = null,
	val jenisKelamin: String? = null,
	val socmed: List<Any?>? = null,
	val tglLahir: String? = null,
	val age: Int? = null,
	val picture: String? = null,
	val username: String? = null,
	val status: String? = null
)

