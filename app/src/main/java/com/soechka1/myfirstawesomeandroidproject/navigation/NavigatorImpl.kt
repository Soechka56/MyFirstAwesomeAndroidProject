package com.soechka1.myfirstawesomeandroidproject.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
import com.soechka1.myfirstawesomeandroidproject.core.navigation.Navigator
import com.example.api.SearchNavigationKey

class NavigatorImpl : Navigator {

    private val backStack = mutableStateListOf<Any>()

    init {
        backStack.add(SearchNavigationKey)
    }

    override fun navigate(route: NavKey) {
        backStack.add(route)
    }

    override fun popEntry() {
        if (backStack.size > 1) {
            backStack.removeLastOrNull()
        }
    }

    fun getBackStack(): SnapshotStateList<Any> = backStack
}
