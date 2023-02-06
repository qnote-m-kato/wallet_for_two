package com.example.walletfortwo.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.walletfortwo.commaToArray
import com.example.walletfortwo.model.ExpenditureItem
import com.example.walletfortwo.model.GiveCost
import com.example.walletfortwo.model.LifeCost
import com.example.walletfortwo.model.User
import com.example.walletfortwo.model.database.dao.ExpenditureItemDao
import com.example.walletfortwo.model.database.dao.GiveCostDao
import com.example.walletfortwo.model.database.dao.LifeCostDao
import com.example.walletfortwo.model.database.dao.UserDao
import com.example.walletfortwo.toCommaString

@Database(entities = [User::class, LifeCost::class, GiveCost::class, ExpenditureItem::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun UserDao() : UserDao
    abstract fun LifeCostDao() : LifeCostDao
    abstract fun GiveCostDao() : GiveCostDao
    abstract fun ExpenditureItemDao(): ExpenditureItemDao

    companion object {
        private val DB_NAME = "DB"
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: callDatabase(context).also { instance = it }
        }
        /**
         * DBの呼び出し
         */
        fun callDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration().build()
        }

    }
}
//DBの中にListを持てない
class TypeConverter {
    @androidx.room.TypeConverter
    fun commaStringToList(commaString: String): Array<String> = commaString.commaToArray()

    @androidx.room.TypeConverter
    fun listToComma(list: Array<String>) = list.toCommaString()
}