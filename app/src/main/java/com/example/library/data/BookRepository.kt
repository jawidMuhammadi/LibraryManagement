package com.example.library.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.library.utils.PAGE_SIZE
import com.example.library.utils.scheduleNotification

class BookRepository private constructor(
    private val bookDao: BookDao,
    private val studentDao: StudentDao
) {

    companion object {
        @Volatile
        private var INSTANCE: BookRepository? = null
        private lateinit var context: Application

        fun getInstance(context: Application): BookRepository {
            this.context = context
            return INSTANCE ?: BookRepository(
                AppDataBase.getInstance(context).bookDao(),
                AppDataBase.getInstance(context).studentDao()
            ).also {
                INSTANCE = it
            }
        }
    }

    fun getAllStudents(): LiveData<PagedList<Student>> {
        val source = studentDao.getAllStudents()
        return LivePagedListBuilder<Int, Student>(
            source, PAGE_SIZE
        ).build()
    }

    fun getAllBooks(): LiveData<PagedList<Book>> {
        val source = bookDao.getAllBooks()
        return LivePagedListBuilder<Int, Book>(
            source, PAGE_SIZE
        ).build()
    }

    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)

        val recentBook = bookDao.getRecentBook()
        scheduleNotification(context, recentBook.studentId, recentBook.returnDate.timeInMillis)
    }

    fun getStudentAndAllBooks(id: Long): LiveData<StudentAndAllBooks> {
        return bookDao.getStudentAndAllBooks(id)
    }

    fun getBookById(bookId: Long): LiveData<Book> {
        return bookDao.getBookById(bookId)
    }

    suspend fun deleteBook(bookId: Book?) {
        bookId?.let { bookDao.delete(it) }
    }
}