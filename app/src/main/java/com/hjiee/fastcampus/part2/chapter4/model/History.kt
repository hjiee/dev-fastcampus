package com.hjiee.fastcampus.part2.chapter4.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
        @PrimaryKey val uid: Int?,
        val expression: String?,
        val result: String?
)