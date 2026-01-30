package com.mobileappsfrontend.weatherapp.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import android.content.Context
import com.mobileappsfrontend.weatherapp.data.api.AuthApi
import com.mobileappsfrontend.weatherapp.data.api.DefaultLocationApi
import com.mobileappsfrontend.weatherapp.data.api.FavouritesApi
import com.mobileappsfrontend.weatherapp.data.api.WeatherApi
import com.mobileappsfrontend.weatherapp.data.local.preferences.UserPreferences
import com.mobileappsfrontend.weatherapp.data.local.preferences.dataStore
import com.mobileappsfrontend.weatherapp.data.repository.AuthRepositoryImpl
import com.mobileappsfrontend.weatherapp.data.repository.FavouriteRepositoryImpl
import com.mobileappsfrontend.weatherapp.data.repository.WeatherRepositoryImpl
import com.mobileappsfrontend.weatherapp.domain.repository.AuthRepository
import com.mobileappsfrontend.weatherapp.domain.repository.FavouriteRepository
import com.mobileappsfrontend.weatherapp.domain.repository.WeatherRepository
import com.mobileappsfrontend.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import com.mobileappsfrontend.weatherapp.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext app: Context): UserPreferences {
        return UserPreferences(app.dataStore)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: WeatherApi,
        defaultLocationApi: DefaultLocationApi,
        prefs: UserPreferences
    ): WeatherRepository = WeatherRepositoryImpl(api, defaultLocationApi, prefs)

    @Provides
    @Singleton
    fun provideGetCurrentWeatherUseCase(repo: WeatherRepository): GetCurrentWeatherUseCase =
        GetCurrentWeatherUseCase(repo)

    @Provides
    @Singleton
    fun provideLoginUseCase(
        authRepository: AuthRepository
    ): LoginUseCase =
        LoginUseCase(authRepository)

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        prefs: UserPreferences
    ): AuthRepository =
        AuthRepositoryImpl(api, prefs)

    @Provides
    @Singleton
    fun provideAuthApi(
        retrofit: Retrofit
    ): AuthApi =
        retrofit.create(AuthApi::class.java)

    @Provides
    @Singleton
    fun provideDefaultLocationApi(
        retrofit: Retrofit
    ): DefaultLocationApi =
        retrofit.create(DefaultLocationApi::class.java)

    @Provides
    @Singleton
    fun provideFavouriteLocationApi(
        retrofit: Retrofit
    ): FavouritesApi =
        retrofit.create(FavouritesApi::class.java)

    @Provides
    @Singleton
    fun provideFavouritesRepository(
        api: FavouritesApi,
        prefs: UserPreferences
    ): FavouriteRepository =
        FavouriteRepositoryImpl(api, prefs)
}