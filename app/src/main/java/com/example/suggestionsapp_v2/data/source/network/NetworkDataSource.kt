package com.example.suggestionsapp_v2.data.source.network

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class FormNetworkDataSource @Inject constructor() {

    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()
    private var forms = listOf(
        NetworkForm(
            fId = "0",
            optionName = "Painting",
            shortDescription = "Paint please",
            color = Color.Blue.toArgb()
        ),
        NetworkForm(
            fId = "1",
            optionName = "Mime",
            shortDescription = "stay quiet",
            color = Color.Blue.toArgb()

        )
    )

    suspend fun loadTasks(): List<NetworkForm> = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        return forms
    }

//    suspend fun saveTasks(newTasks: NetworkForm) = accessMutex.withLock {
//        delay(SERVICE_LATENCY_IN_MILLIS)
//        forms = newTasks
//    }

}

private const val SERVICE_LATENCY_IN_MILLIS = 2000L
