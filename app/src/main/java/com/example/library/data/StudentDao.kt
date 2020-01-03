package com.example.library.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*


@Dao
interface StudentDao {

    @Query("SELECT * FROM student_table")
    fun getAllStudents(): DataSource.Factory<Int, Student>

    @Query("SELECT * FROM student_table where id =:id")
    fun getStudentById(id: Long): LiveData<Student>


    @Insert
    suspend fun inserAll(vararg student: Student)

    @Delete
    suspend fun delete(student: Student)

    @Update
    suspend fun update(student: Student)
}