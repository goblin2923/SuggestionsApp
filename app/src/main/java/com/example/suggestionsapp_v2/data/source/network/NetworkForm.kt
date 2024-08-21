package com.example.suggestionsapp_v2.data.source.network

data class NetworkForm(
    val id: Int,
    val optionName: String,
    val shortDescription: String? = null,
    val priority: Int? = null,
    val votes: Int? = null,
)
