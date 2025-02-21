package com.player.video_player.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.player.video_player.data.VideoViewModel
import kotlinx.serialization.Serializable

@Composable
fun VideoApp(videoViewModel: VideoViewModel= hiltViewModel()) {
    Scaffold {
        val uri by videoViewModel.selectedUri.collectAsState()
        val navController= rememberNavController()
        NavHost(navController=navController, startDestination = Home, modifier = Modifier.padding(it)) {
            composable<Home>{ TabScreen(videoViewModel,navController) }
            composable<Video>{ uri?.let { it1 -> VideoPlayerScreen(it1) } }

        } }


}

@Serializable
object Home

@Serializable
object Video



