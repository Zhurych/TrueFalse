package com.test.truefalse.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facts")
data class Fact internal constructor(
    @field:PrimaryKey
    val id: Int,
    val name: String,
    val isTrue: Boolean,
    val theme: String
)