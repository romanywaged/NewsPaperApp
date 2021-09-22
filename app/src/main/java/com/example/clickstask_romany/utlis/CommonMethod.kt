package com.example.clickstask_romany.utlis

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.TemporalQueries.localDate
import java.util.*


class CommonMethod {
    fun checkNetworkConnection(context: Context): Boolean
    {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFormatDate(inputDate : String) : String
    {
        var date = LocalDate.parse(inputDate)
        var formatter = DateTimeFormatter.ofPattern("ddMMMMyyyy", Locale("ar"))
        var formattedDate = date.format(formatter)
        return formattedDate
    }

    fun getDayNameFromDate(inputDate : String) : String
    {
        val inFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ar"))
        val date: Date = inFormat.parse(inputDate)
        val outFormat = SimpleDateFormat("EEEE", Locale("ar"))
        return outFormat.format(date)
    }

    fun separateTime(dateStr : String) : String
    {
        val parts = dateStr.split("T")
        return parts[1]
    }
    fun convert24Hto12H(oldTime : String) : String
    {
        try {
            val formatStr1 = SimpleDateFormat("HH:mm", Locale("ar"))
            val formatStr2 = SimpleDateFormat("hh:mm a", Locale("ar"))
            val formatStr3 = formatStr1.parse(oldTime)
            return formatStr2.format(formatStr3)

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }

    fun separateString(dateStr : String) : String
    {
        val parts = dateStr.split("T")
        return parts[0]
    }
}