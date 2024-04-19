package com.ho.elhawarycenter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.ho.elhawarycenter.R
import com.ho.elhawarycenter.fragments.ListFragmentDirections
import com.ho.elhawarycenter.model.Case
import java.util.Date

class CaseAdapter : RecyclerView.Adapter<CaseAdapter.RecyclerViewHolder>() {

    //list to filter
    private var fullCases = emptyList<Case>()

    //inner class for the adapter
    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.list_item, parent, false
                )
        )
    }

    override fun getItemCount(): Int {
        return fullCases.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentCase = fullCases[position]
        holder.itemView.apply {
            val tvName: TextView = findViewById(R.id.tv_name)
            val tvAge: TextView = findViewById(R.id.tv_age)
            val tvDiagnosis: TextView = findViewById(R.id.tv_diagnosis)
            val ivImage: ImageView = findViewById(R.id.iv_image)
            val tvDate: TextView = findViewById(R.id.tv_date)
            val moreClick: MaterialCardView = findViewById(R.id.cv_patient_list)

            val name = currentCase.name
            val age = currentCase.age
            val diagnosis = currentCase.diagnosis
            val date = currentCase.admissionDate
            val dateString = java.text.DateFormat.getDateInstance().format(Date(date))

            tvName.text = name.trim()
            tvAge.text = age.toString().trim()
            tvDiagnosis.text = diagnosis.trim()
            tvDate.text = dateString

            if (currentCase.type.contains("Neuro")) {
                ivImage.setImageResource(R.drawable.ic_neurology)
            } else if (currentCase.type.contains("Ped")) {
                ivImage.setImageResource(R.drawable.ic_pediatrics)
            } else {
                ivImage.setImageResource(R.drawable.ic_orthopedics)
            }

            moreClick.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToDetailsFragment(currentCase)
                findNavController().navigate(action)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun insertCaseData(cases: List<Case>) {
        this.fullCases = cases
        notifyDataSetChanged()
    }

    fun getData(): List<Case> {
        return fullCases
    }
}