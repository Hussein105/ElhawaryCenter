package com.ho.elhawarycenter.repo

import androidx.lifecycle.LiveData
import com.ho.elhawarycenter.database.CaseDao
import com.ho.elhawarycenter.model.Case

class CaseRepo(private val caseDao: CaseDao) {
    val readAllData: LiveData<List<Case>> = caseDao.readAllCases()
    suspend fun addCase(newCase: Case) {
        caseDao.insertCase(newCase)
    }

    suspend fun getTotalIncome() {
        caseDao.totalIncome()
    }

    suspend fun updatePrice(caseId: Int, newPrice: String) {
        caseDao.updatePrice(caseId, newPrice)
    }

    suspend fun updatePaid(caseId: Int, newPaid: String) {
        caseDao.updatePaid(caseId, newPaid)
    }

    suspend fun updateTotalSessions(caseId: Int, newTotalSessions: String) {
        caseDao.updateTotalSessions(caseId, newTotalSessions)
    }

    suspend fun updateTakenSessions(caseId: Int, newTakenSessions: String) {
        caseDao.updateTakenSessions(caseId, newTakenSessions)
    }

    suspend fun updateNote(caseId: Int, newNote: String) {
        caseDao.updateNote(caseId, newNote)
    }

    suspend fun deleteCase(deletedCase: Case) {
        caseDao.deleteCase(deletedCase)
    }

    suspend fun deleteAllCases() {
        caseDao.deleteAllCases()
    }
}