package com.ho.elhawarycenter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ho.elhawarycenter.model.Case

@Dao
interface CaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCase(newCase: Case)

    @Query("SELECT * FROM cases ORDER BY id DESC")
    fun readAllCases(): LiveData<List<Case>>

    @Query("SELECT * FROM cases WHERE name LIKE '%' || :keyWord || '%'")
    fun searchCases(keyWord: String): LiveData<List<Case>>

    @Query("SELECT SUM(price) FROM cases")
    fun totalIncome(): Int

    @Query("UPDATE cases SET notes = :newNote WHERE id = :caseId")
    fun updateNote(caseId: Int, newNote: String)

    @Query("UPDATE cases SET price = :newPrice WHERE id = :caseId")
    fun updatePrice(caseId: Int, newPrice: String)

    @Query("UPDATE cases SET paid = :newPaid WHERE id = :caseId")
    fun updatePaid(caseId: Int, newPaid: String)

    @Query("UPDATE cases SET sessions = :newTotalSessions WHERE id = :caseId")
    fun updateTotalSessions(caseId: Int, newTotalSessions: String)

    @Query("UPDATE cases SET sessions = :newTakenSessions WHERE id = :caseId")
    fun updateTakenSessions(caseId: Int, newTakenSessions: String)

    @Delete
    suspend fun deleteCase(deletedCase: Case)

    @Query("DELETE FROM cases")
    suspend fun deleteAllCases()
}