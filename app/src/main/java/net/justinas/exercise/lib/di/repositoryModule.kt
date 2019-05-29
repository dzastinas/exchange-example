package net.justinas.exercise.lib.di

import net.justinas.exercise.lib.domain.BalanceRepository
import net.justinas.exercise.lib.domain.ExchangeRepository
import net.justinas.exercise.lib.domain.ExchangeRepositoryRemote
import net.justinas.exercise.lib.domain.UserRepositoryLocal
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val repositoryModule: Module = module {

    single { ExchangeRepositoryRemote(get()) as ExchangeRepository }
    single { UserRepositoryLocal() as BalanceRepository }
}