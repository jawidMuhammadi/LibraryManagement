package com.example.library.book_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.library.data.Book
import com.example.library.data.BookRepository
import com.example.library.utils.Event

class BookListViewModel(private val bookRepository: BookRepository) : ViewModel() {
    val bookList: LiveData<PagedList<Book>> = bookRepository.getAllBooks()

    private var _onListItemClick = MutableLiveData<Event<Long>>()
    val onListItemClick: LiveData<Event<Long>>
        get() = _onListItemClick

    fun onListItemClick(bookId: Long) {
        _onListItemClick.value = Event(bookId)
    }
}