package com.soechka1.myfirstawesomeandroidproject.navscreens.signpage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun SignContent(
    username: String,
    onUsernameChange: (String) -> Unit,
    usernameError: TypeError?,

    email: String,
    onEmailChange: (String) -> Unit,
    emailError: TypeError?,

    password: String,
    passwordConfirm: String,
    onPasswordChange: (String) -> Unit,
    onPasswordChangeConfirm: (String) -> Unit,
    passwordError: TypeError?,
    isPassword: Boolean,

    onToggleVisibility: (() -> Unit) = { null },

    onSignClick: () -> Unit,
    onLoginClick: () -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },

        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onSignClick,
                    modifier = Modifier
                        .height(Dimens.buttonHeight)
                        .width(Dimens.buttonWidth)
                ) {
                    Text(stringResource(R.string.sign_sign_up_button))
                }
            }
        },
        modifier = modifier.background(Color.LightGray)
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
                    fontSize = FontSizes.headline,
                    textAlign = TextAlign.Center,)


                CustomTextField(
                    value = username,
                    onValueChange = onUsernameChange,
                    error = usernameError,
                    label = stringResource(R.string.sign_username_title),
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier
                )

                CustomTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    error = emailError,
                    label = stringResource(R.string.login_email_label),
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier
                )

                CustomTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    error = if(passwordError != TypeError.ERROR_PASSWORDS_NOT_MATCH){ passwordError } else { null },
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

                CustomTextField(
                    value = passwordConfirm,
                    onValueChange = onPasswordChangeConfirm,
                    error = if(passwordError == TypeError.ERROR_PASSWORDS_NOT_MATCH){ passwordError } else { null },
                    label = stringResource(R.string.sign_password_re_label),
                    keyboardType = KeyboardType.Password,
                    isPassword = true,
                    modifier = Modifier
                )

                Text(
                    text = stringResource(R.string.sign_to_login_screen_link),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.extraSmall)
                        .clickable { onLoginClick() },
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
