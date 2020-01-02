package com.example.library.student_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.library.data.StudentRepository

class StudentListViewModelFactory(val studentRepository: StudentRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StudentListViewModel(studentRepository) as T
    }
}