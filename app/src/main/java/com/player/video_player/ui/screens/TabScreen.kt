package com.player.video_player.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.player.video_player.data.VideoViewModel
import com.player.video_player.ui.common.HorizontalPagerNav
import com.player.video_player.ui.common.TabRowPage
import com.player.video_player.ui.common.list1

@Composable
fun TabScreen(videoViewModel: VideoViewModel, navController: NavController) {
    val pagerState = rememberPagerState(pageCount = { list1.size })
    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            TabRowPage(pagerState)
            HorizontalPagerNav(pagerState, videoViewModel,navController)
        }
    }
}