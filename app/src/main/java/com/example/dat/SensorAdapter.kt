package com.example.dat

import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dat.databinding.SensorItemBinding

class SensorAdapter : RecyclerView.Adapter<SensorAdapter.SensorViewHolder>() {

    private var sensorList: List<Sensor> = listOf()

    fun updateSensors(newSensors: List<Sensor>) {
        sensorList = newSensors
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        val binding = SensorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SensorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        val sensor = sensorList[position]
        holder.binding.sensorName.text = sensor.name
        holder.binding.sensorType.text = "Тип: ${sensor.type}"
    }

    override fun getItemCount(): Int = sensorList.size

    inner class SensorViewHolder(val binding: SensorItemBinding) : RecyclerView.ViewHolder(binding.root)
}
