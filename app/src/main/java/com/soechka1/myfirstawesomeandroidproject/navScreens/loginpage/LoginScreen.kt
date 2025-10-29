package com.soechka1.myfirstawesomeandroidproject.navScreens.loginpage

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.navigation.NotesPageDataObject
import com.soechka1.myfirstawesomeandroidproject.ui.theme.Dimens


@Composable
fun LoginScreen(navController: NavController){
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }

    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var isPassword by remember { mutableStateOf(true) }

    val errorEmailEmpty = stringResource(R.string.error_email_empty)
    val errorEmailInvalid = stringResource(R.string.error_email_invalid)
    val errorPasswordEmpty = stringResource(R.string.error_password_empty)
    val errorPasswordShort = stringResource(R.string.error_password_too_short)

    LoginContent(
        email = email,
        onEmailChange = { email = it; emailError = null },
        emailError = emailError,

        password = password,
        onPasswordChange = { password = it; passwordError = null },
        passwordError = passwordError,
        isPassword = isPassword,

        onSignUpClick = {
            validateAndNavigate(
                email = email,
                password = password,
                errorEmailEmpty = errorEmailEmpty,
                errorEmailInvalid = errorEmailInvalid,
                errorPasswordEmpty = errorPasswordEmpty,
                errorPasswordShort = errorPasswordShort,
                onEmailError = { error -> emailError = error },
                onPasswordError = { error -> passwordError = error },
                onSuccess = {
                    navController.navigate(
                        route = NotesPageDataObject(
                            email = email,
                            password = password,
                        )
                    )
                }
            )
        },

        onToggleVisibility = { isPassword = !isPassword },

        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(Dimens.small),
    )
}

private fun validateAndNavigate(
    email: String,
    password: String,
    errorEmailEmpty: String,
    errorEmailInvalid: String,
    errorPasswordEmpty: String,
    errorPasswordShort: String,
    onEmailError: (String?) -> Unit,
    onPasswordError: (String?) -> Unit,
    onSuccess: () -> Unit
) {
    val emailPattern = android.util.Patterns.EMAIL_ADDRESS

    val emailError = when {
        email.isBlank() -> errorEmailEmpty
        !emailPattern.matcher(email).matches() -> errorEmailInvalid
        else -> null
    }

    val passwordError = when {
        password.isBlank() -> errorPasswordEmpty
        password.length < 8 -> errorPasswordShort
        else -> null
    }

    onEmailError(emailError)
    onPasswordError(passwordError)

    if (emailError == null && passwordError == null) {
        onSuccess()
    }
}

