package com.soechka1.myfirstawesomeandroidproject.navscreens.loginpage

import android.util.Patterns
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

import com.soechka1.myfirstawesomeandroidproject.TypeError
import com.soechka1.myfirstawesomeandroidproject.di.ServiceLocator
import com.soechka1.myfirstawesomeandroidproject.model.UserDataModel
import com.soechka1.myfirstawesomeandroidproject.navigation.SignPageObject
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.Dimens
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    navController: NavController,
    onSuccess: (UserDataModel?) -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<TypeError?>(null) }

    var password by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf<TypeError?>(null) }

    var isPassword by remember { mutableStateOf(true) }

    LoginContent(
        email = email,
        onEmailChange = { email = it; emailError = null },
        emailError = emailError,

        password = password,
        onPasswordChange = { password = it; passwordError = null },
        passwordError = passwordError,
        isPassword = isPassword,

        onToggleVisibility = { isPassword = !isPassword },

        onLoginClick = {
            scope.launch{
                validateAndLogin(
                    email = email,
                    password = password,
                    onEmailError = { emailError = it },
                    onPasswordError = { passwordError = it },
                    onSuccess = { user ->
                        onSuccess(user)
                    }
                )
            }
        },

        onSignClick = {
            navController.navigate(SignPageObject)
        },

        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(Dimens.small)
    )
}


private suspend fun validateAndLogin(
    email: String,
    password: String,
    onEmailError: (TypeError?) -> Unit,
    onPasswordError: (TypeError?) -> Unit,
    onSuccess: (UserDataModel?) -> Unit
) {

    var passwordError: TypeError? = null
    val emailPattern = Patterns.EMAIL_ADDRESS

    // сначала емайл ли это или что-то левое
    var emailError = when {
        email.isBlank() -> TypeError.ERROR_EMAIL_EMPTY
        !emailPattern.matcher(email).matches() -> TypeError.ERROR_EMAIL_INVALID
        else -> null
    }

    // потом проверка на наличие такого пользователя
    if(emailError == null) {
        val hashPassword = ServiceLocator.getUserRepo().getUserHashByEmail(email)

        // а уже потом проверка на корректность пароля из хеша полученного выше
        if(hashPassword == null) { emailError = TypeError.ERROR_EMAIL_NOT_EXIST } else {
            passwordError =
                if (ServiceLocator.getUserRepo().checkPassword(password, hashPassword))
                    null
                else TypeError.ERROR_PASSWORD_INCORRECT

            }
        }

    onEmailError(emailError)
    onPasswordError(passwordError)

    if (emailError == null && passwordError == null) {
        onSuccess(ServiceLocator.getUserRepo().findUserDataByEmail(email))
    }
}
