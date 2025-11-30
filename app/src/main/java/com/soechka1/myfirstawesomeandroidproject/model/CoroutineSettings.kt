package com.soechka1.myfirstawesomeandroidproject.model

import androidx.annotation.StringRes
import com.soechka1.myfirstawesomeandroidproject.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers



enum class SettingSwitch(
    @StringRes val labelRes: Int
) {
    PARALLEL(R.string.run_parallel),
    SEQUENTAL(R.string.run_not_parallel),
    LAZY(R.string.run_lazy),
    BACKGROUND(R.string.run_background);
}

data class CoroutineSettings(
    var countOfCoroutine: Int = 10,
    var isParallel: Boolean = false,
    var isLazyStart: Boolean = false,
    var isBackground: Boolean = false,
    var selectedDispatcher: DispatcherType = DispatcherType.DEFAULT
)

enum class DispatcherType(
    @StringRes val labelRes: Int
) {
    DEFAULT(R.string.dispatcher_default),
    IO(R.string.dispatcher_io),
    MAIN(R.string.dispatcher_main);

    fun toDispatcher(): CoroutineDispatcher = when (this) {
        DEFAULT -> Dispatchers.Default
        IO -> Dispatchers.IO
        MAIN -> Dispatchers.Main
    }
}