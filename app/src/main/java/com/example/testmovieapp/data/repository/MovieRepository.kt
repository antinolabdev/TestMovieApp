package com.movie.findmovie.data.repository

import com.movie.findmovie.data.network.ApiService
import com.movie.findmovie.utils.toResultFlow
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService) {

    fun getMovieList() = toResultFlow {
        apiService.getMoviesList()
    }
}