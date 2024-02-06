package com.ho.elhawarycenter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ho.elhawarycenter.database.CaseDb
import com.ho.elhawarycenter.model.Case
import com.ho.elhawarycenter.repo.CaseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddBottomSheetViewModel(application: Application) : AndroidViewModel(application) {

    private val caseRepo: CaseRepo

    init {
        val caseDao = CaseDb.getDatabase(application).caseDao()
        caseRepo = CaseRepo(caseDao)
    }

    fun addCase(case: Case) {
        viewModelScope.launch(Dispatchers.IO) {
            caseRepo.addCase(case)
        }
    }
}