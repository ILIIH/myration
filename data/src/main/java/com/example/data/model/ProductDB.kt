package com.example.data.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.source.ProductDataSource

@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductDB : RoomDatabase() {
    abstract fun productDao(): ProductDataSource
}