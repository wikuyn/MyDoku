package com.moneymanagement.mywalletpro.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val userName : String,
    val profilePicture : String,
    val balance : String,
    val income : String,
    val spending : String       
){
    @PrimaryKey(autoGenerate = true)
    var userId : Int = 0
}
