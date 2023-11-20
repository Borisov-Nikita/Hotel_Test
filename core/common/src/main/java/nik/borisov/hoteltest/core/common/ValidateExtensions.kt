package nik.borisov.hoteltest.core.common

import android.util.Patterns
import java.util.regex.Pattern

const val PHONE_REGEX = "\\d{10}"

fun CharSequence.isPhoneNumber(): Boolean {
    val pattern = Pattern.compile(PHONE_REGEX)
    return pattern.matcher(this).find()
}

fun CharSequence.isEmail(): Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(this).find()
}