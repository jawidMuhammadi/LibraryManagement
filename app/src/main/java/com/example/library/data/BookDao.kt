package com.example.library.data

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface BookDao {

    @Query("SELECT * FROM BOOK_TABLE")
    fun getAllBooks(): LiveData<List<Book>>

    @RawQuery(observedEntities = [Book::class])
    fun getBooksByRaw(rawQuery: SupportSQLiteQuery): LiveData<List<Book>>

    @Query("select * from student_table,book_table where id =:studentId")
    fun getStudentAndAllBooks(studentId: Long): LiveData<StudentAndAllBooks>

    @Query("select * from student_table where id in(SELECT DISTINCT(student_id) from book_table)")
    fun getAllStudentsAndAllBorrowedBooks(): LiveData<List<StudentAndAllBooks>>

    @Query("SELECT * FROM book_table ORDER BY book_id desc limit 1")
    fun getRecentBook(): Book

    @Query("SELECT * FROM book_table ORDER BY book_id desc limit 1")
    fun getObserVerdRecentBook(): LiveData<Book>

    @Insert
    suspend fun insertBook(book: Book)

    @Update
    suspend fun update(book: Book)

    @Delete
    suspend fun delete(book: Book)

    @Query("SELECT * FROM book_table WHERE book_id=:bookId")
    fun getBookById(bookId: Long): LiveData<Book>

}