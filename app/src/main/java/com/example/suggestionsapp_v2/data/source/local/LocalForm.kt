package com.example.suggestionsapp_v2.data.source.local

import com.example.suggestionsapp_v2.data.source.FormData
import com.example.suggestionsapp_v2.data.source.network.NetworkDataclass

//@Entity(tableName = "FormData")
//data class LocalFormData (
//    @PrimaryKey val id: String,
//    val optionName: String = "",
//    val votes: Int,
//    val color: Int
//)

//fun FormData.toNetwork() = NetworkDataclass(
//    fId = fId,
//    selectedOptions = optionName,
//    votes = votes,
//    color = color
//)
//fun List<FormData>.toNetwork() = map(FormData::toNetwork) // Equivalent to map { it.toExternal() }

//fun NetworkDataclass.toLocal() = FormData(
////    fId =  fId,
//    optionName = selectedOptions,
////    votes = votes,
////    color = color
//)


//fun List<NetworkDataclass>.toLocal() = map(NetworkDataclass::toLocal)


//fun FormData.toNetwork() = NetworkDataclass(
//    fId = fId,
//    optionName = optionName,
//    votes = votes,
//    color = color
//)
//fun List<FormData>.toNetwork() = map(FormData::toNetwork)