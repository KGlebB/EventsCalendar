package com.example.eventscalendar
import android.content.Context
import org.json.JSONArray

data class Holiday(
    val name: String,
    val date: String,
    val description: String,
    val days: String?
)