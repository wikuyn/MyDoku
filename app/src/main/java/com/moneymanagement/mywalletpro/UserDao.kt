package com.moneymanagement.mywalletpro

import androidx.lifecycle.LiveData
import androidx.room.*
import com.moneymanagement.mywalletpro.Model.Relation.TransaksiWithUserRef
import com.moneymanagement.mywalletpro.Model.Relation.UserWithTransaksi
import com.moneymanagement.mywalletpro.Model.Transaksi
import com.moneymanagement.mywalletpro.Model.User
import java.util.*

@Dao
interface UserDao {

    @Insert()
    fun createUsar(newUser : User)

    @Transaction
    @Query("SELECT *FROM 'user'")
    fun getUserWithTransaction(): LiveData<UserWithTransaksi>

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
    fun insertNewTransaction(transaksi: Transaksi): Long

    @Update
    fun updateTransaction(transaksi: Transaksi)

    @Delete()
    fun deleteTransaction(transaksiId: Transaksi)

    @Query("DELETE FROM transaksiwithuserref WHERE transactionId = :transaksiId")
    fun deleteTransaksiOfUser(transaksiId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserTransaksiCrossRef(crosref: TransaksiWithUserRef)

    @Query("SELECT *FROM user WHERE userId = :userid")
    fun getTransaksiOfUser(userid: Int): LiveData<UserWithTransaksi>

    @Query("SELECT *FROM transaksi ORDER BY date DESC")
    fun getAllTransaction(): LiveData<List<Transaksi>>

    @Query("SELECT *FROM transaksi WHERE dateString = :todayDate ORDER BY date DESC")
    fun getTransaksiOfUserToday(todayDate: String): LiveData<List<Transaksi>>

    @Query("SELECT *FROM transaksi WHERE week =:currentWeek ORDER BY date DESC")
    fun getTransaksiOfUserWeek(currentWeek: Int): LiveData<List<Transaksi>>

    @Query("SELECT *FROM transaksi WHERE month =:currentMonth ORDER BY date DESC")
    fun getTransaksiOfUserMonth(currentMonth: Int): LiveData<List<Transaksi>>
}