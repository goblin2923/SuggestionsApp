package com.example.suggestionsapp_v2.data.source

import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.suggestionsapp_v2.SuggestionsApp
import com.example.suggestionsapp_v2.data.source.local.ColorSet
import com.example.suggestionsapp_v2.data.source.local.FormDao
import com.example.suggestionsapp_v2.data.source.network.FormApiService
//import com.example.suggestionsapp_v2.data.source.network.SuggestionsAPI
import com.example.suggestionsapp_v2.data.source.network.SuggestionsApiService
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DefaultFormRepository(
    private val localDataSource: FormDao = SuggestionsApp.suggestionsDatabase.formDao,
    private val networkDataSource: SuggestionsApiService = FormApiService()
) {
    companion object {
        private var totalVotes = 0
    }

    suspend fun refreshForms() {
        try {
            val networkForms = networkDataSource.getResponses()

            if (networkForms.data.isNotEmpty()) {
                var colorList = ColorSet.toMutableList()

                networkForms.data.forEach { formData ->
                    val options = formData.selectedOptions.split(',').map { it.trim() }
                    val userName = formData.name
                    val userSuggestion =
                        if (formData.suggestions.isNullOrEmpty()) "No suggestions" else formData.suggestions
                    val timeStamp = formData.timeStamp
                    val time = formatTime(timeStamp)

                    options.forEach { optionStr ->
                        val option = when (optionStr) {
                            "Painting" -> FormData.Options.PAINTING
                            "Sketching" -> FormData.Options.SKETCHING
                            "Mime" -> FormData.Options.MIME
                            "Qawali night" -> FormData.Options.QAWALI
                            "Fashion show" -> FormData.Options.FASHION
                            else -> FormData.Options.NULL
                        }

                        if (option != FormData.Options.NULL) {
                            val existingFormWithUsers = localDataSource.getUserFormWithUsers(option)

                            if (existingFormWithUsers != null) {
                                val existingUser = localDataSource.getUserForOption(userName, option)
                                if (existingUser != null) {
                                    val updatedUser = existingUser.copy(suggestion = userSuggestion)
                                    localDataSource.updateUser(updatedUser)
                                } else {
                                    val newUser = UserData(
                                        formOptionName = option,
                                        name = userName,
                                        suggestion = userSuggestion,
                                        time = time
                                    )
                                    localDataSource.insertUser(newUser)

                                    val updatedForm = existingFormWithUsers.formData.copy(
                                        votes = existingFormWithUsers.formData.votes + 1
                                    )
                                    localDataSource.updateForm(updatedForm)
                                }
                            } else {
                                // Assign color
                                var color: Int = if (colorList.isNotEmpty()) {
                                    colorList.first().toArgb()
                                } else {
                                    getRandomColor().toArgb()
                                }
                                colorList = colorList.drop(1).toMutableList()

                                // Create a new form and insert
                                val newForm = FormData(
                                    optionName = option,
                                    votes = 1,
                                    color = color,
                                )
                                localDataSource.insertForm(newForm)

                                // Insert the first user for this form option
                                val newUser = UserData(
                                    formOptionName = option,
                                    name = userName,
                                    suggestion = userSuggestion,
                                    time = time
                                )
                                localDataSource.insertUser(newUser)
                            }
                        }
                    }
                }
                // Retrieve all forms with users
                val allFormsWithUsers = localDataSource.getAllFormsWithUsers()

                // Remove duplicates based on user name and option
                val unduplicatedFormsWithUsers = allFormsWithUsers.map { formWithUsers ->
                    val uniqueUsers = formWithUsers.users.distinctBy { it.name to it.formOptionName }
                    formWithUsers.copy(users = uniqueUsers)
                }
                // Retrieve all forms with users to calculate total votes
                totalVotes = calcTotalVotes(unduplicatedFormsWithUsers)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    fun getVotes() = totalVotes

    fun getAllForms() = localDataSource.observeAll()

    suspend fun getUsersForForm(optionName: FormData.Options): FormWithUsers? {
        val formWithUsers = localDataSource.getUserFormWithUsers(optionName)
        return formWithUsers
    }

    private fun getRandomColor(): Color {
        return ColorSet.random()
    }

    private fun calcTotalVotes(data: List<FormWithUsers>): Int {
        return data.sumOf { it.formData.votes }
    }

    private fun formatTime(inputTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
        val date: Date = inputFormat.parse(inputTime) ?: return ""
        return outputFormat.format(date)
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


