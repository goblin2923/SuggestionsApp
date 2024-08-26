package com.example.suggestionsapp_v2

import android.app.Application
import com.example.suggestionsapp_v2.data.source.DefaultFormRepository
import com.example.suggestionsapp_v2.data.source.local.SuggestionsDatabase
import com.example.suggestionsapp_v2.ui.MainScreenViewModel

class SuggestionsApp : Application() {
    companion object{
        lateinit var suggestionsDatabase:SuggestionsDatabase
    }
    lateinit var defaultRepo:DefaultFormRepository
    lateinit var mainViewModel: MainScreenViewModel


    override fun onCreate() {
        super.onCreate()
        suggestionsDatabase = SuggestionsDatabase.getDatabase(applicationContext)
        defaultRepo = DefaultFormRepository()
    }
}