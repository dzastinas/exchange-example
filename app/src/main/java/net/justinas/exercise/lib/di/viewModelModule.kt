package net.justinas.exercise.lib.di

import net.justinas.exercise.lib.view.ExchangeViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val viewModelModule: Module = module {

    viewModel { ExchangeViewModel(get(), get(), get()) }
}