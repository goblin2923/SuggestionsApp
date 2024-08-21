package com.example.suggestionsapp_v2.data.source

import javax.inject.Inject
import com.example.suggestionsapp_v2.data.source.local.FormDao
import com.example.suggestionsapp_v2.data.source.local.toExternal
import com.example.suggestionsapp_v2.data.source.local.toLocal
import com.example.suggestionsapp_v2.data.source.local.toNetwork
import com.example.suggestionsapp_v2.data.source.network.FormNetworkDataSource
import com.example.suggestionsapp_v2.di.ScopeProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class DefaultFormRepository @Inject constructor(
    private val localDataSource: FormDao,
    private val networkDataSource: FormNetworkDataSource,
//    remove hardcode later!!!!!!!
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val scopeProvider: ScopeProvider
) {
    private val scope = scopeProvider.getApplicationScope()

    fun obeserveAll(): Flow<List<Form>> {
        return localDataSource.observeAll().map { forms ->
            forms.toExternal()
        }
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
            votes = votes
        )
        localDataSource.upsert(form.toLocal())
        saveTasksToNetwork()
        return formId
    }

    suspend fun refresh() {
        val networkForm = networkDataSource.loadTasks()
        localDataSource.deleteAll()
        val localTasks = withContext(dispatcher) {
            networkForm.toLocal()
        }
        localDataSource.upsertAll(networkForm.toLocal())
    }

    private suspend fun saveTasksToNetwork() {
        scope.launch {
            val localTasks = localDataSource.observeAll().first()
            val networkTasks = withContext(dispatcher) {
                localTasks.toNetwork()
            }
            networkDataSource.saveTasks(networkTasks)
        }
    }

//    if checkbox funtionality needed later on
//    suspend fun complete(taskId: String) {
//        localDataSource.updateCompleted(taskId, true)
//        saveTasksToNetwork()
//    }
}