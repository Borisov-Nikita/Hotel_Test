package nik.borisov.hoteltest.data.sources.remote

import nik.borisov.hoteltest.core.common.StateResult
import retrofit2.Response

inline fun <T, R> Response<T>.safeAsResult(
    mapper: (T) -> R
): StateResult<R> {
    return try {
        if (this.isSuccessful) {
            val body = this.body()
            body?.let {
                StateResult.Success(value = mapper(body))
            } ?: throw NullPointerException("Response body is empty")
        } else {
            throw Exception("Something went wrong")
        }
    } catch (e: Exception) {
        StateResult.Error(e)
    }
}