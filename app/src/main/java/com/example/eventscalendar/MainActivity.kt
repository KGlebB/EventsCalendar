package com.example.eventscalendar

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Typeface
import android.os.Bundle
import android.provider.CalendarContract
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.json.JSONArray
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale
import android.graphics.Color
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    private lateinit var holidayList: List<Holiday>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        loadHolidaysFromJson()
        listEvents()
    }

    private fun listEvents() {
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)
        for (holiday in holidayList) {
            val button = Button(this)
            val spannableText = SpannableString("${holiday.name}\n${holiday.date}")
            spannableText.setSpan(
                StyleSpan(Typeface.BOLD),
                0,
                holiday.name.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableText.setSpan(
                RelativeSizeSpan(0.9f),
                holiday.name.length + 1,
                spannableText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            button.text = spannableText
            button.setBackgroundColor(Color.parseColor("#5CB1F2"))
            button.setTextColor(
                ContextCompat.getColor(this, android.R.color.white)
            )
            button.setPadding(0,24,0,24)
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(0, 0, 0, 32)
            button.layoutParams = layoutParams
            button.setOnClickListener {
                val intent = Intent(this, HolidayActivity::class.java)
                intent.putExtra("holidayName", holiday.name)
                intent.putExtra("holidayDate", holiday.date)
                intent.putExtra("holidayDescription", holiday.description)
                intent.putExtra("holidayDays", holiday.days)
                startActivity(intent)
            }
            linearLayout.addView(button)
        }
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

    private fun loadHolidaysFromJson() {
        val holidayList = mutableListOf<Holiday>()
        try {
            val inputStream = resources.openRawResource(R.raw.holidays)
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString("name")
                val date = jsonObject.getString("date")
                val formattedDate = formatDate(date)
                val description = jsonObject.getString("description")
                val days = jsonObject.optString("days", null)
                val holiday = Holiday(name, formattedDate, description, days)
                holidayList.add(holiday)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        this.holidayList = holidayList
    }
}
