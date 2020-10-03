package com.stream.app.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stream.app.models.NotificationData.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class NotificationData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int? = null,
    @ColumnInfo(name = ID_NOTIF)
    val idNotification: String? = null,
    @ColumnInfo(name = MESSAGE)
    val message: String? = null,
    @ColumnInfo(name = IS_READ)
    val isRead: Boolean? = null,
    @ColumnInfo(name = DATE)
    val date: String? = null
) {

    companion object {
        const val TABLE_NAME = "NotificationDB"
        const val ID = "id"
        const val ID_NOTIF = "id_notification"
        const val MESSAGE = "message"
        const val IS_READ = "is_read"
        const val DATE = "date"
    }
}