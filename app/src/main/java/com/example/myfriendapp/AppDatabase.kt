package com.example.myfriendapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyFriends::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun myFriendDao(): MyFriendDao

    companion object{
        var INSTANCE: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase?{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "MyFriendAppDB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}