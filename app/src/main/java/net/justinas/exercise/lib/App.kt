package net.justinas.exercise.lib

import android.app.Application
import net.justinas.exercise.lib.di.appModule
import net.justinas.exercise.lib.di.interactorModule
import net.justinas.exercise.lib.di.repositoryModule
import net.justinas.exercise.lib.di.viewModelModule
import org.koin.android.ext.android.startKoin

class App : Application () {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule, repositoryModule, viewModelModule, interactorModule))
    }
}

