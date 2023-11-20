package nik.borisov.hoteltest.data.sources.fake

import javax.inject.Inject
import kotlin.random.Random

class OrderNumberFaker @Inject constructor() {

    fun getFakeOrderNumber(): Int {
        return Random.nextInt(100000, 999999)
    }
}