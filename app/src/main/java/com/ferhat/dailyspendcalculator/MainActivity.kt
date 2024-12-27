package com.ferhat.dailyspendcalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ferhat.dailyspendcalculator.databinding.ActivityMainBinding
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainFunc"
    private lateinit var funcsData: FunctionsData
    private lateinit var funcsCalculator: FunctionsCalculate
    private var currentSalary: Int = 0
    private var currentDate: String = ""
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Log.i(TAG, "onCreate: classes initializing")
        // Function Class init
        funcsData = FunctionsData(this)
        funcsCalculator = FunctionsCalculate(this)

        // Read money from previous session
        Log.i(TAG, "onCreate: reading salary")
        currentSalary = funcsData.getCurrentSalary()
        Log.i(TAG, "onCreate: salary=$currentSalary")
        binding.txtSalary.setText("$currentSalary")

        // Read date from previous session
        currentDate = funcsData.getDate()
        Log.i(TAG, "onCreate: date from db is '$currentDate'")
        binding.txtDate.setText(currentDate)

        binding.btnSalarySave.setOnClickListener() {
            saveSalary()
            saveDate()
        }

        binding.btnCalculate.setOnClickListener() {
            val money: Int = Integer.parseInt(binding.txtSalary.text.toString())
            val targetDate: String = binding.txtDate.text.toString()
            val day = Integer.parseInt(targetDate.split('/')[0])
            val month = Integer.parseInt(targetDate.split('/')[1])
            val year = Integer.parseInt(targetDate.split('/')[2])
            val totalDays: Long = funcsCalculator.daysBetween(day, month, year)
            val dailySpend: Int = funcsCalculator.dailySpend(money, totalDays)
            binding.txtResult.text = "$dailySpend Euro"
            binding.txtDaysLeft.text = "$totalDays days left"
            saveSalary()
            saveDate()
        }
    }

    fun saveSalary() {
        currentSalary = Integer.parseInt(binding.txtSalary.text.toString())
        funcsData.saveSalary(currentSalary)
        Log.i(TAG, "salary saved to db")
    }

    fun saveDate() {
        Log.i(TAG, "saveDate: saving date")
        funcsData.saveDate(binding.txtDate.text.toString())
    }
}

