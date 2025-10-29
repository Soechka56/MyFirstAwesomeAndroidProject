package com.soechka1.myfirstawesomeandroidproject.navScreens.loginpage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.ui.components.CustomTextField
import com.soechka1.myfirstawesomeandroidproject.ui.theme.Dimens
import com.soechka1.myfirstawesomeandroidproject.ui.theme.FontSizes

@Composable
fun LoginContent(
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: String?,

    password: String,
    onPasswordChange: (String) -> Unit,
    passwordError: String?,
    isPassword: Boolean,

    onToggleVisibility: (() -> Unit) = { null },

    onSignUpClick: () -> Unit,
    modifier: Modifier,
) {
    Scaffold(
        bottomBar = {
            Button(
                onClick = onSignUpClick,
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth(),
            ) { Text(text = stringResource(R.string.login_sign_up_button))}
        },

        modifier = modifier // parent-modifier from presenter
            .background(Color.LightGray),

        ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(text = stringResource(R.string.login_title),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.extraSmall),
                    fontSize = FontSizes.extraLarge,
                    textAlign = TextAlign.Center,)

                CustomTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    errorText = emailError,
                    label = stringResource(R.string.login_email_label),
                    keyboardType = KeyboardType.Email,
                )

                CustomTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    errorText = passwordError,
                    label = stringResource(R.string.login_password_label),
                    keyboardType = KeyboardType.Password,
                    isPassword = isPassword,
                    rightIcon = {
                        IconButton(onClick = onToggleVisibility) {
                            if(!isPassword) {
                                Icon(
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = null
                                )
                            }
                            else{
                                Icon(
                                    imageVector = Icons.Default.VisibilityOff,
                                    contentDescription = null
                                )
                            }
                        }
                    })
            }
        }
    }

}

