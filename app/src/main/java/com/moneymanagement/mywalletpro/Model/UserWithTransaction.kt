package com.moneymanagement.mywalletpro.Model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithTransaction(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userIdTransaction"
    )
    val transaksi: List<Transaksi>
)
