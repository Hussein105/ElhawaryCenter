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
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ho.elhawarycenter.R
import com.ho.elhawarycenter.adapter.CaseAdapter
import com.ho.elhawarycenter.databinding.FragmentListBinding
import com.ho.elhawarycenter.viewmodel.ListViewModel

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private lateinit var listViewModel: ListViewModel
    private lateinit var mCaseAdapter: CaseAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
//        setHasOptionsMenu(true)
        listViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(MyMenuProvider(), viewLifecycleOwner, Lifecycle.State.RESUMED)

        mCaseAdapter = CaseAdapter()
        binding.rvPatients.apply {
            adapter = mCaseAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        listViewModel.readAllData.observe(viewLifecycleOwner) {
            mCaseAdapter.insertCaseData(it)
        }

        listViewModel.searchResult.observe(viewLifecycleOwner) {
            mCaseAdapter.insertCaseData(it)
        }

        binding.fabAdd.setOnClickListener {
            val addBottomSheetFragment = AddBottomSheetFragment()
            addBottomSheetFragment.show(childFragmentManager, addBottomSheetFragment.tag)
        }
    }

    private fun deleteAllDialogBuilder() {
        val builder = MaterialAlertDialogBuilder(requireContext())
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

    inner class MyMenuProvider : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.menu_main, menu)

            val searchItem = menu.findItem(R.id.action_search)
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        listViewModel.search(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        listViewModel.search(newText)
                    }
                    return true
                }
            })
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.action_deleteAll -> deleteAllDialogBuilder()
            }
            return true
        }

    }
}