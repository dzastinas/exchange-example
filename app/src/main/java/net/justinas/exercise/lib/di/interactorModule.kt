package net.justinas.exercise.lib.di

import net.justinas.exercise.lib.intent.ExchangeInteractor
import net.justinas.exercise.lib.intent.GetBalanceInteractor
import net.justinas.exercise.lib.intent.ResetBalanceInteractor
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val interactorModule: Module = module {

    single { ExchangeInteractor(get(), get()) }
    single { GetBalanceInteractor(get()) }
    single { ResetBalanceInteractor(get()) }
}