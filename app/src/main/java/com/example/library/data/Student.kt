package com.example.library.data

import androidx.room.Entity
import androidx.room.*


@Entity(tableName = "student_table")
data class Student(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val studentId: Long = 0,
    val name: String,
    @ColumnInfo(name = "class_name")
    val className: String?
)