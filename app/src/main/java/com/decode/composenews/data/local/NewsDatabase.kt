package com.decode.composenews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.decode.composenews.data.local.entity.NewsEntity
import com.decode.composenews.data.local.entity.RemoteKeyEntity
import com.decode.composenews.data.local.entity.SavedNewsEntity

@Database(entities = [NewsEntity::class,RemoteKeyEntity::class,SavedNewsEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun savedNewsDao(): SavedNewsDao
}