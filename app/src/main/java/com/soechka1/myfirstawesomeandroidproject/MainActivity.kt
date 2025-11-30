package com.soechka1.myfirstawesomeandroidproject

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.lifecycleScope
import com.soechka1.myfirstawesomeandroidproject.model.CoroutineSettings
import com.soechka1.myfirstawesomeandroidproject.ui.screens.CoroutineStartScreen
import com.soechka1.myfirstawesomeandroidproject.ui.theme.MyFirstAwesomeAndroidProjectTheme
import com.soechka1.myfirstawesomeandroidproject.utils.CoroutineError
import com.soechka1.myfirstawesomeandroidproject.utils.CoroutineExecutor
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstAwesomeAndroidProjectTheme {

                val executor = remember { CoroutineExecutor() }
                var settings: CoroutineSettings by remember { mutableStateOf(CoroutineSettings()) }

                // stats of process
                val completedCount by executor.totalCount.collectAsState()
                var canceledCount by remember { mutableIntStateOf(0) }

                val programStarted by executor.isRun.collectAsState()
                val errorType by executor.errorType.collectAsState()

                val scope = rememberCoroutineScope()
                val snackbarHostState = remember { SnackbarHostState() }


                LifecycleEventEffect(Lifecycle.Event.ON_PAUSE) {
                    // cancel all coroutine if background work is not needed
                    if(!settings.isBackground) {
                        canceledCount = executor.cancelAllCoroutines()
                    }
                }

                LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
                    if(!settings.isBackground && canceledCount > 0){
                        lifecycleScope.launch {
                            executor.continueWork(this,
                                settings.copy(countOfCoroutine = canceledCount)
                            )
                            canceledCount = 0
                        }
                    }
                }

                Scaffold(modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) { innerPadding ->

                    val cancelledMessage = stringResource(R.string.canceled_coroutines)

                    CoroutineStartScreen(
                        modifier = Modifier.padding(innerPadding),
                        settings = settings,
                        onSettingsChange = { settings = it },
                        completedCount = completedCount,
                        programStarted = programStarted,
                        onExecutorStart = {
                            scope.launch {
                                executor.startBatchOfCoroutines(this, settings)
                            }
                        },
                        onExecutorCancel = {
                            val count = executor.cancelAllCoroutines()
                            // count of canceled coroutines
                            Toast.makeText(this, "$cancelledMessage $count", Toast.LENGTH_SHORT).show()
                        }
                    )


                    val errorTokenMessage = stringResource(R.string.exception_token)
                    val errorHashMessage = stringResource(R.string.exception_hash)

                    // show error messages =)
                    LaunchedEffect(errorType) {
                        when(errorType) {
                            CoroutineError.ERROR_TOAST -> { Toast.makeText(this@MainActivity, errorHashMessage, Toast.LENGTH_SHORT).show() }
                            CoroutineError.ERROR_SNACKBAR -> { snackbarHostState.showSnackbar(errorTokenMessage) }
                            CoroutineError.ERROR_RESET_SETTINGS -> { settings = CoroutineSettings() }
                        }
                    }


                }

            }

        }
    }
}