package com.decode.composenews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decode.composenews.data.local.entity.NewsEntity
import com.decode.composenews.data.local.entity.RemoteKeyEntity

@Database(entities = [NewsEntity::class,RemoteKeyEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}