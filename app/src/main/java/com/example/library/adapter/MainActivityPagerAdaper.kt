package com.example.library.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.library.book_list.BookListFragment
import com.example.library.student_list.StudentListFragment

const val STUDENT_FRAGMENT = 0
const val BOOKS_FRAGMENT = 1

class MainActivityPagerAdaper(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragmentCreator: Map<Int, () -> Fragment> = mapOf(
        STUDENT_FRAGMENT to { StudentListFragment() },
        BOOKS_FRAGMENT to { BookListFragment() }
    )

    override fun getItemCount(): Int {
        return fragmentCreator.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentCreator[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

}