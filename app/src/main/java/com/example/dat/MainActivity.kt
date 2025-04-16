package com.example.dat

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorAdapter: SensorAdapter

    private val categories = mapOf(
        "Окружающая среда" to listOf(
            Sensor.TYPE_AMBIENT_TEMPERATURE,
            Sensor.TYPE_LIGHT,
            Sensor.TYPE_PRESSURE,
            Sensor.TYPE_RELATIVE_HUMIDITY
        ),
        "Положение устройства" to listOf(
            Sensor.TYPE_ACCELEROMETER,
            Sensor.TYPE_GYROSCOPE,
            Sensor.TYPE_MAGNETIC_FIELD,
            Sensor.TYPE_GRAVITY,
            Sensor.TYPE_ROTATION_VECTOR
        ),
        "Состояние человека" to listOf(
            Sensor.TYPE_HEART_RATE,
            Sensor.TYPE_STEP_COUNTER,
            Sensor.TYPE_STEP_DETECTOR,
            Sensor.TYPE_SIGNIFICANT_MOTION
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        sensorAdapter = SensorAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = sensorAdapter

        val categoryNames = categories.keys.toList()
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = spinnerAdapter

        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedCategory = categoryNames[position]
                val sensorTypes = categories[selectedCategory] ?: listOf()
                val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
                    .filter { it.type in sensorTypes }
                sensorAdapter.updateSensors(sensors)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}
