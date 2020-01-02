package com.example.library.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.library.R
import com.example.library.data.StudentAndAllBooks
import kotlinx.android.synthetic.main.student_list_item.view.*

class StudentsListAdapter(
    private val onStudentItemClickListener: OnStudentItemClickListener
) : ListAdapter<StudentAndAllBooks, StudentViewHolder>(Callback()) {


    companion object {
        class Callback : DiffUtil.ItemCallback<StudentAndAllBooks>() {
            override fun areItemsTheSame(
                oldItem: StudentAndAllBooks,
                newItem: StudentAndAllBooks
            ): Boolean {
                return oldItem.stBkId == newItem.stBkId
            }

            override fun areContentsTheSame(
                oldItem: StudentAndAllBooks,
                newItem: StudentAndAllBooks
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        return StudentViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(getItem(position), onStudentItemClickListener)
    }
}


class StudentViewHolder private constructor(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(
        student: StudentAndAllBooks,
        onStudentItemClickListener: OnStudentItemClickListener
    ) {
        view.setOnClickListener {
            onStudentItemClickListener.onClick(student.student.studentId)
        }
        view.textViewName.text = student.student.name
        view.textViewClass.text = student.student.className
        view.tv_brrowed_book_count.text = student.borrowedBooks.size.toString()
    }

    companion object {
        fun from(parent: ViewGroup): StudentViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return StudentViewHolder(inflater.inflate(R.layout.student_list_item, parent, false))
        }
    }
}

class OnStudentItemClickListener(private val clickListener: (id: Long) -> Unit) {
    fun onClick(id: Long) = clickListener(id)
}