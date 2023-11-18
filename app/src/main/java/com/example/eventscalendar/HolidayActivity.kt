package com.example.eventscalendar

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.preference.PreferenceManager

class HolidayActivity : ComponentActivity() {
    private var isDark: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.holiday)
        checkDarkMode()
        initBackButton()
        initData()
    }

    private fun checkDarkMode() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        isDark = sharedPreferences.getBoolean("dark_mode", false)
        if (isDark) {
            findViewById<RelativeLayout>(R.id.mainLayout).setBackgroundColor(Color.parseColor("#111111"))
        }
    }

    private fun initBackButton() {
        findViewById<Button>(R.id.backButton).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setupImage() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val pictureDisplay = sharedPreferences.getString("picture_display", "hide")
        val holidayImage = findViewById<ImageView>(R.id.holidayImage)
        val backImage = findViewById<ImageView>(R.id.backImage)
        val intentImageUrl = intent.getStringExtra("holidayImageUrl")
        val resId = resources.getIdentifier(intentImageUrl, "drawable", packageName)

        holidayImage.visibility = View.GONE
        backImage.visibility = View.GONE

        when (pictureDisplay) {
            "show" -> setImageViewVisibility(holidayImage, resId)
            "back" -> setImageViewVisibility(backImage, resId)
        }
    }

    private fun setImageViewVisibility(imageView: ImageView, resId: Int) {
        if (resId != 0) {
            val bImage = BitmapFactory.decodeResource(resources, resId)
            imageView.setImageBitmap(bImage)
            imageView.visibility = View.VISIBLE
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

        if (isDark) {
            setTextViewColor(holidayDate, "#CCCCCC")
            setTextViewColor(holidayDescription, "#CCCCCC")
        }

        holidayName.text = intentName
        holidayDate.text = intentDate
        holidayDescription.text = intentDescription
        holidayDays.text = intentDays

        setupImage()
    }

    private fun setTextViewColor(textView: TextView, color: String) {
        textView.setTextColor(Color.parseColor(color))
    }
}
