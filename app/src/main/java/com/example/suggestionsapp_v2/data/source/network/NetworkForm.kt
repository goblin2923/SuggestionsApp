package com.example.suggestionsapp_v2.data.source.network

data class NetworkForm(
    val fId: String,
    val optionName: String,
    val shortDescription: String? = null,
    val color: Int,
    val priority: Int? = null,
    val votes: Int = 0,
)
