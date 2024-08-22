package com.example.suggestionsapp_v2.data.source.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface FormDao {
    @Query("SELECT * FROM FormData")
    fun observeAll(): List<LocalFormData>

    @Query("SELECT * FROM FormData WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<LocalFormData>

    @Upsert
    suspend fun upsert(task: LocalFormData)

    @Upsert
    suspend fun upsertAll(tasks: List<LocalFormData>)

    @Query("SELECT * FROM FormData WHERE optionName LIKE :option")
    fun findByName(option: String): LocalFormData

    @Query("DELETE FROM FormData")
    suspend fun deleteAll()

//    add later, insert suggestions in db. maybe need new db

//    @Insert
//    fun insertAll(vararg options: localFormData)


//    @Query("UPDATE task SET isCompleted = :completed WHERE id = :taskId")
//    suspend fun updateCompleted(taskId: String, completed: Boolean)
}