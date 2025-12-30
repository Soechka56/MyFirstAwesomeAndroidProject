package com.soechka1.myfirstawesomeandroidproject.navscreens.loginpage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import com.soechka1.myfirstawesomeandroidproject.R
import com.soechka1.myfirstawesomeandroidproject.TypeError
import com.soechka1.myfirstawesomeandroidproject.ui.components.CustomTextField
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.Dimens
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.FontSizes

@Composable
fun LoginContent(
    email: String,
    onEmailChange: (String) -> Unit,
    emailError: TypeError?,

    password: String,
    onPasswordChange: (String) -> Unit,
    passwordError: TypeError?,
    isPassword: Boolean,

    onToggleVisibility: (() -> Unit) = { null },

    onLoginClick: () -> Unit,
    onSignClick: () -> Unit,
    modifier: Modifier,
) {
    Scaffold(
        bottomBar = {
            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .imePadding()
                    .fillMaxWidth(),
            ) { Text(text = stringResource(R.string.login_login_in_button))}
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

                Text(text = stringResource(R.string.title),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.extraSmall),
                    fontSize = FontSizes.titleMedium,
                    textAlign = TextAlign.Center,)

                CustomTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    error = emailError,
                    label = stringResource(R.string.login_email_label),
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier,
                )

                CustomTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    error = passwordError,
                    label = stringResource(R.string.login_password_label),
                    keyboardType = KeyboardType.Password,
                    isPassword = isPassword,
                    rightIcon = {
                        IconButton(onClick = onToggleVisibility) {
                            if(!isPassword) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_visibility_24),
                                    contentDescription = null
                                )
                            }
                            else{
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_visibility_off_24),
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    modifier = Modifier)

                Text(
                    text = stringResource(R.string.login_to_sign_screen_link),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.extraSmall)
                        .clickable { onSignClick() },
                    fontSize = FontSizes.caption,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }

}

