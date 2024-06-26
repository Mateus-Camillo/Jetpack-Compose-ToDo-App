package com.example.jetpackcomposetodoapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpackcomposetodoapp.R
import com.example.jetpackcomposetodoapp.ui.theme.JetpackComposeToDoAppTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Register()
        }
    }
}

@Composable
fun Register(viewModel: RegisterViewModel = hiltViewModel()) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isRegistrationSuccessful by viewModel.isRegistrationSuccessful.observeAsState(false)

    var startLoginActivity: Boolean by remember { mutableStateOf(false) }

    val areRegisterFieldsValid: Boolean = viewModel.areRegisterFieldsValid(email, password)

    val areFieldsEmpty: Boolean = viewModel.areFieldsEmpty(fields = arrayOf(email, password))

    var isPasswordVisible by remember { mutableStateOf(false) }

    val toastContext = LocalContext.current
    val toastMessage by viewModel.toastMessage.observeAsState("")

    val context = LocalContext.current

    LaunchedEffect(toastMessage) {
        if (toastMessage.isNotEmpty()) {
            Toast.makeText(toastContext, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(isRegistrationSuccessful || startLoginActivity) {
        if (isRegistrationSuccessful || startLoginActivity) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            if (context is RegisterActivity) {
                context.finish()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),

            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (isPasswordVisible)
                    painterResource(R.drawable.ic_visibility)
                else
                    painterResource(R.drawable.ic_visibility_off)

                IconButton(onClick = {isPasswordVisible = !isPasswordVisible}) {
                    Icon(painter = image, contentDescription = "Toggle password visibility")
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.registerUser(email, password)
            },
            enabled = areFieldsEmpty && areRegisterFieldsValid,

            modifier = Modifier.fillMaxWidth()
        ) { Text("Register") }

        ClickableText(text = buildAnnotatedString {
            append("Already have an account? ")

            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("Login")
            }
        },
            onClick = {
                Log.d("RegisterActivity", "startLoginActivity")
                startLoginActivity = true
            },

            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    } //TODO: Add text with password requirements and error message
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    JetpackComposeToDoAppTheme {
        Register()
    }
}