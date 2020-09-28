package com.stream.app.models

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)

data class SocmedItem(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("sosmed")
	val sosmed: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class Data(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("bio")
	val bio: String? = null,

	@field:SerializedName("jenis_kelamin")
	val jenisKelamin: String? = null,

	@field:SerializedName("tgl_lahir")
	val tglLahir: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("socmed")
	val socmed: List<SocmedItem?>? = null
) {
	constructor():
			this("", "", "", "", "", "", "", "")
}
