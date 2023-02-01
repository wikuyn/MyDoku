package com.moneymanagement.mywalletpro.Model.Relation

import androidx.room.Entity

@Entity(primaryKeys = ["transactionId","userId"])
data class TransaksiWithUserRef(
    val transactionId: String,
    val userId: Int
)
