package com.example.library.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.library.R
import com.example.library.data.Book
import kotlinx.android.synthetic.main.book_list_item.view.*

class BookListAdapter constructor(private val onBookItemClickListener: OnBookItemClickListener) :
    ListAdapter<Book, BookViewHolder>(Callback()) {
    companion object {
        class Callback : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.bookId == newItem.bookId
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(getItem(position), onBookItemClickListener)
    }
}

class BookViewHolder private constructor(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        book: Book,
        onBookItemClickListener: OnBookItemClickListener
    ) {
        with(view) {
            textViewBookName.text = book.bookName
            textViewBookCount.text = "5"
            textViewAuthorName.text = book.author
            setOnClickListener {
                onBookItemClickListener.onClick(bookId = book.bookId)
            }
        }
    }

    companion object {
        fun from(parent: ViewGroup): BookViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return BookViewHolder(inflater.inflate(R.layout.book_list_item, parent, false))
        }
    }
}

class OnBookItemClickListener(private val clickLis: (bookId: Long) -> Unit) {
    fun onClick(bookId: Long) = clickLis(bookId)
}