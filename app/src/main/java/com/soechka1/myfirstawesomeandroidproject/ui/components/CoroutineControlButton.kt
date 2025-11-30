package com.soechka1.myfirstawesomeandroidproject.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CoroutineControlButton(
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
)
{
    Row(modifier = modifier) {
        Button(
            onClick = onButtonClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text( text = buttonText )
        }
    }
}