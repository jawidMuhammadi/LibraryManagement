package com.example.library.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.library.utils.PAGE_SIZE


class StudentRepository private constructor(
    private val studentDao: StudentDao,
    private val bookDao: BookDao
) {

    companion object {
        @Volatile
        private var INSTANCE: StudentRepository? = null

        fun getInstance(context: Application): StudentRepository {
            return INSTANCE
                ?: StudentRepository(
                    AppDataBase.getInstance(context).studentDao(),
                    AppDataBase.getInstance(context).bookDao()
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

    suspend fun insertStudent(student: Student) {
        studentDao.inserAll(student)
    }

    fun getStudentById(id: Long): LiveData<Student> {
        return studentDao.getStudentById(id)
    }

    fun getStudentAndAllBooksById(id: Long): LiveData<StudentAndAllBooks> {

        return bookDao.getStudentAndAllBooks(id)
    }

    fun getAllStudentsAndAllBooks(): LiveData<PagedList<StudentAndAllBooks>> {
        val source = bookDao.getAllStudentsAndAllBorrowedBooks()
        return LivePagedListBuilder<Int, StudentAndAllBooks>(
            source, PAGE_SIZE
        ).build()
    }


}