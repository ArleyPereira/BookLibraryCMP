package br.com.hellodev.di

import br.com.hellodev.data.database.getRoomDatabase
import br.com.hellodev.presenter.features.details.DetailsViewModel
import br.com.hellodev.presenter.features.home.HomeViewModel
import br.com.hellodev.presenter.features.manage.ManageViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val targetModule: Module

val sharedModule = module {
    single { getRoomDatabase(get()) }

    viewModelOf(::HomeViewModel)

    viewModel {
        ManageViewModel(
            database = get(),
            savedStateHandle = get()
        )
    }

    viewModel {
        DetailsViewModel(
            database = get(),
            savedStateHandle = get()
        )
    }
}

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(targetModule, sharedModule)
    }
}