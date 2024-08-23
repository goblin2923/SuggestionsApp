package com.example.suggestionsapp_v2.data.source

import com.example.suggestionsapp_v2.data.source.local.FormDao
import com.example.suggestionsapp_v2.data.source.local.SuggestionsDatabase
import com.example.suggestionsapp_v2.data.source.local.toExternal
import com.example.suggestionsapp_v2.data.source.local.toLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

class DefaultFormRepository(var localDataSource: FormDao? = SuggestionsDatabase.INSTANCE?.formDao) {


    // This method might be computationally expensive
    private fun createFormId(): String {
        return UUID.randomUUID().toString()
    }

    suspend fun create(fID: String, option: String, votes: Int, color: Int): String {
        val formId = withContext(Dispatchers.Default) {
            createFormId()
        }
        val formData = FormData(
            fId = formId,
            optionName = option,
            votes = votes,
            color = color
        )
        localDataSource?.upsert(formData.toLocal())
//        saveTasksToNetwork()
        return formId
    }


    suspend fun getAll(): List<FormData>? {
        return localDataSource?.observeAll()?.map { forms ->
            forms.toExternal()
        }
    }

    //    fun assignColor(form: Form): String {
//        // Ensure there are available colors
//        if (availableColors.isEmpty()) {
//            throw IllegalStateException("No colors available to assign")
//        }
//        // Assign a color from the available list and remove it from the list
//        val assignedColor = availableColors.removeAt(0)
//        form.color = assignedColor
//
//        return assignedColor
//    }

//    suspend fun refresh() {
//        val networkForm = networkDataSource.loadTasks()
//        localDataSource.deleteAll()
//        val localTasks = withContext(dispatcher) {
//            networkForm.toLocal()
//        }
//        localDataSource.upsertAll(networkForm.toLocal())
//    }

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