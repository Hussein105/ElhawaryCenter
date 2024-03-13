package com.ho.elhawarycenter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ho.elhawarycenter.R
import com.ho.elhawarycenter.databinding.FragmentAddBottomSheetBinding
import com.ho.elhawarycenter.model.Case
import com.ho.elhawarycenter.viewmodel.AddBottomSheetViewModel
import java.util.Date

class AddBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var addViewModel: AddBottomSheetViewModel
    private var _binding: FragmentAddBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBottomSheetBinding.inflate(inflater, container, false)
        addViewModel = ViewModelProvider(this)[AddBottomSheetViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            saveButton.setOnClickListener {
                val remaining =
                    totalPriceInput.text.toString().toFloat() - amountPaidInput.text.toString()
                        .toFloat()
                val rSessions =
                    totalSessionsInput.text.toString().toInt() - takenSessionsInput.text.toString()
                        .toInt()
                val case = Case(
                    0,
                    nameInput.text.toString(),
                    ageInput.text.toString().toFloat(),
                    diagnosisInput.text.toString(),
                    typeInput.text.toString(),
                    sexInput.text.toString(),
                    totalPriceInput.text.toString().toFloat(),
                    amountPaidInput.text.toString().toFloat(),
                    remaining,
                    totalSessionsInput.text.toString().toInt(),
                    takenSessionsInput.text.toString().toInt(),
                    rSessions,
                    notesInput.text.toString(),
                    getString(R.string.place_holder),
                    Date().time
                )
                try {
                    addViewModel.addCase(case)
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.saved_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                    this@AddBottomSheetFragment.dismiss()
                } catch (_: RuntimeException) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.exception_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}