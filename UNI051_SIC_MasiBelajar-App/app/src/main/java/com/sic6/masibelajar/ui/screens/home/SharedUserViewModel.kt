package com.sic6.masibelajar.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sic6.masibelajar.data.local.PrefManager
import com.sic6.masibelajar.domain.dao.SharedUserDao
import com.sic6.masibelajar.domain.entities.SharedUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


//class SharedUserViewModel @Inject constructor(private val sharedUserDao: SharedUserDao, private val prefManager: PrefManager, ) : ViewModel() {
//    val _sharedUsers = MutableStateFlow(SharedUserState(email = "", url = ""))
//
//    init {
//        viewModelScope.launch {
//            _sharedUsers.value = sharedUserDao.getSharedUsers()
//        }
//    }
//    val state = _sharedUsers.asStateFlow()
//
//    fun addSharedUser(email: String) {
//        viewModelScope.launch {
//            val user = SharedUser(
//                id = email.hashCode(),
//                email = email,
//                url = prefManager.getUrl()
//            )
//            sharedUserDao.insertSharedUser(user)
//            _sharedUsers.value = sharedUserDao.getSharedUsers()
//        }
//    }
//
//    fun deleteAllSharedUsers() {
//        viewModelScope.launch {
//            sharedUserDao.deleteSharedUser()
//            _sharedUsers.value = emptyList()
//        }
//    }
//
//    fun save() {
//        viewModelScope.launch {
//            prefManager.setSharedUser(state.value.email)
//        }
//    }
//}

@HiltViewModel
class SharedUserViewModel @Inject constructor(
    private val sharedUserDao: SharedUserDao,
    private val prefManager: PrefManager,
) : ViewModel() {

    private val _state = MutableStateFlow(SharedUserState())
    val state = _state.asStateFlow()

    private val _sharedUsers = MutableStateFlow<List<SharedUser>>(emptyList())
    val sharedUsers = _sharedUsers.asStateFlow()

    init {
        viewModelScope.launch {
            loadSharedUsers()
        }
    }

    private suspend fun loadSharedUsers() {
        _sharedUsers.value = sharedUserDao.getSharedUsers()
    }

    fun onEmailChanged(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onUrlChanged(url: String) {
        _state.update { it.copy(url = url) }
    }

    fun addSharedUser() {
        viewModelScope.launch {
            val email = state.value.email.trim()
            if (email.isEmpty()) return@launch

            val user = SharedUser(
                id = email.hashCode(),
                email = email,
                url = prefManager.getUrl() // atau state.value.url jika ingin pakai input UI
            )
            sharedUserDao.insertSharedUser(user)
            loadSharedUsers()
        }
    }

    fun deleteAllSharedUsers() {
        viewModelScope.launch {
            sharedUserDao.deleteSharedUser()
            _sharedUsers.value = emptyList()
        }
    }

    fun save() {
        viewModelScope.launch {
            prefManager.setSharedUser(state.value.email)
        }
    }
}
