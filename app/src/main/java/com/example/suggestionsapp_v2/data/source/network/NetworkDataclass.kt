package com.example.suggestionsapp_v2.data.source.network

import kotlinx.serialization.Serializable

@Serializable
data class NetworkDataclass(
//    val fId: Int,
    val selectedOptions: String,
    val suggestions: String? = null,
//    val votes: Int = 0,
    val timeStamp: String? = null,
//    val color: Int? = 0
)


@Serializable
data class NetworkResponseForms(
    val data: List<NetworkDataclass>
)
