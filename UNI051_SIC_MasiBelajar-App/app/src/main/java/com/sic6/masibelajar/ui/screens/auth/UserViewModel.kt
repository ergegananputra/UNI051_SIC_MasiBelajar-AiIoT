package com.sic6.masibelajar.ui.screens.auth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.sic6.masibelajar.data.local.PrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val prefManager: PrefManager
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    init {
        _email.value = prefManager.getLoggedInEmail() ?: ""
    }

    fun signInUser(email: String, password: String, callback: (Result<Unit>) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _email.value = email
                    prefManager.setLoggedInEmail(email)
                    callback(Result.success(Unit))
                } else {
                    callback(Result.failure(task.exception ?: Exception("Login failed")))
                }
            }
    }

    fun signUpUser(email: String, password: String, callback: (Result<Unit>) -> Unit) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _email.value = email
                    prefManager.setLoggedInEmail(email)
                    callback(Result.success(Unit))
                } else {
                    callback(Result.failure(task.exception ?: Exception("Unknown error")))
                }
            }
    }

    fun logoutUser() {
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        _email.value = ""
        prefManager.setLoggedInEmail(null)
    }

    fun isLoggedIn(): Boolean {
        return _email.value.isNotEmpty()
    }
}