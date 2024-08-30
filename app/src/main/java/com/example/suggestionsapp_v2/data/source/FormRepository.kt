package com.example.suggestionsapp_v2.data.source

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.suggestionsapp_v2.SuggestionsApp
import com.example.suggestionsapp_v2.data.source.local.FormDao
import com.example.suggestionsapp_v2.data.source.network.FormApiService
import com.example.suggestionsapp_v2.data.source.network.NetworkDataclass
import com.example.suggestionsapp_v2.data.source.network.SuggestionsApiService
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
                    handleFormData(formData, colorList)
                }

                val allFormsWithUsers = localDataSource.getAllFormsWithUsers()
                totalVotes = calcTotalVotes(allFormsWithUsers)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun handleFormData(formData: NetworkDataclass, colorList: MutableList<Color>) {
        val options = formData.selectedOptions.split(',').map { it.trim() }
        val userName = formData.name
        val userSuggestion =
            if (formData.suggestions.isNullOrEmpty()) "No suggestions" else formData.suggestions
        val time = formatTime(formData.timeStamp)

        options.forEach { optionStr ->
            val option = mapOption(optionStr)
            if (option != FormData.Options.NULL) {
                processFormOption(option, userName, userSuggestion, time, colorList)
            }
        }
    }


    private fun mapOption(optionStr: String): FormData.Options {
        return when (optionStr) {
            "Painting" -> FormData.Options.PAINTING
            "Sketching" -> FormData.Options.SKETCHING
            "Mime" -> FormData.Options.MIME
            "Qawali night" -> FormData.Options.QAWALI
            "Fashion show" -> FormData.Options.FASHION
            else -> FormData.Options.NULL
        }
    }


    private suspend fun processFormOption(
        option: FormData.Options,
        userName: String,
        userSuggestion: String,
        time: String,
        colorList: MutableList<Color>
    ) {
        val existingFormWithUsers = localDataSource.getUserFormWithUsers(option)

        if (existingFormWithUsers != null) {
            updateUserForOption(existingFormWithUsers, userName, userSuggestion, time)
        } else {
            createNewForm(option, userName, userSuggestion, time, colorList)
        }
    }

    private suspend fun updateUserForOption(
        existingFormWithUsers: FormWithUsers, userName: String, userSuggestion: String, time: String
    ) {
        val existingUser =
            localDataSource.getUserForOption(userName, existingFormWithUsers.formData.optionName)
        if (existingUser != null) {
            val updatedUser = existingUser.copy(suggestion = userSuggestion)
            localDataSource.updateUser(updatedUser)
        } else {
            insertNewUser(existingFormWithUsers.formData.optionName, userName, userSuggestion, time)
            val updatedForm =
                existingFormWithUsers.formData.copy(votes = existingFormWithUsers.formData.votes + 1)
            localDataSource.updateForm(updatedForm)
        }
    }

    private suspend fun createNewForm(
        option: FormData.Options,
        userName: String,
        userSuggestion: String,
        time: String,
        colorList: MutableList<Color>
    ) {
        val color = assignColor(colorList)
        val newForm = FormData(optionName = option, votes = 1, color = color)
        localDataSource.insertForm(newForm)
        insertNewUser(option, userName, userSuggestion, time)
    }

    private fun assignColor(colorList: MutableList<Color>): Int {
        return if (colorList.isNotEmpty()) {
            val color = colorList.first().toArgb()
            colorList.removeAt(0)
            color
        } else {
            getRandomColor().toArgb()
        }
    }

    private suspend fun insertNewUser(
        option: FormData.Options, userName: String, userSuggestion: String, time: String
    ) {
        val newUser = UserData(
            formOptionName = option, name = userName, suggestion = userSuggestion, time = time
        )
        localDataSource.insertUser(newUser)
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


val ColorSet: Set<Color> = setOf(
    Color(0xFF489e9d),
    Color(0xFF690105),
    Color(0xFF4a2062),
    Color(0xFF4b607c),
    Color(0xFF749e48),
    Color(0xFF72489e),
    Color(0xFF003736),
    Color(0xFF9e4849),
    Color(0xFF7c674b),
)