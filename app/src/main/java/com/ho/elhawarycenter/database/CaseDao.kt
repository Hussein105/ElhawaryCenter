package com.ho.elhawarycenter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ho.elhawarycenter.model.Case

@Dao
interface CaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCase(case: Case)

    @Query("SELECT * FROM cases ORDER BY id DESC")
    fun readAllCases(): LiveData<List<Case>>

    @Update
    suspend fun updateCaseData(case: Case)

    @Delete
    suspend fun deleteCase(case: Case)

    @Query("DELETE FROM cases")
    suspend fun deleteAllCases()

}