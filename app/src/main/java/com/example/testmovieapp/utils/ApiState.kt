package com.movie.findmovie.utils

import com.movie.findmovie.data.model.Movies

sealed class ApiState<out T> {

    data class Success<R>(val data: R) : ApiState<R>()
    data class Failure(val msg: String) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()

}