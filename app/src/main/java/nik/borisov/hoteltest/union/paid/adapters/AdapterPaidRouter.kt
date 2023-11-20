package nik.borisov.hoteltest.union.paid.adapters

import nik.borisov.hoteltest.features.paid.presentation.PaidRouter
import nik.borisov.hoteltest.navigation.GlobalNavComponentRouter
import javax.inject.Inject

class AdapterPaidRouter @Inject constructor(
    private val globalNavComponentRouter: GlobalNavComponentRouter
) : PaidRouter {

    override fun popBack() {
        globalNavComponentRouter.restart()
    }
}