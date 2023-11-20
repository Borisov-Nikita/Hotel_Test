package nik.borisov.hoteltest.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nik.borisov.hoteltest.data.repositories.DataRepository
import nik.borisov.hoteltest.data.repositories.DataRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindDataRepository(
        dataRepository: DataRepositoryImpl
    ): DataRepository
}