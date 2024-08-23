package com.example.suggestionsapp_v2

import com.example.suggestionsapp_v2.data.source.FormData
import com.example.suggestionsapp_v2.data.source.local.FormDao

class fakeFormDaoTest (initialForms: List<FormData>) : FormDao{
    private val _forms = initialForms.toMutableList()
    private val formStream = _forms.toList()

    override fun observeAll(): List<FormData> = formStream

    override suspend fun upsert(task: FormData) {
        TODO("Not yet implemented")
    }

    override suspend fun upsertAll(tasks: List<FormData>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

}