package com.example.eventscalendar
import android.content.Context
import org.json.JSONArray
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class HolidayDataLoader(private val context: Context) {

    fun loadHolidayFromJson(): List<Holiday> {
        val holidayJsonString = loadHolidayJsonString()
        return parseHolidayJson(holidayJsonString)
    }

    private fun loadHolidayJsonString(): String {
        return try {
            val inputStream = context.resources.openRawResource(R.raw.holidays)
            inputStream.bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }

    private fun parseHolidayJson(jsonString: String): List<Holiday> {
        val holidayList = mutableListOf<Holiday>()
        val holidayJsonArray = JSONArray(jsonString)

        for (i in 0 until holidayJsonArray.length()) {
            val holidayJsonObject = holidayJsonArray.getJSONObject(i)
            val name = holidayJsonObject.getString("name")
            val date = holidayJsonObject.getString("date")
            val formattedDate = formatDate(date)
            val description = holidayJsonObject.getString("description")
            val days = holidayJsonObject.optString("days", null)
            val holiday = Holiday(name, formattedDate, description, days)
            holidayList.add(holiday)
        }
        return holidayList
    }


    private fun formatDate(dateString: String): String {
        val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = DateTimeFormatter.ofPattern("d MMMM", Locale("ru", "RU"))
        return try {
            val date = LocalDate.parse(dateString, inputFormat)
            date.format(outputFormat)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}