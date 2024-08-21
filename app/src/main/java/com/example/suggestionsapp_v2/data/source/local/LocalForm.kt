package com.example.suggestionsapp_v2.data.source.local

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.suggestionsapp_v2.data.source.Form
import com.example.suggestionsapp_v2.data.source.network.NetworkForm

@Entity(
    tableName = "FormData"
)
data class LocalFormData (
    @PrimaryKey val id: String,
    val optionName: String = "",
//    val color: Color? = Color.Red,
    var votes: Int
){

}

fun LocalFormData.toExternal() = Form(
    fId = id,
    optionName = optionName,
    votes = votes
)
fun List<LocalFormData>.toExternal() = map(LocalFormData::toExternal) // Equivalent to map { it.toExternal() }

fun Form.toLocal() = LocalFormData(
    id = fId,
    optionName = optionName,
    votes = votes
)
fun List<Form>.toLocal() = map(Form::toLocal)


fun NetworkForm.toLocal() = LocalFormData(
    id = id,
    optionName = optionName,
    votes = votes
)
fun List<NetworkForm>.toLocal() = map(NetworkForm::toLocal)


fun LocalFormData.toNetwork() = NetworkForm(
    id = id,
    optionName = optionName,
    votes = votes
)
fun List<LocalFormData>.toNetwork() = map(LocalFormData::toNetwork)