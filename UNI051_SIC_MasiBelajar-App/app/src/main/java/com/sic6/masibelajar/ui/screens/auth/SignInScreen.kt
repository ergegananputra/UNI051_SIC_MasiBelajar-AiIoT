package com.sic6.masibelajar.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sic6.masibelajar.R
import com.sic6.masibelajar.ui.navigation.graphs.AuthGraph
import com.sic6.masibelajar.ui.navigation.graphs.RootGraph
import com.sic6.masibelajar.ui.theme.MasiBelajarDashboardTheme

@Preview(
    name = "Light Mode",
    showSystemUi = true,
    showBackground = true,
)
@Composable
private fun SignInScreenDeveloperPreview() {
    MasiBelajarDashboardTheme {
        val navController = rememberNavController()
        SignInScreen(navController)
    }
}

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: UserViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(60.dp))
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_lokari_3),
                    contentDescription = null,
                    modifier = Modifier
                        .size(140.dp)
                        .align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Welcome text
            Text(
                text = "Welcome Back!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Form Sign In
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Sign In",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Input Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email address") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email Icon") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Input Password
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password Icon") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it })
                    Text(text = "Remember me", fontSize = 14.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = { /* Forgot password logic */ }) {
                        Text(
                            text = "Forgot Password?",
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.signInUser(email, password) { result ->
                            result.onSuccess {
                                navController.navigate(RootGraph.Main) {
                                    popUpTo(RootGraph.Auth) {
                                        inclusive = true
                                    }
                                }
                            }.onFailure { exception ->
                                println("Login failed: ${exception.message}")
                                Toast.makeText(context, exception.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Log In")
                }

                // Sign Up
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Don’t have an account?")
                    TextButton(onClick = { navController.navigate(AuthGraph.SignUp) }) {
                        Text("Sign Up")
                    }
                }
            }
        }

        Image(
            painter = painterResource(R.drawable.foreground),
            contentDescription = null,
            modifier = Modifier
                .aspectRatio(2f / 1f)
                .fillMaxSize()
                .align(Alignment.BottomCenter)
                .offset(y = 16.dp)
                .zIndex(-1f)
        )
    }
}

