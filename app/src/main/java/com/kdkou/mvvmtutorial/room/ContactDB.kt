package com.kdkou.mvvmtutorial.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDB: RoomDatabase() {
    abstract val contactDAO: ContactDAO

    companion object{
        @Volatile
        private var INSTANCE: ContactDB ?= null
        fun getInstance(context: Context): ContactDB{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDB::class.java,
                        "contacts_db"
                    ).build()
                }
                INSTANCE = instance
                return instance
            }
        }
    }
}