package com.example.suggestionsapp_v2.data.source.network

import kotlinx.serialization.Serializable

@Serializable
data class NetworkDataclass(
    val selectedOptions: String,
    val suggestions: String? = null,
    val timeStamp: String? = null,
)


@Serializable
data class NetworkResponseForms(
    val data: List<NetworkDataclass>
)


