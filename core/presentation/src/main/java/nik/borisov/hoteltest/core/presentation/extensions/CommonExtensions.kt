package nik.borisov.hoteltest.core.presentation.extensions

fun Int.preparePriceForUi(): String {
    return this.toString().reversed().chunked(3).joinToString(" ").reversed()
}