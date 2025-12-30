package com.soechka1.myfirstawesomeandroidproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

import com.soechka1.myfirstawesomeandroidproject.di.ServiceLocator
import com.soechka1.myfirstawesomeandroidproject.model.UserDataModel
import com.soechka1.myfirstawesomeandroidproject.navscreens.signpage.SignScreen
import com.soechka1.myfirstawesomeandroidproject.navigation.AppDestination
import com.soechka1.myfirstawesomeandroidproject.navigation.ItemPageObject
import com.soechka1.myfirstawesomeandroidproject.navigation.LoginPageObject
import com.soechka1.myfirstawesomeandroidproject.navigation.ProfilePageObject
import com.soechka1.myfirstawesomeandroidproject.navigation.RecoveryPageObject
import com.soechka1.myfirstawesomeandroidproject.navigation.SignPageObject
import com.soechka1.myfirstawesomeandroidproject.navscreens.contentpage.ContentScreen
import com.soechka1.myfirstawesomeandroidproject.navscreens.favoritepage.FavoriteScreen
import com.soechka1.myfirstawesomeandroidproject.navscreens.itempage.ItemScreen
import com.soechka1.myfirstawesomeandroidproject.navscreens.loginpage.LoginScreen
import com.soechka1.myfirstawesomeandroidproject.navscreens.newitempage.NewItemScreen
import com.soechka1.myfirstawesomeandroidproject.navscreens.profilepage.ProfileScreen
import com.soechka1.myfirstawesomeandroidproject.navscreens.recoveryprofilepage.RecoveryProfileScreen
import com.soechka1.myfirstawesomeandroidproject.repo.UserDataRepository
import com.soechka1.myfirstawesomeandroidproject.ui.theme.MyFirstAwesomeAndroidProjectTheme
import com.soechka1.myfirstawesomeandroidproject.ui.theme.components.BottomNavigation

import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MyFirstAwesomeAndroidProjectTheme {

                var userId by remember { mutableStateOf<Long?>(null) }
                var userDataModel by remember { mutableStateOf<UserDataModel?>(null) }
                var allowDeletion by remember { mutableStateOf<Boolean>(false) }


                // init session
                LaunchedEffect(Unit) {
                    userId = ServiceLocator.getUserRepo().getUserByToken()
                }

                val navController = rememberNavController()
                val scope = rememberCoroutineScope()

                val startDestination = if (userId == null) {
                    SignPageObject
                } else {
                    ProfilePageObject
                }

                var selectedDestination by rememberSaveable { mutableStateOf(AppDestination.ProfileObject) }

                LaunchedEffect(userId) {
                    userId?.let { id ->
                        userDataModel = ServiceLocator.getUserRepo().findUserDataById(id)

                        if(!allowDeletion) {
                            navController.navigate(ProfilePageObject) {
                                popUpTo(0) { inclusive = true }

                            }
                        }
                    }
                }

                NavHost(
                    navController = navController,
                        startDestination = startDestination
                ) {

                    composable<LoginPageObject> {
                        LoginScreen(
                            navController = navController,
                            onSuccess = { user ->
                                user?.let {
                                    scope.launch {
                                        val isDeleted = ServiceLocator.getUserRepo().isUserDeleted(user.userId)
                                        if (isDeleted) {
                                            val daysSince = ServiceLocator.getUserRepo().getDaysSinceDeletion(user.userId)

                                            println("TEST TAG - DAYS AFTER DELETiON: $daysSince")
                                            if (daysSince != null && daysSince < 7) {
                                                userDataModel = user
                                                userId = user.userId
                                                allowDeletion = true
                                                ServiceLocator.getUserRepo().updateToken(user.userId)
                                                navController.navigate(RecoveryPageObject) {
                                                    popUpTo(0) { inclusive = true }
                                                }
                                            } else if (daysSince != null && daysSince >= 7) {
                                                ServiceLocator.getUserRepo().hardDeleteUser(user.userId)
                                            } else {
                                                userDataModel = user
                                                userId = user.userId
                                                ServiceLocator.getUserRepo().updateToken(user.userId)
                                            }
                                        } else {
                                            userDataModel = user
                                            userId = user.userId
                                            ServiceLocator.getUserRepo().updateToken(user.userId)
                                        }
                                    }
                                }
                            }
                        )
                    }

                    composable<SignPageObject> {
                        SignScreen(
                            navController = navController,
                            modifier = Modifier,
                            onSuccess = { user ->
                                scope.launch {
                                    val newUserId = ServiceLocator.getUserRepo().createNewUser(user)
                                    if (!(newUserId == null || newUserId == Keys.CURRENT_USER_EXIST)) { 
                                        userId = newUserId 
                                    }
                                }
                            }
                        )
                    }

                    composable<ProfilePageObject> {
                        Scaffold(
                            modifier = Modifier,
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
                        ) { innerPadding ->
                            userDataModel?.let{ user ->
                                ProfileScreen(
                                    user = user,
                                    onLogoutClick = {
                                        UserDataRepository.clear()
                                        userId = null; userDataModel = null
                                        navController.navigate(route = SignPageObject) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    },
                                    onDeleteAccountClick = {
                                        scope.launch {
                                            ServiceLocator.getUserRepo().markUserAsDeleted(user.userId)
                                            UserDataRepository.clear()
                                            userId = null; userDataModel = null
                                            navController.navigate(route = SignPageObject) {
                                                popUpTo(0) { inclusive = true }
                                            }
                                        }
                                    },
                                    modifier = Modifier.padding(innerPadding),
                                )
                            }
                        }
                    }

                    composable(route = AppDestination.ContentPageObject.name) {
                        Scaffold(
                            modifier = Modifier,
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
                        ) { innerPadding ->
                            ContentScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }

                    composable(route = AppDestination.FavoritePageObject.name) {
                        Scaffold(
                            modifier = Modifier,
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
                        ) { innerPadding ->
                            FavoriteScreen(
                                navController = navController,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }

                    composable(route = AppDestination.NewItemPageObject.name) {
                        Scaffold(
                            modifier = Modifier,
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
                        ) { innerPadding ->
                            NewItemScreen(
                                navController = navController,
                                onSuccess = {
                                },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }

                    composable<ItemPageObject> { backStackEntry ->
                        val itemPageObject = backStackEntry.toRoute<ItemPageObject>()
                        ItemScreen(
                            itemId = itemPageObject.itemId,
                            navController = navController,
                            modifier = Modifier
                        )
                    }

                    composable<RecoveryPageObject> {
                        userDataModel?.let { user ->
                            RecoveryProfileScreen(
                                navController = navController,
                                onSuccess = {
                                    scope.launch {
                                        ServiceLocator.getUserRepo().restoreUser(user.userId)
                                        ServiceLocator.getUserRepo().updateToken(user.userId)
                                        navController.navigate(ProfilePageObject) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    }
                                },
                                modifier = Modifier
                            )
                        }
                    }

                }
            }
        }
    }
}
