package com.moneymanagement.mywalletpro.Model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryTransaction(
    val icon: Int,
    val thumbnail: Int,
    val categoryType: Int,
    val categoryName: String,
    val categoryGroupName: String
): Parcelable
