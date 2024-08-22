package com.example.suggestionsapp_v2.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.suggestionsapp_v2.data.source.FormData


@Database(entities = [FormData::class], version = 1, exportSchema = false)
abstract class SuggestionsDatabase : RoomDatabase() {

    abstract val formDao: FormDao

    companion object {
        @Volatile
        var INSTANCE: SuggestionsDatabase? = null

        fun getDatabase(context: Context): SuggestionsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, SuggestionsDatabase::class.java, "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
