package com.moneymanagement.mywalletpro

import androidx.lifecycle.LiveData
import androidx.room.*
import com.moneymanagement.mywalletpro.Model.Transaksi
import com.moneymanagement.mywalletpro.Model.User
import com.moneymanagement.mywalletpro.Model.UserWithTransaction
import com.moneymanagement.mywalletpro.Utils.OnCategoryClick

@Dao
interface UserDao {

    @Insert()
    fun createUsar(newUser : User)

    @Query("SELECT *FROM `transaksi`")
    fun getAllTransaction(): LiveData<List<Transaksi>>

    @Transaction
    @Query("SELECT *FROM 'user'")
    fun getUserWithTransaction(): LiveData<UserWithTransaction>

    @Query("SELECT nominal FROM `transaksi` WHERE type = :type")
    fun getSpending(type: Int):LiveData<List<Long>>

    @Query("SELECT *FROM `transaksi` WHERE type = :type")
    fun getSpendingList(type: Int):LiveData<List<Transaksi>>

    @Query("SELECT nominal FROM 'transaksi' WHERE category = :categoryName")
    fun getNominalByCategory(categoryName: String): LiveData<List<Long>>

    @Query("SELECT balance FROM user WHERE userId = :userId")
    fun getBalance(userId : Int):LiveData<String>

    @Query("SELECT *FROM user WHERE userName = :username")
    fun getUserLogin(username : String):LiveData<User>

    @Insert()
    fun insertNewTransaction(transaksi: Transaksi)

    @Update
    fun updateTransaction(transaksi: Transaksi)

    @Delete()
    fun deleteTransaction(transaksiId : Transaksi)

    @Transaction
    @Query("SELECT * FROM User")
    fun getUsersWithPlaylists(): LiveData<List<UserWithTransaction>>
}