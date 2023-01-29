package com.moneymanagement.mywalletpro

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moneymanagement.mywalletpro.Model.Relation.TransaksiWithUserRef
import com.moneymanagement.mywalletpro.Model.Transaksi
import com.moneymanagement.mywalletpro.Model.User
import com.moneymanagement.mywalletpro.Utils.DateConverter

@Database(entities = [
    User::class,
    Transaksi::class,
    TransaksiWithUserRef::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    companion object{
        private const val DATABASE_NAME : String = "my_doku"
        private var appDatabase : AppDatabase? = null

        fun getDatabaseInstance(context : Context): AppDatabase{
            if (appDatabase == null){
                appDatabase = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DATABASE_NAME)
                    .build()
            }
            return appDatabase as AppDatabase
        }
    }
    abstract fun userDao() : UserDao
}