package com.example.clickstask_romany.utlis

import android.content.Context
import android.net.ConnectivityManager
import java.text.SimpleDateFormat
import java.util.*

class CommonMethod {
    fun checkNetworkConnection(context: Context): Boolean
    {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun getDayNameFromDate(inputDate : String) : String
    {
        val inFormat = SimpleDateFormat("yyyy-MM-dd", Locale("ar"))
        val date: Date = inFormat.parse(inputDate)
        val outFormat = SimpleDateFormat("EEEE", Locale("ar"))
        return outFormat.format(date)
    }

    fun separateString(dateStr : String) : String
    {
        val parts = dateStr.split("T")
        return parts[0]
    }
}