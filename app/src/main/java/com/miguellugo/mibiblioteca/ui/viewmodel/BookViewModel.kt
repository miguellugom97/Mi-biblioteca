package com.miguellugo.mibiblioteca.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguellugo.mibiblioteca.data.database.entities.Book
import com.miguellugo.mibiblioteca.domain.usecases.DeleteBook
import com.miguellugo.mibiblioteca.domain.usecases.GetAllBooks
import com.miguellugo.mibiblioteca.domain.usecases.InsertBook
import com.miguellugo.mibiblioteca.domain.usecases.SearchBooks
import com.miguellugo.mibiblioteca.domain.usecases.UpdateBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getAllBooks: GetAllBooks,
    private val insertBook: InsertBook,
    private val updateBook: UpdateBook,
    private val deleteBook: DeleteBook,
    private val searchBooks: SearchBooks
) : ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val books = MutableLiveData<List<Book>>()

    fun getListBooks() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = getAllBooks()
            if (!result.isNullOrEmpty())
                books.postValue(result)
            isLoading.postValue(false)
        }
    }

    fun searchBook(searchQuery: String) {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = searchBooks(searchQuery)
            if (!result.isNullOrEmpty())
                books.postValue(result)
            isLoading.postValue(false)
        }
    }

    fun addBook(book: Book) {
        viewModelScope.launch {
            isLoading.postValue(true)
            insertBook(book)
            isLoading.postValue(false)
        }
    }

    fun upBook(book: Book) {
        viewModelScope.launch {
            isLoading.postValue(true)
            updateBook(book)
            isLoading.postValue(false)
        }
    }

    fun delBook(book: Book) {
        viewModelScope.launch {
            isLoading.postValue(true)
            deleteBook(book)
            isLoading.postValue(false)
        }
    }
}