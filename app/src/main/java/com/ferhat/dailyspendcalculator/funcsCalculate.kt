package com.ferhat.dailyspendcalculator

import android.content.Context
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class FunctionsCalculate(context: Context) {
    fun daysBetween(day: Int, month: Int, year: Int): Long {
        return ChronoUnit.DAYS.
            between(LocalDate.now(), LocalDate.of(year, month, day))
    }

    fun dailySpend(money: Int, days: Long): Int{
        return money / days.toInt()
    }
}