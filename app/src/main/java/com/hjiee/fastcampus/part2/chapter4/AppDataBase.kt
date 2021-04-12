package com.hjiee.fastcampus.part2.chapter4

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hjiee.fastcampus.part2.chapter4.dao.HistoryDao
import com.hjiee.fastcampus.part2.chapter4.model.History

@Database(entities = [History::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}