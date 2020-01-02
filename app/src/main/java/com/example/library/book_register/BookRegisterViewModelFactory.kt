package com.example.library.book_register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.library.data.BookRepository

class BookRegisterViewModelFactory(private val bookRepository: BookRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookRegisterViewModel(bookRepository) as T
    }
}