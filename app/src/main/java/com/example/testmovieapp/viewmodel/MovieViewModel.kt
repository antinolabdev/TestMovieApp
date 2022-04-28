package com.example.testmovieapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testmovieapp.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
@Inject constructor(private val movieRepository: MovieRepository) : ViewModel() {

     val getPost = movieRepository.getMovieList()
}