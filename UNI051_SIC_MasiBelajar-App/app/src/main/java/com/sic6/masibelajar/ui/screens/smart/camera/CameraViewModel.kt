package com.sic6.masibelajar.ui.screens.smart.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sic6.masibelajar.data.local.PrefManager
import com.sic6.masibelajar.domain.entities.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val prefManager: PrefManager,
) : ViewModel() {
    private val _state = MutableStateFlow(
        CameraScreenState(
            ipCamera = "",
//            ipCamera = "ws://10.33.35.199:8000/v1/main-con",
            timeThreshold = 10,
            points = listOf(
                Point(0, 0, 0),
                Point(1, 0,0),
                Point(2, 0, 0),
            )
        )
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = CameraScreenState(
                ipCamera = prefManager.getUrl(),
                timeThreshold = prefManager.getTimeThreshold(),
                targetClass = prefManager.getTargetClass(),
                points = prefManager.getPoints()
            )
        }
    }

    fun save() {
        viewModelScope.launch {
            prefManager.setUrl(state.value.ipCamera)
            prefManager.setTimeThreshold(state.value.timeThreshold)
            prefManager.setTargetClass(state.value.targetClass)
            prefManager.setPoints(state.value.points)
        }
    }

    fun setIpCamera(ipCamera: String) {
        _state.update { it.copy(ipCamera = ipCamera) }
    }

    fun setTimeThreshold(timeThreshold: String) {
        val time = timeThreshold.toIntOrNull() ?: 0
        _state.update { it.copy(timeThreshold = time) }
    }

    fun setPointX(index: Int, x: Int) {
        _state.update { state ->
            val points = state.points.toMutableList()
            points[index] = points[index].copy(x = x)
            state.copy(points = points)
        }
    }

    fun setPointY(index: Int, y: Int) {
        _state.update { state ->
            val points = state.points.toMutableList()
            points[index] = points[index].copy(y = y)
            state.copy(points = points)
        }
    }

    fun addPoint() {
        _state.update { state ->
            val points = state.points.toMutableList()
            points.add(Point(state.numberOfPoints + 1, 0, 0))
            state.copy(points = points)
        }
    }

    fun removePoint(index: Int) {
        _state.update { state ->
            val points = state.points.toMutableList()
            points.removeAt(index)
            state.copy(points = points)
        }
    }

    fun setPoint(newValue: String) {
        val number = newValue.toIntOrNull()
        if (number != null && number < 3) return

        val pointsLength = state.value.points.size

        viewModelScope.launch {
            if (number != null && pointsLength > 0) {
                // Check if the length of the points list is less than the number
                if (pointsLength < number) {
                    // Add points to the list
                    for (i in pointsLength until number) {
                        _state.update { state ->
                            val points = state.points.toMutableList()
                            points.add(Point(i, 0, 0))
                            state.copy(points = points)
                        }
                    }
                } else {
                    // Remove points from the list
                    for (i in pointsLength downTo number + 1) {
                        _state.update { state ->
                            val points = state.points.toMutableList()
                            points.removeAt(i - 1)
                            state.copy(points = points)
                        }
                    }
                }
            }
        }
    }

    fun addTarget(targetClass: String) {
        _state.update { state ->
            val targetClasses = state.targetClass.toMutableList()
            if (!targetClasses.contains(targetClass)) {
                targetClasses.add(targetClass)
            }
            state.copy(targetClass = targetClasses)
        }
    }

    fun removeTarget(targetClass: String) {
        _state.update { state ->
            val targetClasses = state.targetClass.toMutableList()
            if (targetClasses.contains(targetClass)) {
                targetClasses.remove(targetClass)
            }
            state.copy(targetClass = targetClasses)
        }
    }
}