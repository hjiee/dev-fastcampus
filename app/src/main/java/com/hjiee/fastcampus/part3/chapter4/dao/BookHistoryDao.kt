package com.hjiee.fastcampus.part3.chapter4.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.hjiee.fastcampus.part3.chapter4.model.BookHistory

@Dao
interface BookHistoryDao {

    @Query("SELECT * FROM BookHistory")
    fun getAll(): List<BookHistory>

    @Insert
    fun insertBookHistory(history: BookHistory)

    @Query("DELETE FROM BookHistory WHERE keyword = :keyword")
    fun delete(keyword: String)

}