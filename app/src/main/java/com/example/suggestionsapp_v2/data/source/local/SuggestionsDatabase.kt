package com.example.suggestionsapp_v2.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [LocalFormData::class], version = 1, exportSchema = false)
abstract class SuggestionsDatabase : RoomDatabase() {

    abstract fun formDao(): FormDao
}
