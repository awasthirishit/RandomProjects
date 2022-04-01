package com.example.covidvaccinetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val centerList : List<DataModelClass>):RecyclerView.Adapter<Adapter.viewHolder>() {
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val centerName: TextView = itemView.findViewById(R.id.CenterName)
        val centerAddress: TextView = itemView.findViewById(R.id.CenterAddress)
        val centerTimings: TextView = itemView.findViewById(R.id.CenterTimings)
        val vaccineName: TextView = itemView.findViewById(R.id.VaccineName)
        val centerAgeLimit: TextView = itemView.findViewById(R.id.AgeLimit)
        val centerFeeType: TextView = itemView.findViewById(R.id.FeeType)
        val avalability: TextView = itemView.findViewById(R.id.Avaliablity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_item,
            parent, false
        )
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = centerList[position]

        holder.centerName.text = currentItem.centerName
        holder.centerAddress.text = currentItem.centerAddress
        holder.centerTimings.text = ("From : " + currentItem.centerFromTime + " To : " + currentItem.centerToTime)
        holder.vaccineName.text = currentItem.vaccineName
        holder.centerAgeLimit.text = "Age Limit : " + currentItem.ageLimit.toString()
        holder.centerFeeType.text = currentItem.fee_type
        holder.avalability.text = "Availability : " + currentItem.availableCapacity.toString()
    }

    override fun getItemCount(): Int {
        return centerList.size
    }
}