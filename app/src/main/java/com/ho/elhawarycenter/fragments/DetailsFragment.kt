package com.ho.elhawarycenter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ho.elhawarycenter.R
import com.ho.elhawarycenter.databinding.FragmentDetailsBinding
import com.ho.elhawarycenter.viewmodel.DetailsViewModel

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
                "${getString(R.string.date_of_admission)} ${args.CurrentCase.admissionDate}"

            if (args.CurrentCase.age >= 18 && args.CurrentCase.gender == "Male") {
                ivAvatar.setImageResource(R.drawable.ic_avatar_male_child)
            } else if (args.CurrentCase.age >= 18 && args.CurrentCase.gender == "Female") {
                ivAvatar.setImageResource(R.drawable.ic_avatar_female_child)
            } else if (args.CurrentCase.gender == "Female") {
                ivAvatar.setImageResource(R.drawable.ic_avatar_female)
            } else {
                ivAvatar.setImageResource(R.drawable.ic_avatar_male)
            }

            btnDetailsEdit.setOnClickListener {
                findNavController().navigate(
                    R.id.action_detailsFragment_to_updateBottomSheetFragment,
                    requireArguments()
                )
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_deleteAll) {
            deletePatientData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deletePatientData() {
        val builder = AlertDialog.Builder(requireContext())
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