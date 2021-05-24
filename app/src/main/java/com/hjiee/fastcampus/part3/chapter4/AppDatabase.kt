package com.hjiee.fastcampus.part3.chapter4

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hjiee.fastcampus.part3.chapter4.dao.BookHistoryDao
import com.hjiee.fastcampus.part3.chapter4.dao.ReviewDao
import com.hjiee.fastcampus.part3.chapter4.model.BookHistory
import com.hjiee.fastcampus.part3.chapter4.model.Review

@Database(entities = [BookHistory::class, Review::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): BookHistoryDao
    abstract fun reviewDao(): ReviewDao
}

//fun getAppDatabase(context: Context): AppDatabase {
//
//    val migration_1_2 = object : Migration(1,2) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("CREATE TABLE `REVIEW` (`id` INTEGER, `review` TEXT," + "PRIMARY KEY(`id`))")
//        }
//
//    }
//
//    return Room.databaseBuilder(
//        context,
//        AppDatabase::class.java,
//        "BookSearchDB"
//    )
//        .addMigrations(migration_1_2)
//        .build()
//}