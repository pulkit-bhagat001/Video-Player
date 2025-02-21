package com.player.video_player.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.player.video_player.data.VideoViewModel
import com.player.video_player.ui.screens.VideoScreen

@Composable
fun HorizontalPagerNav(
    pagerState: PagerState,
    videoViewModel: VideoViewModel,
    navController: NavController
) {
    HorizontalPager(state = pagerState,Modifier.fillMaxSize()) {
        when(it){
            0->{                VideoScreen(videoViewModel,navController)
            }
//            1->{
//                VideoScreen(videoViewModel,navController)
//
//            }
        }
    }

}