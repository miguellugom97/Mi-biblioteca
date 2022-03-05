package com.miguellugo.mibiblioteca.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.miguellugo.mibiblioteca.data.database.entities.Book

@Dao
interface BookDao {
    @Query("SELECT * FROM table_book ORDER BY id ASC")
    suspend fun getAllBooks(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("SELECT * FROM table_book WHERE title LIKE :searchQuery OR author LIKE :searchQuery OR editorial LIKE :searchQuery OR year LIKE :searchQuery")
    suspend fun searchBook(searchQuery: String): List<Book>
}