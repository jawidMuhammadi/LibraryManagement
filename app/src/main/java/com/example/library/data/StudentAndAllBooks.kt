package com.example.library.data

import androidx.lifecycle.LiveData
import androidx.room.Embedded
import androidx.room.Relation
import java.util.*

data class StudentAndAllBooks(
    @Embedded
    val student: Student,

    @Relation(parentColumn = "id", entityColumn = "student_id")
    val borrowedBooks: List<Book> = emptyList()
) {
    var stBkId: String? = UUID.randomUUID().toString()
}