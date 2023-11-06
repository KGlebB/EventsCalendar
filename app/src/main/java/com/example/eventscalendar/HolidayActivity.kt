package com.example.eventscalendar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.eventscalendar.ui.theme.EventsCalendarTheme

class HolidayActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.holiday)
        initBackButton()
        initData()
    }

    private fun initBackButton() {
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initData() {
        val intentName = intent.getStringExtra("holidayName")
        val intentDate = intent.getStringExtra("holidayDate")
        val intentDescription = intent.getStringExtra("holidayDescription")
        val intentDays = intent.getStringExtra("holidayDays")

        val holidayName = findViewById<TextView>(R.id.holidayName)
        val holidayDate = findViewById<TextView>(R.id.holidayDate)
        val holidayDescription = findViewById<TextView>(R.id.holidayDescription)
        val holidayDays = findViewById<TextView>(R.id.holidayDays)

        holidayName.text = intentName
        holidayDate.text = intentDate
        holidayDescription.text = intentDescription
        holidayDays.text = intentDays
    }
}
