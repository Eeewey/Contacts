package com.example.contacts

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ContEntity::class],
    version = 1
)

abstract class ContDb : RoomDatabase() {

    abstract fun todoDao(): ContDao

    companion object {

        @Volatile
        private var INSTANCE: ContDb? = null

        fun getDatabase(context: Context): ContDb {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildDatabase(context)
                }
            }

            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): ContDb {
            return Room.databaseBuilder(
                context.applicationContext,
                ContDb::class.java,
                "contdb"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}