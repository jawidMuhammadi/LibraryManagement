package com.example.library.data

import android.app.Application
import androidx.lifecycle.LiveData

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

    fun getAllStudents(): LiveData<List<Student>> {
        return studentDao.getAllStudents()
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

    fun getAllStudentsAndAllBooks(): LiveData<List<StudentAndAllBooks>> {
        return bookDao.getAllStudentsAndAllBorrowedBooks()
    }


}