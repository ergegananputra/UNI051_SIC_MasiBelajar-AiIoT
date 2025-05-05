package com.sic6.masibelajar.domain.resources.wrapper

sealed interface Result<out Data, out Error: ErrorBase> {

    data class Success<out Data, out Error: ErrorBase>(
        val data: Data) : Result<Data, Error>

    data class Failure<out Data, out Error: ErrorBase>(
        val error: Error) : Result<Data, Error>

}