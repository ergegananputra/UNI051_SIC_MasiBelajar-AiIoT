package com.sic6.masibelajar.data.local

import android.content.Context
import com.sic6.masibelajar.domain.entities.Point
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PrefManager @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun setRoomName(name: String) {
        editor.putString("room_name", name).apply()
    }

    fun getRoomName(): String {
        return sharedPreferences.getString("room_name", "Room 1") ?: "Room 1"
    }

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
        val pointsString = if (points.size < 3) {
            "0,0,0;1,0,0;2,0,0"
        } else {
            points.joinToString(";") { "${it.id},${it.x},${it.y}" }
        }
        editor.putString("points", pointsString).apply()
    }

    fun getPoints(): List<Point> {
        val points = sharedPreferences.getString("points", "0,0,0;1,0,0;2,0,0")
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

    fun setSharedUser(email : String?) {
        editor.putString("emailSharedUser", email).apply()
    }

    fun getSharedUser() : String? {
        return sharedPreferences.getString("emailSharedUser", null)
    }

    fun clear() {
        editor.clear().apply()
    }

}