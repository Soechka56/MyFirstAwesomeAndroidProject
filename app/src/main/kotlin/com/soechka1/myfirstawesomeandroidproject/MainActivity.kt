package com.soechka1.myfirstawesomeandroidproject

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.analytics.api.Analytics
import com.example.api.BattleLogDetailsNavigationKey
import com.example.api.SearchNavigationKey
import com.example.buildconfig.api.BuildConfigProvider
import com.example.impl.GetBattleLogScreen
import com.example.impl.SearchScreen
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.soechka1.designsystem.theme.MyFirstAwesomeAndroidProjectTheme
import com.soechka1.myfirstawesomeandroidproject.core.navigation.Navigator
import org.koin.android.ext.android.inject
import java.util.UUID

class MainActivity : ComponentActivity() {

    private val buildConfigProvider: BuildConfigProvider by inject()
    private val analytics: Analytics by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { }

            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }


        val sessionUserId = UUID.randomUUID().toString()
        FirebaseCrashlytics.getInstance().setCustomKey("userId", sessionUserId)
        FirebaseCrashlytics.getInstance().setUserId(sessionUserId)

        analytics.trackScreenView(
            screenName = "SearchScreen",
            screenClass = "MainActivity",
        )

        setContent {
            MyFirstAwesomeAndroidProjectTheme {
                val navigator = remember { Navigator(SearchNavigationKey) }

                Scaffold { inner ->
                    NavDisplay(
                        backStack = navigator.backStack,
                        onBack = remember { { navigator.goBack() } },

                        transitionSpec = {
                            EnterTransition.None togetherWith ExitTransition.None
                        },

                        popTransitionSpec = {
                            EnterTransition.None togetherWith ExitTransition.None
                        },

                        entryProvider = entryProvider {
                            entry<SearchNavigationKey> {
                                SearchScreen(
                                    onBattleClick = remember { { battleId ->
                                        analytics.trackScreenView(
                                            screenName = "GetBattleLogScreen",
                                            screenClass = "MainActivity",
                                        )
                                        navigator.goTo(BattleLogDetailsNavigationKey(battleId))
                                    } },
                                    modifier = Modifier.fillMaxSize(),
                                )
                            }
                            entry<BattleLogDetailsNavigationKey> { key ->
                                GetBattleLogScreen(
                                    battleId = key.id,
                                    cdnBaseUrl = buildConfigProvider.getBrawlerCdnBaseUrl(),
                                    onBackClick = remember { { analytics.trackScreenView(
                                            screenName = "SearchScreen",
                                            screenClass = "MainActivity",
                                        )
                                        navigator.goBack() } },
                                    modifier = Modifier.fillMaxSize(),
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(inner),
                    )
                }
            }
        }
    }
}
