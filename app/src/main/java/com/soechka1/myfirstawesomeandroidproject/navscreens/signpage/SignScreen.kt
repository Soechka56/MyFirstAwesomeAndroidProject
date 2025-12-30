package com.soechka1.myfirstawesomeandroidproject.navscreens.signpage

import android.util.Patterns
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.SnackbarHostState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController

import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.soechka1.myfirstawesomeandroidproject.TypeError
import com.soechka1.myfirstawesomeandroidproject.di.ServiceLocator
import com.soechka1.myfirstawesomeandroidproject.model.UserSignDataModel
import com.soechka1.myfirstawesomeandroidproject.navigation.LoginPageObject

import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.Dimens
import kotlinx.coroutines.launch


@Composable
fun SignScreen(navController: NavController, onSuccess: (UserSignDataModel) -> Unit, modifier: Modifier){
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf<TypeError?>(null) }

    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<TypeError?>(null) }

    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }

    var passwordError by remember { mutableStateOf<TypeError?>(null) }
    var isPassword by remember { mutableStateOf(true) }

    SignContent(
        username = username,
        onUsernameChange = { username = it; usernameError = null },
        usernameError = usernameError,

        email = email,
        onEmailChange = { email = it; emailError = null },
        emailError = emailError,

        password = password,
        passwordConfirm = passwordConfirm,
        onPasswordChange = { password = it; passwordError = null },
        onPasswordChangeConfirm = { passwordConfirm = it; passwordError = null},
        passwordError = passwordError,
        isPassword = isPassword,

        onSignClick = {

            scope.launch{
                validateAndNavigate(
                    username = username,
                    email = email,
                    password = password,
                    passwordConfirm = passwordConfirm,
                    onEmailError = { error ->
                        if(error == TypeError.ERROR_EMAIL_EMPTY) { }
                        else { emailError = error } },
                    onPasswordError = { error -> passwordError = error },
                    onUsernameError = { error -> usernameError = error },

                    onSuccess = {
                        onSuccess(
                            UserSignDataModel(
                                username = username,
                                email = email,
                                password = password
                            )
                        )
                    }
                )

            }
        },

        onLoginClick = { navController.navigate(route = LoginPageObject) },

        onToggleVisibility = { isPassword = !isPassword },
        snackbarHostState = snackbarHostState,

        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(Dimens.small),

    )
}

private suspend fun validateAndNavigate(
    username: String,
    email: String,
    password: String,
    passwordConfirm: String,

    onEmailError: (TypeError?) -> Unit,
    onPasswordError: (TypeError?) -> Unit,
    onUsernameError: (TypeError?) -> Unit,

    onSuccess: () -> Unit
) {
    val emailPattern = Patterns.EMAIL_ADDRESS

    val usernameError = if (username.length < 3) { TypeError.ERROR_USERNAME_SHORT } else null

    val emailError = when {

        email.isBlank() -> TypeError.ERROR_EMAIL_EMPTY

        !emailPattern.matcher(email).matches() -> TypeError.ERROR_EMAIL_INVALID

        ServiceLocator.getUserRepo().isEmailExists(email) -> TypeError.ERROR_EMAIL_EXIST

        else -> null
    }

    val passwordError = when {

        password.isBlank() -> TypeError.ERROR_PASSWORD_EMPTY

        password.length < 8 -> TypeError.ERROR_PASSWORD_SHORT

        password != passwordConfirm -> TypeError.ERROR_PASSWORDS_NOT_MATCH

        else -> null
    }

    onEmailError(emailError)
    onPasswordError(passwordError)
    onUsernameError(usernameError)


    if (usernameError == null && emailError == null && passwordError == null) {
        onSuccess()
    }
}

