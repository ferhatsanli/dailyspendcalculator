package com.ferhat.dailyspendcalculator

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE

class FunctionsData(context: Context) {
    val TAG = "DataFunc"
    private val dataName = "SalaryData"
    private val salaryName = "CurrentSalary"
    private val sharedPref = context.getSharedPreferences(dataName, Context.MODE_PRIVATE)

    fun saveSalary(salary: Int){
        val editor = sharedPref.edit()
        
        editor.putInt(salaryName, salary)
        editor.apply()
        Log.i(TAG, "saveSalary: new salary saved ($salary)")
    }

    fun getCurrentSalary(): Int{
        if (sharedPref.contains(salaryName)) {
            val currsalary = sharedPref.getInt(salaryName, 0)
            Log.i(TAG, "getCurrentSalary: $salaryName found ($currsalary)")
            return currsalary
        }
        Log.i(TAG, "getCurrentSalary: not found in db")
        return 0
    }
}