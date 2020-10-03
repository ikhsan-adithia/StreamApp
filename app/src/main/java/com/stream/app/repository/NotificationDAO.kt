package com.stream.app.repository

import androidx.room.*
import com.stream.app.models.NotificationData
import com.stream.app.models.NotificationData.Companion.TABLE_NAME
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface NotificationDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun addNotification(data: List<NotificationData>): Completable

    @Query("SELECT * FROM ${TABLE_NAME}")
    fun getAllNotif(): Single<List<NotificationData>>

    @Delete
    fun deleteNotification(data: NotificationData): Completable

//    @Update
//    fun updateNo
}