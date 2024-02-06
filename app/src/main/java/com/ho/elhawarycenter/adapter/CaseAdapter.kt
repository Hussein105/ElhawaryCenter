package com.ho.elhawarycenter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ho.elhawarycenter.R
import com.ho.elhawarycenter.fragments.ListFragmentDirections
import com.ho.elhawarycenter.model.Case

class CaseAdapter : RecyclerView.Adapter<CaseAdapter.RecyclerViewHolder>() {

    private var cases = emptyList<Case>()

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
        return cases.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val currentCase = cases[position]
        holder.itemView.apply {
            val tvName: TextView = findViewById(R.id.tv_name)
            val tvAge: TextView = findViewById(R.id.tv_age)
            val tvDiagnosis: TextView = findViewById(R.id.tv_diagnosis)
            val ivImage: ImageView = findViewById(R.id.iv_image)
            val btnMore: Button = findViewById(R.id.btn_viewMore)

            val name = currentCase.name
            val age = currentCase.age
            val diagnosis = currentCase.diagnosis

            tvName.text = name.trim()
            tvAge.text = age.toString().trim()
            tvDiagnosis.text = diagnosis.trim()

            if (currentCase.gender == "Female" && currentCase.age >= 18) {
                ivImage.setImageResource(R.drawable.ic_avatar_female_child)
            } else if (currentCase.gender == "Female") {
                ivImage.setImageResource(R.drawable.ic_avatar_female)
            } else if (currentCase.gender == "Male" && currentCase.age >= 18) {
                ivImage.setImageResource(R.drawable.ic_avatar_male_child)
            } else {
                ivImage.setImageResource(R.drawable.ic_avatar_male)
            }

            btnMore.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToDetailsFragment(currentCase)
                findNavController().navigate(action)
            }
        }
    }

    fun insertCaseData(case: List<Case>) {
        this.cases = case
        notifyDataSetChanged()
    }
}