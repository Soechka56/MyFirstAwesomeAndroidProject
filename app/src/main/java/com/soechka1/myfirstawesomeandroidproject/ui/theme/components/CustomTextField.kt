package com.soechka1.myfirstawesomeandroidproject.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.soechka1.myfirstawesomeandroidproject.TypeError

import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.FontSizes


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "",
    error: TypeError? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    rightIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    modifier: Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        isError = error != null,
        supportingText = {
            error?.let {
                Text(
                    text = stringResource(it.messageId),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        trailingIcon = rightIcon,
        singleLine = singleLine,
        visualTransformation = if (isPassword)
            PasswordVisualTransformation()
        else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(20.dp),
        textStyle = TextStyle(
            fontSize = FontSizes.bodySmall,
            fontWeight = FontWeight.Medium
        ),
        modifier = modifier,
    )
}
