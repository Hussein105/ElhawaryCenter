package com.ho.elhawarycenter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ho.elhawarycenter.R
import com.ho.elhawarycenter.databinding.FragmentUpdateBottomSheetBinding

import com.ho.elhawarycenter.model.Case
import com.ho.elhawarycenter.viewmodel.UpdateBottomSheetViewModel

class UpdateBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var updateViewModel: UpdateBottomSheetViewModel
    private val args by navArgs<DetailsFragmentArgs>()
    private var _binding: FragmentUpdateBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBottomSheetBinding.inflate(inflater, container, false)
        updateViewModel = ViewModelProvider(this)[UpdateBottomSheetViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startDataHandler()
        binding.saveButtonUpdate.setOnClickListener {
            updateCaseData()
        }
    }

    private fun startDataHandler() {
        binding.apply {
            nameInputUpdate.setText(args.CurrentCase.name)
            ageInputUpdate.setText(args.CurrentCase.age)
            diagnosisInputUpdate.setText(args.CurrentCase.diagnosis)
            typeInputUpdate.setText(args.CurrentCase.type)
            notesInputUpdate.setText(args.CurrentCase.notes)
        }
    }

    private fun updateCaseData() {
        val newName = binding.nameInputUpdate.text.toString().trim()
        val newAge = binding.ageInputUpdate.text.toString().trim().toInt()
        val newDiagnosis = binding.diagnosisInputUpdate.text.toString().trim()
        val newType = binding.typeInputUpdate.text.toString().trim()
        val newNote = binding.notesInputUpdate.text.toString().trim()

        val newCase = Case(
            args.CurrentCase.id,
            newName,
            newAge,
            newDiagnosis,
            newType,
            args.CurrentCase.gender,
            args.CurrentCase.price,
            args.CurrentCase.paid,
            args.CurrentCase.remaining,
            args.CurrentCase.sessions,
            args.CurrentCase.tSessions,
            args.CurrentCase.rSessions,
            newNote,
            args.CurrentCase.attachmentUri,
            args.CurrentCase.admissionDate
        )

        try {
            updateViewModel.updateCaseData(newCase)
            Toast.makeText(
                requireContext(),
                getString(R.string.updated_toast),
                Toast.LENGTH_SHORT
            ).show()
        } catch (_: RuntimeException) {
            Toast.makeText(
                requireContext(),
                getString(R.string.exception_toast),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}