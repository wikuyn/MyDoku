package com.moneymanagement.mywalletpro.Model.Relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.moneymanagement.mywalletpro.Model.Transaksi
import com.moneymanagement.mywalletpro.Model.User

data class UserWithTransaksi(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "transactionId",
        associateBy = Junction(TransaksiWithUserRef::class)
    )
    val transaksi: List<Transaksi>
)