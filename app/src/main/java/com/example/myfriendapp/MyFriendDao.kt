package com.example.myfriendapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MyFriendDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun tambahTeman(friend: MyFriends)

    @Query("SELECT * FROM MyFriends")
    fun ambilSemuaTeman(): List<MyFriends>
}