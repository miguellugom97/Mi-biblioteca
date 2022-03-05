package com.miguellugo.mibiblioteca.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.miguellugo.mibiblioteca.data.database.dao.BookDao
import com.miguellugo.mibiblioteca.data.database.entities.Book
import com.miguellugo.mibiblioteca.data.model.Converters

@Database(entities = [Book::class], version = 1)
@TypeConverters(Converters::class)
abstract class BookDatabase: RoomDatabase() {
    abstract fun getBookDao(): BookDao
}