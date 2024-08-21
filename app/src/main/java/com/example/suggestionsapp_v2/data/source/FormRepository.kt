package com.example.suggestionsapp_v2.data.source

import javax.inject.Inject
import com.example.suggestionsapp_v2.data.source.local.FormDao
import com.example.suggestionsapp_v2.data.source.local.toExternal
import com.example.suggestionsapp_v2.data.source.local.toLocal
import com.example.suggestionsapp_v2.data.source.network.FormNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID

class DefaultFormRepository @Inject constructor(
    private val localDataSource: FormDao,
    private val networkDataSource: FormNetworkDataSource,
//    remove hardcode later!!!!!!!
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    fun obeserveAll(): Flow<List<Form>> {
        return localDataSource.observeAll().map { forms->
            forms.toExternal()
        }
    }
    // This method might be computationally expensive
    private fun createFormId() : String {
        return UUID.randomUUID().toString()
    }
    suspend fun create(fID: String, option: String, votes: Int):String{
        val formId = withContext(dispatcher){
            createFormId()
        }
        val form = Form(
            fId = formId,
            optionName = option,
            votes = votes)
        localDataSource.upsert(form.toLocal())

        return formId
    }
    suspend fun refresh() {
        val networkTasks = networkDataSource.loadTasks()
        localDataSource.deleteAll()
        val localTasks = withContext(dispatcher) {
            networkTasks.toLocal()
        }
        localDataSource.upsertAll(networkTasks.toLocal())
    }
}