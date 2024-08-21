package com.example.suggestionsapp_v2.data.source.network

import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class FormNetworkDataSource @Inject constructor() {

    // A mutex is used to ensure that reads and writes are thread-safe.
    private val accessMutex = Mutex()
    private var tasks = listOf(
        NetworkForm(
            id = "0",
            optionName = "Painting",
            shortDescription = "Paint please"
        ),
        NetworkForm(
            id = "1",
            optionName = "Mime",
            shortDescription = "stay quiet"
        )
    )

    suspend fun loadTasks(): List<NetworkForm> = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        return tasks
    }

    suspend fun saveTasks(newTasks: List<NetworkForm>) = accessMutex.withLock {
        delay(SERVICE_LATENCY_IN_MILLIS)
        tasks = newTasks
    }

}

private const val SERVICE_LATENCY_IN_MILLIS = 2000L
