package com.example.suggestionsapp_v2

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.suggestionsapp_v2.data.source.DefaultFormRepository
import com.example.suggestionsapp_v2.data.source.local.SuggestionsDatabase
import com.example.suggestionsapp_v2.ui.screens.SuggestionsViewModel

class SuggestionsApp : Application() {
    companion object{
        lateinit var suggestionsDatabase:SuggestionsDatabase
    }
    lateinit var defaultRepo:DefaultFormRepository
    lateinit var mainViewModel: SuggestionsViewModel


    override fun onCreate() {
        super.onCreate()
        suggestionsDatabase = SuggestionsDatabase.getDatabase(applicationContext)
        defaultRepo = DefaultFormRepository()
    }
}
@Composable
fun GetMainScreenViewModel(): SuggestionsViewModel = viewModel(factory = SuggestionsViewModel.Factory)