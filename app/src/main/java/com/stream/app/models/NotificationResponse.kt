package com.stream.app.models

import com.google.gson.annotations.SerializedName

data class NotificationResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
