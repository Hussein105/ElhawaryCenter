package com.ho.elhawarycenter.repo

import androidx.lifecycle.LiveData
import com.ho.elhawarycenter.database.CaseDao
import com.ho.elhawarycenter.model.Case

class CaseRepo(private val caseDao: CaseDao) {
    val readAllData: LiveData<List<Case>> = caseDao.readAllCases()

    suspend fun addCase(case: Case) {
        caseDao.insertCase(case)
    }

    suspend fun updateCaseData(case: Case) {
        caseDao.updateCaseData(case)
    }

    suspend fun deleteCase(case: Case) {
        caseDao.deleteCase(case)
    }

    suspend fun deleteAllCases() {
        caseDao.deleteAllCases()
    }
}