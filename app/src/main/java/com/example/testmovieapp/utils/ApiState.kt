package com.example.testmovieapp.utils

sealed class ApiState<out T> {

    data class Success<R>(val data: R) : ApiState<R>()
    data class Failure(val msg: String) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()

}