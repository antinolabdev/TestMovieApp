package com.example.testmovieapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.RoundedCornersTransformation
import com.example.testmovieapp.R
import com.example.testmovieapp.ui.theme.TestMovieAppTheme
import com.example.testmovieapp.viewmodel.MovieViewModel
import com.movie.findmovie.data.model.Movies
import com.movie.findmovie.data.network.ApiService
import com.movie.findmovie.utils.ApiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestMovieAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    GetMovieData(viewModel = viewModel, context = this)
                }
            }
        }
    }
}

@Composable
fun GetMovieData(viewModel: MovieViewModel, context: Context) {
    viewModel.getPost.collectAsState(ApiState.Loading).value.let {
        when (it) {
            is ApiState.Success -> {
                LazyColumn {
                    items(it.data.results) { movieData ->
                        SetMovieData(data = movieData) {
                            onClickListener(movieData, context)
                        }
                    }
                }
            }
            is ApiState.Failure -> {

                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(text = "${it.msg}")
                }

            }
            is ApiState.Loading -> {
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SetMovieData(data: Movies.Results, onClickListener: () -> Unit) {

    Card(modifier = Modifier
        .padding(horizontal = 8.dp, vertical = 8.dp)
        .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        onClick = { onClickListener() }

    ) {
        Row {
            Image(painter = rememberImagePainter(data = "${ApiService.IMAGE_URL}${data.poster_path}", builder = {
                transformations(RoundedCornersTransformation(6F))
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
            }), contentDescription = null, modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterVertically)
                .padding(start = 5.dp))
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.CenterVertically)) {
                Text(text = data.original_title!!, style = MaterialTheme.typography.h6)
                Text(text = data.overview!!, style = MaterialTheme.typography.caption)

            }
        }
    }

}

fun onClickListener(data: Movies.Results, context: Context) {
    val intent = Intent(context, MovieDetailActivity::class.java).apply {
        putExtra("image", "${ApiService.IMAGE_URL}${data.poster_path}")
        putExtra("title", data.original_title)
        putExtra("vote", data.vote_average)
        putExtra("overview", data.overview)
    }
    context.startActivity(intent)
}

/*
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TestMovieAppTheme {
        Greeting("Android")
    }
}*/
