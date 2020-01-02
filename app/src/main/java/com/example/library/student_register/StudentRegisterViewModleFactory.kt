package com.example.library.student_register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.library.data.StudentRepository

class StudentRegisterViewModleFactory(private val studentRepository: StudentRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StudentRegisterViewModle(studentRepository) as T
    }
}