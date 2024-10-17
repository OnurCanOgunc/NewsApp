package com.decode.composenews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decode.composenews.data.local.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}