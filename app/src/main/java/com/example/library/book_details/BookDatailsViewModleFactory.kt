package com.example.library.book_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.library.data.BookRepository

class BookDatailsViewModleFactory(
    private val bookRepository: BookRepository,
    private val bookId: Long
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return BookDetailsViewModel(bookRepository, bookId) as T
    }
}