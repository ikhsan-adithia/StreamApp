package com.stream.app.localStorage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stream.app.localStorage.NotificationDatabase.Companion.DB_VERSION
import com.stream.app.models.NotificationData
import com.stream.app.repository.NotificationDAO

@Database(entities = [NotificationData::class], version = DB_VERSION)
abstract class NotificationDatabase: RoomDatabase() {
    abstract fun notifDao(): NotificationDAO

    companion object {
        @Volatile
        private var databaseInstance: NotificationDatabase? = null

        fun getDatabaseInstance(context: Context): NotificationDatabase =
            databaseInstance ?: synchronized(this) {
                databaseInstance ?: buildDatabaseInstance(context).also {
                    this.databaseInstance = it
                }
            }

        private fun buildDatabaseInstance(context: Context) =
            Room.databaseBuilder(context, NotificationDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

        const val DB_NAME = "NotificationDB"
        const val DB_VERSION = 1
    }
}