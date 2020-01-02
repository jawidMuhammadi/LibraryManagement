package com.example.library.data


import androidx.room.*
import java.util.*


@Entity(
    tableName = "book_table",
    foreignKeys = [ForeignKey(
        entity = Student::class,
        parentColumns = ["id"],
        childColumns = ["student_id"]
    )]
)
data class Book(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "book_id")
    val bookId: Long = 0,
    @ColumnInfo(name = "book_name")
    val bookName: String,
    val author: String?,
    @ColumnInfo(name = "student_id")
    val studentId: Long = 0,
    @ColumnInfo(name = "borrow_date")
    val borrowDate: Calendar = Calendar.getInstance(),
    @ColumnInfo(name = "return_date")
    val returnDate: Calendar = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, 1)
    }

)