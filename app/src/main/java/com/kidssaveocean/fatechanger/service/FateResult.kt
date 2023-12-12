package com.kidssaveocean.fatechanger.service

sealed class FateResult<out T> {

    data class Success<out T>(val data: T) : FateResult<T>()
    data class Failure(val error: Exception) : FateResult<Nothing>()

    fun getOrThrow(): T =
        when (this) {
            is Success -> data
            is Failure -> throw error
        }

    fun getOrNull(): T? =
        when (this) {
            is Success -> data
            is Failure -> null
        }

    inline fun onSuccess(block: (T) -> Unit): FateResult<T> {
        if (this is Success) block(data)
        return this
    }

    inline fun onFailure(block: (Exception) -> Unit): FateResult<T> {
        if (this is Failure) block(error)
        return this
    }

    inline fun <R> fold(onSuccess: (T) -> R, onFailure: (Exception) -> R): R =
        when (this) {
            is Success -> onSuccess(data)
            is Failure -> onFailure(error)
        }

    inline fun <R> map(block: (T) -> R): FateResult<R> =
        when (this) {
            is Success -> Success(block(data))
            is Failure -> this
        }

    inline fun <E : Exception> mapException(block: (Exception) -> E): FateResult<T> =
        when (this) {
            is Success -> this
            is Failure -> Failure(block(error))
        }
}