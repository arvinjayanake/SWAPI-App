package com.arvin.swapi.di

import com.arvin.swapi.data.local.SharedPreferencesRepository
import com.arvin.swapi.data.repository.SWApiRepositoryImpl
import com.arvin.swapi.domain.repository.PreferencesRepository
import com.arvin.swapi.domain.repository.SWApiRepository
import com.arvin.swapi.domain.usecase.PlanetUseCase
import com.arvin.swapi.presentation.features.planetdetails.PlanetDetailsPageViewModel
import com.arvin.swapi.presentation.features.planetlist.PlanetListPageViewModel
import com.arvin.swapi.data.remote.api.RetrofitClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModel

val appModule = module {

    // Retrofit API
    single { RetrofitClient.api }

    // Repository
    single<SWApiRepository> { SWApiRepositoryImpl(api = get()) }
    single<PreferencesRepository> { SharedPreferencesRepository(context = androidContext()) }

    //UseCases
    single { PlanetUseCase(repository = get()) }

    //ViewModels
    viewModel { PlanetListPageViewModel(planetUseCase = get()) }
    viewModel { PlanetDetailsPageViewModel(savedStateHandle = get(), planetUseCase = get()) }

}