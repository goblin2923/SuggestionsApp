package com.example.suggestionsapp_v2

import android.app.Application
import com.example.suggestionsapp_v2.data.source.local.SuggestionsDatabase

class SuggestionsApp : Application() {
    companion object{
        lateinit var suggestionsDatabase:SuggestionsDatabase
    }

    override fun onCreate() {
        super.onCreate()
        suggestionsDatabase = SuggestionsDatabase.getDatabase(applicationContext)
    }
}