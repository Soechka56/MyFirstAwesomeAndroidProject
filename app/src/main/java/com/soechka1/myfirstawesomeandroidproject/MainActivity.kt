package com.soechka1.myfirstawesomeandroidproject

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.soechka1.myfirstawesomeandroidproject.model.ReplyReceiver
import com.soechka1.myfirstawesomeandroidproject.navScreens.addnotif.AddNotifScreen
import com.soechka1.myfirstawesomeandroidproject.navScreens.editnotif.EditNotifScreen
import com.soechka1.myfirstawesomeandroidproject.navScreens.usermsg.UserMsgScreen
import com.soechka1.myfirstawesomeandroidproject.navigation.AppDestination
import com.soechka1.myfirstawesomeandroidproject.ui.theme.Dimens
import com.soechka1.myfirstawesomeandroidproject.ui.theme.MyFirstAwesomeAndroidProjectTheme
import com.soechka1.myfirstawesomeandroidproject.utils.NotificationHandler
import com.soechka1.myfirstawesomeandroidproject.utils.PermissionHandler
import com.soechka1.myfirstawesomeandroidproject.utils.ResManager

class MainActivity : ComponentActivity() {

    private var permissionsHandler: PermissionHandler? = null
    private var notificationHandler: NotificationHandler? = null
    private var replyReceiver: ReplyReceiver? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        permissionsHandler = PermissionHandler(onPermissionGranted = {}, onPermissionDenied = {}, activity = this)
        val resManager = ResManager(ctx = applicationContext)

        if (notificationHandler == null) {
            notificationHandler = NotificationHandler(ctx = applicationContext, resManager = resManager)

            notificationHandler?.initNotifChannel()
        }
        if (replyReceiver == null) {
            replyReceiver = ReplyReceiver()
            val intentFilter = android.content.IntentFilter(Keys.REPLY_ACTION)
            registerReceiver(replyReceiver, intentFilter, android.content.Context.RECEIVER_NOT_EXPORTED)
        }



        // get notif permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(
                this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
                ){
                permissionsHandler?.requestMultiplePermissions(
                    permission = listOf(Manifest.permission.POST_NOTIFICATIONS)
                )
            }
        }


        setContent {
            MyFirstAwesomeAndroidProjectTheme {
                val navController = rememberNavController()

                val startDestination = AppDestination.AddNotif
                var selectedDestination by rememberSaveable { mutableStateOf(startDestination) }

                Scaffold(
                    bottomBar = {
                        BottomNavigation(
                            modifier = Modifier,
                            onNavigate = { destination ->
                                destination.also { selectedDestination = it }
                                navController.navigate(route = destination.name)
                            },
                            selectedDestination = selectedDestination
                        )
                    }
                )
                { innerPadding ->
                    NavHost(navController = navController,
                        startDestination = startDestination.name){

                        composable(route = AppDestination.AddNotif.name) {
                            AddNotifScreen(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                                    .padding(Dimens.small),
                                onShowButtonClicked = { titleAndContentPair, messageSettings ->
                                    notificationHandler?.showNotification(
                                        titleAndContentPair,
                                        messageSettings = messageSettings
                                    )
                                }
                            )
                        }


                        composable(route = AppDestination.EditNotif.name) {
                            EditNotifScreen(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding)
                                    .padding(Dimens.small),
                                onUpdateClicked = { id, newText ->
                                    notificationHandler?.updateNotification(id, newText)
                                },
                                onClearAllClicked = {
                                    notificationHandler?.clearAllNotifications()
                                }
                            )
                        }

                        composable(route = AppDestination.UserMsg.name) {
                            UserMsgScreen(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                            )
                        }
                    }
                }
            }

        }
    }

}

@Composable
fun BottomNavigation(
    modifier: Modifier,
    onNavigate: (AppDestination) -> Unit,
    selectedDestination: AppDestination) {
        NavigationBar(windowInsets = NavigationBarDefaults.windowInsets,
            modifier = modifier){
        AppDestination.entries.forEach { destination ->
            NavigationBarItem(
                selected = selectedDestination == destination,
                onClick = { onNavigate(destination) },
                icon = {
                    Icon(painterResource(destination.icon),
                    contentDescription = null)
                       },
                label = { Text(text = stringResource(destination.label)) })
            }
        }
    }

