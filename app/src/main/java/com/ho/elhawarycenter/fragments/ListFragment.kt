package com.ho.elhawarycenter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ho.elhawarycenter.R
import com.ho.elhawarycenter.adapter.CaseAdapter
import com.ho.elhawarycenter.databinding.FragmentListBinding
import com.ho.elhawarycenter.viewmodel.ListViewModel

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private lateinit var listViewModel: ListViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        listViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mCaseAdapter = CaseAdapter()
        binding.rvPatients.apply {
            adapter = mCaseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        listViewModel.readAllData.observe(viewLifecycleOwner) {
            mCaseAdapter.insertCaseData(it)
        }

        binding.fabAdd.setOnClickListener {
            val addBottomSheetFragment = AddBottomSheetFragment()
            addBottomSheetFragment.show(childFragmentManager, addBottomSheetFragment.tag)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_delete_all, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_deleteAll) {
            alertDialogBuilder()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun alertDialogBuilder() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.alert_dialogue_title))
        builder.setMessage(getString(R.string.alert_dialogue_message))
        val password = EditText(requireContext())
        val param = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        param.setMargins(8, 8, 8, 8)
        password.layoutParams = param
        password.hint = getString(R.string.key_hint)
        builder.setView(password)
        builder.setIcon(R.drawable.ic_delete)
        builder.setPositiveButton(getString(R.string.confirm)) { _, _ ->
            if (password.text.toString().trim() == getString(R.string.absolute_passkey)) {
                listViewModel.deleteAllCases()
                Toast.makeText(
                    requireContext(),
                    getString(R.string.delete_confirmation),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.delete_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
            Toast.makeText(
                requireContext(),
                getString(R.string.cancel_deletion),
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}