package com.example.library.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.library.utils.scheduleNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    fun getAllStudents(): LiveData<List<Student>> {
        return studentDao.getAllStudents()
    }

    fun getAllBooks(): LiveData<List<Book>> {
        return bookDao.getAllBooks()
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