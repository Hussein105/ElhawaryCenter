package com.ho.elhawarycenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ho.elhawarycenter.database.CaseDb
import com.ho.elhawarycenter.model.Case
import com.ho.elhawarycenter.repo.CaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Case>>
    private val caseRepo: CaseRepo

    init {
        val caseDao = CaseDb.getDatabase(application).caseDao()
        caseRepo = CaseRepo(caseDao)
        readAllData = caseRepo.readAllData
    }

    fun deleteAllCases() {
        viewModelScope.launch(Dispatchers.IO) {
            caseRepo.deleteAllCases()
        }
    }
}