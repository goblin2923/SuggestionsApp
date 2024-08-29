package com.example.suggestionsapp_v2.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.suggestionsapp_v2.data.source.FormData
import com.example.suggestionsapp_v2.data.source.UserData


@Database(entities = [FormData::class, UserData::class], version = 2, exportSchema = false)
abstract class SuggestionsDatabase : RoomDatabase() {

    abstract val formDao: FormDao
//    abstract val userDao: UserDao

    companion object {
        @Volatile
        var INSTANCE: SuggestionsDatabase? = null

        fun getDatabase(context: Context): SuggestionsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context, SuggestionsDatabase::class.java, "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `FormData_new` (
                `fId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `optionName` TEXT NOT NULL,
                `votes` INTEGER NOT NULL,
                `color` INTEGER NOT NULL
            )
        """
        )

        db.execSQL(
            """
            INSERT INTO `FormData_new` (optionName, votes, color)
            SELECT optionName, votes, color
            FROM FormData
        """
        )

        db.execSQL("DROP TABLE FormData")

        db.execSQL("ALTER TABLE FormData_new RENAME TO FormData")
    }
}

