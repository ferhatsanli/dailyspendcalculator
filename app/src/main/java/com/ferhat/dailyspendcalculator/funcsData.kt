package com.ferhat.dailyspendcalculator

import android.content.Context
import android.util.Log
import java.time.LocalDate

class FunctionsData(context: Context) {
    val TAG = "DataFunc"
    private val dbName = "SalaryData"
    private val keySalary = "CurrentSalary"
    private val keyDate = "date"
    private val sharedPref = context.getSharedPreferences(dbName, Context.MODE_PRIVATE)

    private fun saveData(key: String, value: Any) {
        val editor = sharedPref.edit()

        when (value) {
            is Int -> editor.putInt(key, value)
            is String -> editor.putString(key, value)
        }
        editor.apply()
        Log.i(TAG, "saveData: $key saved, value is $value")
    }

//    private fun getData(key: String): Any? {
//        if (sharedPref.contains(key)) {
//            Log.i(TAG, "getData: key ($key) found")
//            when (sharedPref.getString("${key}_type", null)) {
//                "int" -> {
//                    Log.i(TAG, "getData: key=$key")
//                    return sharedPref.getInt(key, 0)
//                }
//
//                "string" -> return sharedPref.getString(key, "")
//                else -> {
//                    Log.i(TAG, "getData: 404: type=${key}_type")
//                    return null
//                }
//            }
//        }
//        return null
//    }

    fun saveSalary(salary: Int) {
        saveData(keySalary, salary)
    }

    fun saveDate(date: String) {
        saveData(keyDate, date)
    }

    fun getDate(): String {
        var result = sharedPref.getString(keyDate, "")
        if (result == null || result == "") {
            val plus30days = LocalDate.now().plusDays(30)
            result = "${plus30days.dayOfMonth}/${plus30days.monthValue}/${plus30days.year}"
        }
        return result
    }

    fun getCurrentSalary(): Int {
        Log.i(TAG, "getCurrentSalary: started")
//        return getData(keySalary) as Int

        val currsalary = sharedPref.getInt(keySalary, 0)
        Log.i(TAG, "getCurrentSalary: $keySalary found ($currsalary)")
        return currsalary
    }
}