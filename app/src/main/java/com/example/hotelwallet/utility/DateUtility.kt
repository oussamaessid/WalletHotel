package com.example.hotelwallet.utility

import android.content.Context
import com.example.hotelwallet.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*


fun String.getMonthAndYearFromDate(): String {
    val dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    val date1: OffsetDateTime = OffsetDateTime.parse(this, dateTimeFormatter)
    val fmt = DateTimeFormatter.ofPattern("MMMM yyyy")
    return "${fmt.format(date1)}"
}

fun Long.getDateFromTimestamp2(): String{
    return try {
        val sdf: DateFormat =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val netDate = Date(this*1000L)
        sdf.format(netDate)
    } catch (e: Exception) {
        "0"
    }
}


fun Long.getTimeAgo(context: Context): String {
    var time = this
    if (time < 1000000000000L) {
        time *= 1000
    }
    val now = System.currentTimeMillis()

    val diff = now - time
    return if (diff < MINUTE_MILLIS) {
        context.getString(R.string.txt_just_now)
    } else if (diff < 2 * MINUTE_MILLIS) {
        context.getString(R.string.txt_minute_ago)
    } else if (diff < 50 * MINUTE_MILLIS) {
        context.getString(R.string.txt_minutes_ago).format(diff.div(MINUTE_MILLIS))
    } else if (diff < 90 * MINUTE_MILLIS) {
        context.getString(R.string.txt_an_hour_ago)
    } else if (diff < 24 * HOUR_MILLIS) {
        context.getString(R.string.txt_hours_ago).format(diff.div(HOUR_MILLIS))
    } else if (diff < 48 * HOUR_MILLIS) {
        context.getString(R.string.txt_yesterday)
    } else if (diff < 7 * DAY_MILLIS){
        context.getString(R.string.txt_days_ago).format(diff.div(DAY_MILLIS))
    }else {
        this.getDateFromTimestamp2()
    }
}