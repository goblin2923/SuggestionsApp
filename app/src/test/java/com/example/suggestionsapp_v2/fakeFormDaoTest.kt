package com.example.suggestionsapp_v2

import com.example.suggestionsapp_v2.data.source.local.FormDao
import com.example.suggestionsapp_v2.data.source.local.LocalFormData

class fakeFormDaoTest (initialForms: List<LocalFormData>) : FormDao{
    private val _forms = initialForms.toMutableList()
    private val formStream = _forms.toList()

    override fun observeAll(): List<LocalFormData> = formStream

    override suspend fun upsert(task: LocalFormData) {
        TODO("Not yet implemented")
    }

    override suspend fun upsertAll(tasks: List<LocalFormData>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

}