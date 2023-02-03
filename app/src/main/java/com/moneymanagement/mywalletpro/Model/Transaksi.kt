package com.moneymanagement.mywalletpro.Model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity
@Parcelize
data class Transaksi(
    val icon: Int,
    val transactionName: String,
    val userIdTransaction: Int,
    val nominal: Long,
    val date: Date,
    val type: Int,
    val category: String,
    @PrimaryKey(autoGenerate = false)
    var transactionId: String
): Parcelable
