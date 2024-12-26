package com.ferhat.dailyspendcalculator

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ferhat.dailyspendcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainAct"
    private lateinit var funcsData: FunctionsData
    private lateinit var funcsCalculator: FunctionsCalculate
    private var currentSalary: Int = 0
    private lateinit var binding: ActivityMainBinding

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
        funcsData = FunctionsData(this)
        funcsCalculator = FunctionsCalculate(this)

        currentSalary = funcsData.getCurrentSalary()
        Log.i(TAG, "onCreate: salary=$currentSalary")
        binding.txtSalary.setText("$currentSalary")


        binding.btnSalarySave.setOnClickListener() {
            saveSalary()
        }

        binding.btnCalculate.setOnClickListener(){
            val money: Int = Integer.parseInt(binding.txtSalary.text.toString())
            val targetDate: String = binding.txtDate.text.toString()
            val day = Integer.parseInt(targetDate.split('/')[0])
            val month = Integer.parseInt(targetDate.split('/')[1])
            val year = Integer.parseInt(targetDate.split('/')[2])
            val totalDays: Long = funcsCalculator.daysBetween(day,  month, year)
            val dailySpend: Int = funcsCalculator.dailySpend(money, totalDays)
            binding.txtResult.text = "$dailySpend"
            saveSalary()
        }
    }

    fun saveSalary(){
        currentSalary = Integer.parseInt(binding.txtSalary.text.toString())
        funcsData.saveSalary(currentSalary)
        Log.i(TAG,"salary saved to db")
    }
}

