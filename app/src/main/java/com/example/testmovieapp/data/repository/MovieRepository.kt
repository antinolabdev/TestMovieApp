package com.example.testmovieapp.data.repository

import com.example.testmovieapp.data.network.ApiService
import com.example.testmovieapp.utils.toResultFlow
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService) {

    fun getMovieList() = toResultFlow {
        apiService.getMoviesList()
    }
}