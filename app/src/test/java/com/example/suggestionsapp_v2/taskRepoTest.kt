package com.example.suggestionsapp_v2

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.suggestionsapp_v2.data.source.DefaultFormRepository
import com.example.suggestionsapp_v2.data.source.local.LocalFormData
import com.example.suggestionsapp_v2.data.source.local.toExternal
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class taskRepoTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    private var testDispatcher = UnconfinedTestDispatcher()
    private var testScope = TestScope(testDispatcher)

    private var localForm = listOf(
        LocalFormData("1", "Help", 5, Color.Red.toArgb()),
        LocalFormData("2", "me", 0, Color.Blue.toArgb()),
        LocalFormData("3", "die", 15, Color.Unspecified.toArgb()),
    )

    private val localDataSource = fakeFormDaoTest(localForm)
//    private val networkDataSource = TaskNetworkDataSource()
    private val taskRepository = DefaultFormRepository(
        localDataSource = localDataSource,
        dispatcher = testDispatcher,
        scope = testScope
    )

    @Test
    fun observeAll_exposesLocalData() = runTest {
        val tasks = taskRepository.getAll().first()
        assertEquals(localForm.toExternal(), tasks)
    }

}