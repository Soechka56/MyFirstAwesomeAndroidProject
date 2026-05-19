package com.example.impl.di

import com.example.impl.GetBattleLogViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val getBattleLogModule = module {
    viewModel { (battleId: Long) -> GetBattleLogViewModel(battleId, get()) }
}
