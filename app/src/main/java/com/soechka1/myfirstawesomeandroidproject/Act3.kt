package com.soechka1.myfirstawesomeandroidproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soechka1.myfirstawesomeandroidproject.core.Constants
import com.soechka1.myfirstawesomeandroidproject.ui.theme.MyFirstAwesomeAndroidProjectTheme

class Act3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstAwesomeAndroidProjectTheme {

                val text: String? = intent.getStringExtra(Constants.EXTRA_MESSAGE)
                Screen3(text)
            }
        }
    }
}

@Composable
fun Screen3(text: String?){
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = when {
                text.isNullOrBlank() -> stringResource(R.string.screen3_default)
                else -> text
            },
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row() {
            Button(
                modifier = Modifier.padding(4.dp),
                onClick = {
                    val intent: Intent = Intent(context, Act1::class.java)
                        .putExtra(Constants.EXTRA_MESSAGE, text)

                    // <<В стеке должен быть новый экземпляра активити с первым экраном>>
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)

                    context.startActivity(intent)
                }
            ) {
                Text(text = stringResource(R.string.btn_to_screen1_new))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Screen3() {
    MyFirstAwesomeAndroidProjectTheme {
        Screen3("")
    }
}