package com.hjiee.fastcampus.part3.chapter4.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Review(
    @PrimaryKey val uid: Int?,
    @ColumnInfo(name = "review") val review: String?
)