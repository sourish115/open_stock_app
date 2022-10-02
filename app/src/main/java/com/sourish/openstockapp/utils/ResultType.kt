package com.sourish.openstockapp.utils

sealed class ResultType<T>(val data: T? = null, val message: String? = null){

    class Success<T>(data: T?):ResultType<T>(data)

    class Error<T>(data: T? = null,message: String?): ResultType<T>(data,message)

    class Loading<T>(val isLoading: Boolean = true): ResultType<T>()
}
