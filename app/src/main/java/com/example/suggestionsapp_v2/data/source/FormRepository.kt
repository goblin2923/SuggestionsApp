package com.example.suggestionsapp_v2.data.source

import androidx.compose.ui.graphics.Color
import javax.inject.Inject
import com.example.suggestionsapp_v2.data.source.local.FormDao
import com.example.suggestionsapp_v2.data.source.local.toExternal
import com.example.suggestionsapp_v2.data.source.local.toLocal
import com.example.suggestionsapp_v2.data.source.local.toNetwork
import com.example.suggestionsapp_v2.data.source.network.FormNetworkDataSource
import com.example.suggestionsapp_v2.di.DispatcherProvider
import com.example.suggestionsapp_v2.di.ScopeProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class DefaultFormRepository @Inject constructor(
    private val localDataSource: FormDao,
    private val networkDataSource: FormNetworkDataSource,

    private val dispatchProvider: DispatcherProvider,
    private val scopeProvider: ScopeProvider
) {
//    remove hardcode later!!!!!!!
    private val dispatcher = dispatchProvider.default
    private val scope = scopeProvider.getApplicationScope()

    private val availableColors = mutableListOf(Color.Cyan, Color.Blue, Color.Yellow, Color.Magenta, Color.Green, Color.White, )

    private fun assignColor(form: Form): Color {
        // Ensure there are available colors
        if (availableColors.isEmpty()) {
            throw IllegalStateException("No colors available to assign")
        }
        // Assign a color from the available list and remove it from the list
        val assignedColor = availableColors.removeAt(0)
        form.color = assignedColor

        return assignedColor
    }

    // This method might be computationally expensive
    private fun createFormId(): String {
        return UUID.randomUUID().toString()
    }

    suspend fun create(fID: String, option: String, votes: Int): String {
        val formId = withContext(dispatcher) {
            createFormId()
        }
        val form = Form(
            fId = formId,
            optionName = option,
            votes = votes,
        ).apply {
            color = assignColor(this)
        }
        localDataSource.upsert(form.toLocal())
//        saveTasksToNetwork()
        return formId
    }


    fun getAll(): List<Form> {
        return localDataSource.observeAll().map { forms ->
            forms.toExternal()
        }
    }


    suspend fun refresh() {
        val networkForm = networkDataSource.loadTasks()
        localDataSource.deleteAll()
        val localTasks = withContext(dispatcher) {
            networkForm.toLocal()
        }
        localDataSource.upsertAll(networkForm.toLocal())
    }

//    if checkbox functionality needed later on
//    suspend fun complete(taskId: String) {
//        localDataSource.updateCompleted(taskId, true)
//        saveTasksToNetwork()
//    }

//    private suspend fun saveTasksToNetwork() {
//        scope.launch {
//            val localTasks = localDataSource.observeAll().first()
//            val networkTasks = withContext(dispatcher) {
//                localTasks.toNetwork()
//            }
//            networkDataSource.saveTasks(networkTasks)
//        }
//    }
}