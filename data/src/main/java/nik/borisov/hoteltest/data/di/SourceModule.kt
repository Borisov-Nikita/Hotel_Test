package nik.borisov.hoteltest.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nik.borisov.hoteltest.data.sources.DataSource
import nik.borisov.hoteltest.data.sources.DataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SourceModule {

    @Binds
    @Singleton
    fun bindDataSource(
        dataSource: DataSourceImpl
    ): DataSource
}