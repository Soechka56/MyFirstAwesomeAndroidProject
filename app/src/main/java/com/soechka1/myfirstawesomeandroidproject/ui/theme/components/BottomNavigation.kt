package com.soechka1.myfirstawesomeandroidproject.ui.theme.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.soechka1.myfirstawesomeandroidproject.navigation.AppDestination

@Composable
fun BottomNavigation(
    modifier: Modifier,
    onNavigate: (AppDestination) -> Unit,
    selectedDestination: AppDestination) {
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets, modifier = modifier){

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
