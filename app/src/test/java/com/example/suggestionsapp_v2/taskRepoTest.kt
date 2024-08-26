package com.example.suggestionsapp_v2

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.suggestionsapp_v2.data.source.DefaultFormRepository
import com.example.suggestionsapp_v2.data.source.FormData
import com.example.suggestionsapp_v2.data.source.local.toLocal
import com.example.suggestionsapp_v2.data.source.network.NetworkDataclass
import com.example.suggestionsapp_v2.data.source.network.SuggestionsAPI
import com.example.suggestionsapp_v2.data.source.network.SuggestionsApiService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TaskRepoTest {

    private lateinit var testDispatcher: TestDispatcher
    private lateinit var testScope: TestScope

    private lateinit var localForm: List<FormData>
    private lateinit var localDataSource: fakeFormDaoTest
    private lateinit var networkDataSource: SuggestionsApiService
    private lateinit var taskRepository: DefaultFormRepository

    @Before
    fun setup() {
        testDispatcher = UnconfinedTestDispatcher()
        testScope = TestScope(testDispatcher)

        // Set up local data
        localForm = listOf(
            FormData(1, "Help", 5, Color.Red.toArgb()),
            FormData(2, "me", 0, Color.Blue.toArgb()),
            FormData(3, "die", 15, Color.Unspecified.toArgb()),
        )

        localDataSource = fakeFormDaoTest(localForm)
        networkDataSource = SuggestionsAPI.retrofitService

        taskRepository = DefaultFormRepository(
            localDataSource = localDataSource,
            networkDataSource = networkDataSource
        )
    }

    @Test
    fun observeAll_exposesLocalData() = runTest {
        val tasks = taskRepository.getAllForms()

        assertEquals(localForm, tasks)
    }


    @Test
    fun onRefreshLocalEqualNetwork() = runTest {
        val networkTasks = listOf(
            NetworkDataclass(selectedOptions = "Color", timeStamp = currentTime.toString()),
            NetworkDataclass(selectedOptions = "Sketch"),

        )
//        taskRepository.refreshForms()

//        assertEquals(networkTasks.toLocal(), localDataSource.observeAll().first())
        assertEquals(networkTasks.toLocal(), networkDataSource.getResponses().first())
    }

}



