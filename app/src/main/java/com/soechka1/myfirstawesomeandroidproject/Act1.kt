package com.soechka1.myfirstawesomeandroidproject

import android.content.Intent
import android.os.Bundle
import androidx.compose.material3.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soechka1.myfirstawesomeandroidproject.core.Constants
import com.soechka1.myfirstawesomeandroidproject.ui.theme.MyFirstAwesomeAndroidProjectTheme

class  Act1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstAwesomeAndroidProjectTheme {
                Screen1()
            }
        }
    }
}



@Composable
fun Screen1() {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(text=stringResource(R.string.enter_text)) },
            modifier = Modifier.padding(8.dp)
        )

        Row {
            Button(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    val intent1 = Intent(context, Act2::class.java)
                    intent1.putExtra(Constants.EXTRA_MESSAGE, text)
                    context.startActivity(intent1)
                }
            ) {
                Text(text = stringResource(R.string.btn_to_screen2))
            }

            Button(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    val intent2 = Intent(context, Act3::class.java)
                    intent2.putExtra(Constants.EXTRA_MESSAGE, text)
                    context.startActivity(intent2)
                }
            ) {
                Text(text = stringResource(R.string.btn_to_screen3))
            }
        }

    }
}

@Preview(name = "Pixel 7", showSystemUi = false, showBackground = true)
@Composable
fun PreviewAct1() {
    MyFirstAwesomeAndroidProjectTheme {
        Screen1()
    }
}