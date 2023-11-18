package com.example.eventscalendar

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager

class MainActivity : ComponentActivity() {
    private lateinit var linearLayout: LinearLayout
    private var isDark: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        checkDarkMode()
        initEvents()
        initSettingsButton()
    }

    private fun checkDarkMode() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        isDark = sharedPreferences.getBoolean("dark_mode", false)
        if (isDark) {
            findViewById<LinearLayout>(R.id.mainLayout).setBackgroundColor(Color.parseColor("#000000"))
        }
    }

    private fun initSettingsButton() {
        val settingsButton: ImageButton = findViewById(R.id.settingsButton)
        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun initEvents() {
        linearLayout = findViewById(R.id.linearLayout)
        val holidayDataLoader = HolidayDataLoader(this)
        val holidayList = holidayDataLoader.loadHolidayFromJson()
        holidayList.forEach { initHoliday(it) }
    }

    private fun initHoliday(holiday: Holiday) {
        val button = createHolidayButton(holiday)
        setButtonAttributes(button)
        setButtonClickListener(button, holiday)
        linearLayout.addView(button)
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
        val bgColor = if (isDark) Color.parseColor("#18659B") else Color.parseColor("#5CB1F2")
        val textColor = if (isDark) Color.parseColor("#CCCCCC") else Color.parseColor("#FFFFFF")

        button.setBackgroundColor(bgColor)
        button.setTextColor(textColor)
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
            val intent = Intent(this, HolidayActivity::class.java).apply {
                putExtra("holidayName", holiday.name)
                putExtra("holidayDate", holiday.date)
                putExtra("holidayDescription", holiday.description)
                putExtra("holidayDays", holiday.days)
                putExtra("holidayImageUrl", holiday.imageUrl)
            }
            startActivity(intent)
        }
    }
}
