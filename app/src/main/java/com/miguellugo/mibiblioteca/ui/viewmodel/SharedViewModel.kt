package com.miguellugo.mibiblioteca.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miguellugo.mibiblioteca.data.database.entities.Book

class SharedViewModel: ViewModel() {
    private var _isNew = MutableLiveData<Boolean>()
    val isNew: LiveData<Boolean> = _isNew
    private var _book = MutableLiveData<Book>()
    val book: LiveData<Book> = _book

    fun saveBook(newBook: Book) {
        _book.value = newBook
    }

    fun saveBoolean(bool: Boolean) {
        _isNew.value = bool
    }
}