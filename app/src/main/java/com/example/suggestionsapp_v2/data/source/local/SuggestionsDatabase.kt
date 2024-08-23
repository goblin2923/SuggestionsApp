package com.example.suggestionsapp_v2.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.suggestionsapp_v2.data.source.FormData


@Database(entities = [FormData::class], version = 2, exportSchema = false)
abstract class SuggestionsDatabase : RoomDatabase() {

    abstract val formDao: FormDao

    companion object {

        @Volatile
        var INSTANCE: SuggestionsDatabase? = null

        fun getDatabase(context: Context): SuggestionsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, SuggestionsDatabase::class.java, "app_database"
                )
//                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Create the new table with the modified schema
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS `FormData_new` (
                `fId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `optionName` TEXT NOT NULL,
                `votes` INTEGER NOT NULL,
                `color` INTEGER NOT NULL
            )
        """)

        // Copy the data from the old table to the new table, converting fId to an integer if necessary
        db.execSQL("""
            INSERT INTO `FormData_new` (optionName, votes, color)
            SELECT optionName, votes, color
            FROM FormData
        """)

        // Drop the old table
        db.execSQL("DROP TABLE FormData")

        // Rename the new table to the old table's name
        db.execSQL("ALTER TABLE FormData_new RENAME TO FormData")
    }
}

