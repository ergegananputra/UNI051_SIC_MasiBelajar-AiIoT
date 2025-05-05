package com.sic6.masibelajar.data.local

import android.content.Context
import com.sic6.masibelajar.domain.entities.Point
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PrefManager @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun setUrl(url: String) {
        editor.putString("url", url).apply()
    }

    fun getUrl(): String {
        return sharedPreferences.getString("url", "") ?: ""
    }

    fun setTimeThreshold(timeThreshold: Int) {
        editor.putInt("timeThreshold", timeThreshold).apply()
    }

    fun getTimeThreshold(): Int {
        return sharedPreferences.getInt("timeThreshold", 5)
    }

    fun setTargetClass(targetClass: List<String>) {
        editor.putString("targetClass", targetClass.joinToString(",")).apply()
    }

    fun getTargetClass(): List<String> {
        val targetClass = sharedPreferences.getString("targetClass", "toddler,non-toddler")
        return targetClass?.split(",") ?: emptyList()
    }

    fun setPoints(points: List<Point>) {
        editor.putString("points", points.joinToString(",") { "${it.id},${it.x},${it.y}" }).apply()
    }

    fun getPoints(): List<Point> {
        val points = sharedPreferences.getString("points", "1,0,0;2,0,0;3,0,0")
        return points?.split(";")?.map { segment ->
            val values = segment.split(",").map { it.toInt() }
            Point(values[0], values[1], values[2])
        }
            ?: listOf(
                Point(0, 0, 0),
                Point(1, 0,0),
                Point(2, 0, 0),
            )
    }

    fun setLoggedInEmail(email: String?) {
        editor.putString("loggedInEmail", email).apply()
    }

    fun getLoggedInEmail(): String? {
        return sharedPreferences.getString("loggedInEmail", null)
    }

    fun clear() {
        editor.clear().apply()
    }
}