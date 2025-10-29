package com.soechka1.myfirstawesomeandroidproject.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

import com.soechka1.myfirstawesomeandroidproject.ui.theme.FontSizes


@Composable
fun CustomTextField (
    value: String,
    onValueChange: (String) -> Unit,
    errorText: String? = null,
    label: String = "",
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    rightIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    modifier: Modifier = Modifier,

    ) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        isError = !errorText.isNullOrEmpty(),
        supportingText = {
            if (!errorText.isNullOrEmpty()) {
                Text(text = errorText, color = MaterialTheme.colorScheme.error)
            }
        }
        ,
        trailingIcon = rightIcon,
        singleLine = singleLine,
        // change text to * if its pass
        visualTransformation = if (isPassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),

        // unified interface and my attempt to justify a custom text field..
        shape = RoundedCornerShape(20.dp),
        textStyle = TextStyle(
            fontSize = FontSizes.medium,
            fontWeight = FontWeight.Medium
        )
    )
}
