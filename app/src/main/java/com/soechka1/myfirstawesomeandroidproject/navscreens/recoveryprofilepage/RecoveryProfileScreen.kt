package com.soechka1.myfirstawesomeandroidproject.navscreens.recoveryprofilepage

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.soechka1.myfirstawesomeandroidproject.di.ServiceLocator
import com.soechka1.myfirstawesomeandroidproject.navigation.SignPageObject
import com.soechka1.myfirstawesomeandroidproject.ui.theme.sizes.Dimens
import kotlinx.coroutines.launch

@Composable
fun RecoveryProfileScreen(
    navController: NavController,
    onSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    var isRestoring by remember { mutableStateOf(false) }
    var userId by remember { mutableStateOf<Long?>(null) }
    var daysSince by remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(Unit) {
        scope.launch {
            val currentUserId = ServiceLocator.getUserRepo().getUserByToken()
            userId = currentUserId

            if (currentUserId != null) {
                daysSince = ServiceLocator.getUserRepo().getDaysSinceDeletion(currentUserId)
            }
        }
    }

    RecoveryProfileContent(
        isRestoring = isRestoring,
        daysSince = daysSince,
        onRestoreClick = {
            scope.launch {
                val currentUserId = userId
                isRestoring = true

                if (currentUserId != null) {
                    ServiceLocator.getUserRepo().restoreUser(currentUserId)
                }

                isRestoring = false
                onSuccess()
            }
        },
        onHardDeleteClick = {
            scope.launch {
                val currentUserId = userId

                if (currentUserId != null) {
                    ServiceLocator.getUserRepo().hardDeleteUser(currentUserId)
                    navController.navigate(SignPageObject) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }
        },
        onCancelClick = {
            navController.navigate(SignPageObject) {
                popUpTo(0) { inclusive = true }
            }
        },
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(Dimens.small)
    )
}
