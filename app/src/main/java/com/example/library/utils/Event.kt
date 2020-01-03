package com.example.library.utils

import androidx.lifecycle.Observer

class Event<out T> constructor(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            return content
        }
    }

}

class EventObserver<T>(private val onEventUnhandeledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(t: Event<T>?) {
        t?.getContentIfNotHandled()?.let {
            onEventUnhandeledContent(it)
        }
    }

}