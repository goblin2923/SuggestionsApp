package com.example.suggestionsapp_v2.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.suggestionsapp_v2.data.source.FormData
import kotlinx.coroutines.flow.Flow

@Dao
interface FormDao {

    @Query("SELECT * FROM FormData")
    fun observeAll(): Flow<List<FormData>>

    @Upsert
    suspend fun upsert(task: FormData)

    @Query("SELECT votes FROM FormData WHERE optionName = :option")
    suspend fun getVotes(option: FormData.Options): Int

    @Query("UPDATE FormData SET votes = votes + 1 WHERE optionName = :optionName")
    suspend fun incrementVotes(optionName: FormData.Options)

//    @Insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(forms: List<FormData>)

    @Query("DELETE FROM FormData")
    suspend fun deleteAll()

//    @Query("SELECT * FROM FormData WHERE id IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<LocalFormData>

//    add later, insert suggestions in db. maybe need new db
//    @Query("SELECT * FROM FormData WHERE optionName LIKE :option")
//    fun findByName(option: String): LocalFormData
//    @Insert
//    fun insertAll(vararg options: localFormData)


//    @Query("UPDATE task SET isCompleted = :completed WHERE id = :taskId")
//    suspend fun updateCompleted(taskId: String, completed: Boolean)
}