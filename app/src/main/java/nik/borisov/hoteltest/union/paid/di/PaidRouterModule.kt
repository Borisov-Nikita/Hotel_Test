package nik.borisov.hoteltest.union.paid.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import nik.borisov.hoteltest.features.paid.presentation.PaidRouter
import nik.borisov.hoteltest.union.paid.adapters.AdapterPaidRouter

@Module
@InstallIn(ActivityRetainedComponent::class)
interface PaidRouterModule {

    @Binds
    fun bindPaidRouter(paidRouter: AdapterPaidRouter): PaidRouter
}