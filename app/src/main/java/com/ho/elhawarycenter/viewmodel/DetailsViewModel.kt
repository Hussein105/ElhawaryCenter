package com.ho.elhawarycenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ho.elhawarycenter.database.CaseDb
import com.ho.elhawarycenter.model.Case
import com.ho.elhawarycenter.repo.CaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val caseRepo: CaseRepo

    init {
        val caseDao = CaseDb.getDatabase(application).caseDao()
        caseRepo = CaseRepo(caseDao)
    }

    fun updateNote(caseId: Int, newNote: String) {
        viewModelScope.launch(Dispatchers.IO) {
            caseRepo.updateNote(caseId, newNote)
        }
    }

    fun updatePrice(caseId: Int, newPrice: String) {
        viewModelScope.launch(Dispatchers.IO) {
            caseRepo.updatePrice(caseId, newPrice)
        }
    }

    fun updatePaid(caseId: Int, newPaid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            caseRepo.updatePaid(caseId, newPaid)
        }
    }

    fun updateTotalSessions(caseId: Int, newTotalSessions: String) {
        viewModelScope.launch(Dispatchers.IO) {
            caseRepo.updateTotalSessions(caseId, newTotalSessions)
        }
    }

    fun updateTakenSessions(caseId: Int, newTakenSessions: String) {
        viewModelScope.launch(Dispatchers.IO) {
            caseRepo.updateTakenSessions(caseId, newTakenSessions)
        }
    }

    fun deleteCase(case: Case) {
        viewModelScope.launch(Dispatchers.IO) {
            caseRepo.deleteCase(case)
        }
    }

}