package com.example.myfriendapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyFriends(
    @PrimaryKey(autoGenerate = true)
    val temanId: Int? = null,
    val nama: String,
    val kelamin: String,
    val email: String,
    val telp: String,
    val alamat: String
)
