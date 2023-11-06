package com.example.eventscalendar

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import org.json.JSONArray
import java.util.Locale
import android.graphics.Color
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    private lateinit var holidayList: List<Holiday>
    private val holidayDataLoader = HolidayDataLoader(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        holidayList = holidayDataLoader.loadHolidayFromJson()
        listEvents()
    }

    private fun listEvents() {
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)
        for (holiday in holidayList) {
            val button = createHolidayButton(holiday)
            setButtonAttributes(button)
            setButtonClickListener(button, holiday)
            linearLayout.addView(button)
        }
    }

    private fun createHolidayButton(holiday: Holiday): Button {
        val button = Button(this)
        val spannableText = createSpannableText(holiday)
        button.text = spannableText
        return button
    }

    private fun createSpannableText(holiday: Holiday): SpannableString {
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
        return spannableText
    }

    private fun setButtonAttributes(button: Button) {
        button.setBackgroundColor(Color.parseColor("#5CB1F2"))
        button.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        button.setPadding(0, 24, 0, 24)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, 0, 0, 32)
        button.layoutParams = layoutParams
    }

    private fun setButtonClickListener(button: Button, holiday: Holiday) {
        button.setOnClickListener {
            val intent = Intent(this, HolidayActivity::class.java)
            intent.putExtra("holidayName", holiday.name)
            intent.putExtra("holidayDate", holiday.date)
            intent.putExtra("holidayDescription", holiday.description)
            intent.putExtra("holidayDays", holiday.days)
            startActivity(intent)
        }
    }
}
