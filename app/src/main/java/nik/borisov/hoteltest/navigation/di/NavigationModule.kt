package nik.borisov.hoteltest.navigation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nik.borisov.hoteltest.core.presentation.ActivityRequired
import nik.borisov.hoteltest.navigation.GlobalNavComponentRouter

@Module
@InstallIn(SingletonComponent::class)
class NavigationModule {

    @Provides
    fun provideRouterAsActivityRequired(
        router: GlobalNavComponentRouter,
    ): ActivityRequired {
        return router
    }
}