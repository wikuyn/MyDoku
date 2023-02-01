package com.moneymanagement.mywalletpro.Model.Relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.moneymanagement.mywalletpro.Model.Transaksi
import com.moneymanagement.mywalletpro.Model.User

data class TransaksiWithUser(
    @Embedded val transaksi: Transaksi,
    @Relation(
        parentColumn = "transactionId",
        entityColumn = "userId",
        associateBy = Junction(TransaksiWithUserRef::class)
    )
    val user: List<User>
)
