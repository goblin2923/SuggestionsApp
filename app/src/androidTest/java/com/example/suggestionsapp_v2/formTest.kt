package com.example.suggestionsapp_v2

import androidx.compose.ui.graphics.Color
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.example.suggestionsapp_v2.data.source.local.LocalFormData
import com.example.suggestionsapp_v2.data.source.local.SuggestionsDatabase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class fromTest {

    private lateinit var database: SuggestionsDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            SuggestionsDatabase::class.java,
        ).allowMainThreadQueries().build()
    }


    @Test
    fun insertTaskAndGetTasks() = runTest {

        val task = LocalFormData(
            id = "0",
            optionName = "Get hekp",
            votes = 10
        )
        database.formDao().upsert(task)

        val tasks = database.formDao().observeAll().first()

        assertEquals(1, tasks.size)
    }
}