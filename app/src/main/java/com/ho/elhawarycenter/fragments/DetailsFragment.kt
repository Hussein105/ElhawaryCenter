package com.ho.elhawarycenter.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ho.elhawarycenter.R
import com.ho.elhawarycenter.databinding.FragmentDetailsBinding
import com.ho.elhawarycenter.viewmodel.DetailsViewModel
import java.util.Date

class DetailsFragment : Fragment() {

    private lateinit var detailsViewModel: DetailsViewModel
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        detailsViewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            tvDetailsName.text = "${getString(R.string.name)} ${args.CurrentCase.name}"
            tvDetailsAge.text = "${getString(R.string.age)} ${args.CurrentCase.age}"
            tvDetailsDiagnosis.text =
                "${getString(R.string.diagnosis)} ${args.CurrentCase.diagnosis}"
            tvDetailsType.text = "${getString(R.string.case_type)} ${args.CurrentCase.type}"
            tvDetailsGender.text = "${getString(R.string.gender)} ${args.CurrentCase.gender}"
            tvDetailsTotalPrice.text =
                "${getString(R.string.total_price)} ${args.CurrentCase.price}"
            tvDetailsPaid.text = "${getString(R.string.money_paid)} ${args.CurrentCase.paid}"
            tvDetailsRemainingMoney.text =
                "${getString(R.string.amount_remaining)} ${args.CurrentCase.remaining}"
            tvDetailsTotalSessions.text =
                "${getString(R.string.total_sessions)} ${args.CurrentCase.sessions}"
            tvDetailsTakenSessions.text =
                "${getString(R.string.taken_sessions)} ${args.CurrentCase.tSessions}"
            tvDetailsRemainingSessions.text =
                "${getString(R.string.remaining_sessions)} ${args.CurrentCase.rSessions}"
            tvDetailsNotes.text = "${getString(R.string.notes)} ${args.CurrentCase.notes}"
            tvDetailsDate.text =
                "${getString(R.string.date_of_admission)} ${
                    java.text.DateFormat.getDateInstance().format(
                        Date(args.CurrentCase.admissionDate)
                    )
                }"

            if (args.CurrentCase.type.contains("Neuro")) {
                ivAvatar.setImageResource(R.drawable.ic_neurology)
            } else if (args.CurrentCase.type.contains("Ped")) {
                ivAvatar.setImageResource(R.drawable.ic_pediatrics)
            } else {
                ivAvatar.setImageResource(R.drawable.ic_orthopedics)
            }

            btnDetailsEdit.setOnClickListener {
                updateNoteDialogBuilder(tvDetailsNotes)
            }

            icnBtnAddTPrice.setOnClickListener {
                updatePriceDialogBuilder(tvDetailsTotalPrice)
            }

            icnBtnAddPaid.setOnClickListener {
                updatePaidDialogBuilder(tvDetailsPaid)
            }

            icnBtnAddTSessions.setOnClickListener {
                updateTSessionsDialogBuilder(tvDetailsTotalSessions)
            }

            icnBtnAddTakenSessions.setOnClickListener {
                updateTakenSessionsDialogBuilder(tvDetailsTakenSessions)
            }
        }
    }

    private fun updateTakenSessionsDialogBuilder(newTakenSession: TextView) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Update ${args.CurrentCase.name} taken sessions")
        builder.setMessage("Please insert new value")
        val newValue = EditText(requireContext())
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        param.setMargins(16, 8, 16, 8)
        newValue.layoutParams = param
        newValue.hint = "insert new value"
        builder.setView(newValue)
        builder.setIcon(R.drawable.ic_edit)
        builder.setPositiveButton(getString(R.string.confirm)) { _, _ ->
            try {
                val takenSessions = newValue.text.toString()
                val caseId = args.CurrentCase.id
                detailsViewModel.updateTakenSessions(caseId, takenSessions)
                newTakenSession.text = "${getString(R.string.taken_sessions)} $takenSessions"
                Toast.makeText(
                    requireContext(),
                    "updated",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (_: RuntimeException) {
                Toast.makeText(
                    requireContext(),
                    "Failed to update",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
            Toast.makeText(
                requireContext(),
                "Nothing changed",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.create().show()
    }

    private fun updateTSessionsDialogBuilder(newTotalSessions: TextView) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Update ${args.CurrentCase.name} total sessions")
        builder.setMessage("Please insert new value")
        val newValue = EditText(requireContext())
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        param.setMargins(16, 8, 16, 8)
        newValue.layoutParams = param
        newValue.hint = "insert new value"
        builder.setView(newValue)
        builder.setIcon(R.drawable.ic_edit)
        builder.setPositiveButton(getString(R.string.confirm)) { _, _ ->
            try {
                val totalSessions = newValue.text.toString()
                val caseId = args.CurrentCase.id
                detailsViewModel.updateTotalSessions(caseId, totalSessions)
                newTotalSessions.text = "${getString(R.string.total_sessions)} $totalSessions"
                Toast.makeText(
                    requireContext(),
                    "updated",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (_: RuntimeException) {
                Toast.makeText(
                    requireContext(),
                    "Failed to update",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
            Toast.makeText(
                requireContext(),
                "Nothing changed",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.create().show()
    }

    private fun updatePaidDialogBuilder(newPaid: TextView) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Update ${args.CurrentCase.name} paid amount")
        builder.setMessage("Please insert new value")
        val newValue = EditText(requireContext())
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        param.setMargins(16, 8, 16, 8)
        newValue.layoutParams = param
        newValue.hint = "insert new value"
        builder.setView(newValue)
        builder.setIcon(R.drawable.ic_edit)
        builder.setPositiveButton(getString(R.string.confirm)) { _, _ ->
            try {
                val paid = newValue.text.toString()
                val caseId = args.CurrentCase.id
                detailsViewModel.updatePaid(caseId, paid)
                newPaid.text = "${getString(R.string.money_paid)} $paid"
                Toast.makeText(
                    requireContext(),
                    "updated",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (_: RuntimeException) {
                Toast.makeText(
                    requireContext(),
                    "Failed to update",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
            Toast.makeText(
                requireContext(),
                "Nothing changed",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.create().show()
    }

    private fun updatePriceDialogBuilder(newPrice: TextView) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Update ${args.CurrentCase.name} Price")
        builder.setMessage("Please insert new value")
        val newValue = EditText(requireContext())
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        param.setMargins(16, 8, 16, 8)
        newValue.layoutParams = param
        newValue.hint = "insert new value"
        builder.setView(newValue)
        builder.setIcon(R.drawable.ic_edit)
        builder.setPositiveButton(getString(R.string.confirm)) { _, _ ->
            try {
                val price = newValue.text.toString()
                val caseId = args.CurrentCase.id
                detailsViewModel.updatePrice(caseId, price)
                newPrice.text = "${getString(R.string.total_price)} $price"
                Toast.makeText(
                    requireContext(),
                    "updated",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (_: RuntimeException) {
                Toast.makeText(
                    requireContext(),
                    "Failed to update",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
            Toast.makeText(
                requireContext(),
                "Nothing changed",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.create().show()
    }

    private fun updateNoteDialogBuilder(newNote: TextView) {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Update ${args.CurrentCase.name} notes")
        builder.setMessage("Please insert your updates")
        val newValue = EditText(requireContext())
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        param.setMargins(16, 8, 16, 8)
        newValue.layoutParams = param
        newValue.hint = "insert your notes"
        builder.setView(newValue)
        builder.setIcon(R.drawable.ic_edit)
        builder.setPositiveButton(getString(R.string.confirm)) { _, _ ->
            try {
                val note = newValue.text.toString()
                val caseId = args.CurrentCase.id
                detailsViewModel.updateNote(caseId, note)
                newNote.text = "${getString(R.string.notes)} $note"
                Toast.makeText(
                    requireContext(),
                    "Notes updated",
                    Toast.LENGTH_SHORT
                ).show()
            } catch (_: RuntimeException) {
                Toast.makeText(
                    requireContext(),
                    "Failed to update notes",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
            Toast.makeText(
                requireContext(),
                "Nothing changed",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.create().show()
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete) {
            deletePatientData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deletePatientData() {
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            detailsViewModel.deleteCase(args.CurrentCase)
            Toast.makeText(
                requireContext(),
                "Successfully Removed ${args.CurrentCase.name} !",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_detailsFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ ->
            Toast.makeText(
                requireContext(),
                "Your data still safe",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setTitle("Delete ${args.CurrentCase.name}")
        builder.setMessage("Confirming removing ${args.CurrentCase.name}")
        builder.create().show()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}