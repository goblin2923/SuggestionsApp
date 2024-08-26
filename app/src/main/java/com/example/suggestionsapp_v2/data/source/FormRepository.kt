package com.example.suggestionsapp_v2.data.source

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.suggestionsapp_v2.SuggestionsApp
import com.example.suggestionsapp_v2.data.source.local.ColorSet
import com.example.suggestionsapp_v2.data.source.local.FormDao
import com.example.suggestionsapp_v2.data.source.local.SuggestionsDatabase
import com.example.suggestionsapp_v2.data.source.network.FormApiService
import com.example.suggestionsapp_v2.data.source.network.NetworkDataclass
//import com.example.suggestionsapp_v2.data.source.network.SuggestionsAPI
import com.example.suggestionsapp_v2.data.source.network.SuggestionsApiService

class DefaultFormRepository(
    private val localDataSource: FormDao = SuggestionsApp.suggestionsDatabase.formDao,
    private val networkDataSource: SuggestionsApiService = FormApiService()
) {

    suspend fun refreshForms() {
        try{
            val networkForms = networkDataSource.getResponses()

            if (networkForms.data.isNotEmpty()) {
                localDataSource?.insertAll(networkForms.data.map { formData ->
                    FormData(
                        optionName = formData.selectedOptions,
                        color = getRandomColor().toArgb(),
                        votes = calculateVotes()
                    )
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

//        println(localDataSource?.observeAll()?.first())
    }

    suspend fun getAllForms() = localDataSource?.observeAll()

    private fun calculateVotes(networkDataclass: NetworkDataclass? = null): Int{
        return 0
    }
    private fun getRandomColor(): Color {
        return ColorSet.random()
    }



//    suspend fun create(fID: Int, option: String, votes: Int, color: Int): Int {
//        val formData = FormData(
//            fId = fID,
//            optionName = option,
//            votes = votes,
//            color = color
//        )
//        localDataSource?.upsert(formData.toLocal())
//        saveTasksToNetwork()
//        return fID
//    }


//    suspend fun getAll(): List<FormData>? {
//        return localDataSource?.observeAll()?.map { forms ->
//            forms.toExternal()
//        }
//    }

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


suspend fun main(){
    val def = DefaultFormRepository()

    def.refreshForms()
}
