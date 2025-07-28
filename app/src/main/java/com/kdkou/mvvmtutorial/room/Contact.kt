package com.kdkou.mvvmtutorial.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
data class Contact(
    @PrimaryKey(true)
    val id: Int,

    var name: String,

    var email: String
)
