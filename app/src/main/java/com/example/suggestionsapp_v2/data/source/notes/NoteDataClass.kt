package com.example.suggestionsapp_v2.data.source.notes

data class Note(val id: String = "",
                val title: String = "",
                val content: String = "",
                val timestamp: Long = System.currentTimeMillis()
)