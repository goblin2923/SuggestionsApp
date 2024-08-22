package com.example.suggestionsapp_v2.data.source.local

import com.example.suggestionsapp_v2.data.source.FormData
import com.example.suggestionsapp_v2.data.source.network.NetworkForm

//@Entity(tableName = "FormData")
//data class LocalFormData (
//    @PrimaryKey val id: String,
//    val optionName: String = "",
//    val votes: Int,
//    val color: Int
//)

fun FormData.toExternal() = FormData(
    fId = fId,
    optionName = optionName,
    votes = votes,
    color = color
)
fun List<FormData>.toExternal() = map(FormData::toExternal) // Equivalent to map { it.toExternal() }

fun FormData.toLocal() = FormData(
    fId =  fId,
    optionName = optionName,
    votes = votes,
    color = color
)
//fun List<Form>.toLocal() = map(Form::toLocal)
//

fun NetworkForm.toLocal() = FormData(
    fId =  fId,
    optionName = optionName,
    votes = votes,
    color = color
)
//fun List<NetworkForm>.toLocal() = map(NetworkForm::toLocal)


fun FormData.toNetwork() = NetworkForm(
    fId = fId,
    optionName = optionName,
    votes = votes,
    color = color
)
fun List<FormData>.toNetwork() = map(FormData::toNetwork)