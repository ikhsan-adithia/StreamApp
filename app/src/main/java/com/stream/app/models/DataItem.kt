package com.stream.app.models

import com.google.gson.annotations.SerializedName

data class DataItem(

    @field:SerializedName("is_read")
    val isRead: Int? = null,

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("id_notification")
    val idNotification: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)