package com.arvin.swapi.di

import android.app.Application
import androidx.room.Room
import com.arvin.swapi.data.local.db.AppDatabase
import com.arvin.swapi.data.local.prefs.SharedPreferencesRepository
import com.arvin.swapi.data.repository.ApiRepositoryImpl
import com.arvin.swapi.domain.repository.PreferencesRepository
import com.arvin.swapi.domain.repository.ApiRepository
import com.arvin.swapi.domain.usecase.PlanetUseCase
import com.arvin.swapi.presentation.features.planetdetails.PlanetDetailsPageViewModel
import com.arvin.swapi.presentation.features.planetlist.PlanetListPageViewModel
import com.arvin.swapi.data.remote.api.RetrofitClient
import com.arvin.swapi.data.repository.DBRepositoryImpl
import com.arvin.swapi.data.repository.PlanetRepositoryImpl
import com.arvin.swapi.domain.repository.DBRepository
import com.arvin.swapi.domain.repository.PlanetRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

/**
 * Koin dependency injection module for configuring and providing all core app dependencies.
 *
 * Registers singletons for network, database, repositories, use cases, and view models.
 * Ensures all dependencies are available for injection throughout the application.
 */
val appModule = module {

    // Retrofit API
    single { RetrofitClient.api }

    //Database
    single {
        Room.databaseBuilder(
            get<Application>(),
            AppDatabase::class.java,
            "planet_database"
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    single { get<AppDatabase>().planetDao() }

    // Repository
    single<ApiRepository> { ApiRepositoryImpl(api = get()) }
    single<PreferencesRepository> { SharedPreferencesRepository(context = androidContext()) }
    single<DBRepository> { DBRepositoryImpl(planetDao = get()) }
    single<PlanetRepository> { PlanetRepositoryImpl(apiRepository = get(), dbRepository = get()) }

    //UseCases
    single { PlanetUseCase(planetRepository = get()) }

    //ViewModels
    viewModel { PlanetListPageViewModel(planetUseCase = get()) }
    viewModel { PlanetDetailsPageViewModel(savedStateHandle = get(), planetUseCase = get()) }

}