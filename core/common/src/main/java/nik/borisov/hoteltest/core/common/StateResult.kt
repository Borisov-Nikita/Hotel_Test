package nik.borisov.hoteltest.core.common

sealed class StateResult<out T> {

    abstract fun <R> map(mapper: ((T) -> R)): StateResult<R>

    data object Pending : StateResult<Nothing>() {
        override fun <R> map(mapper: (Nothing) -> R): StateResult<R> {
            return this
        }

    }

    data class Error(
        val exception: Exception
    ) : StateResult<Nothing>() {

        override fun <R> map(mapper: (Nothing) -> R): StateResult<R> {
            return this
        }

    }

    data class Success<T>(
        val value: T
    ) : StateResult<T>() {

        override fun <R> map(mapper: (T) -> R): StateResult<R> {
            return try {
                Success(mapper(value))
            } catch (e: Exception) {
                Error(e)
            }
        }
    }
}