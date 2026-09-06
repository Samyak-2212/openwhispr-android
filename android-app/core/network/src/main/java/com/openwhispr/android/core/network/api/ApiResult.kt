package com.openwhispr.android.core.network.api

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val code: Int, val message: String?, val exception: Exception? = null) : ApiResult<Nothing>()
}
