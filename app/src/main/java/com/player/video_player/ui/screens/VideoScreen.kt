package com.player.video_player.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.player.VideoPlayer.R
import com.player.video_player.data.VideoViewModel
import com.player.video_player.data.data_classes.Events
import com.player.video_player.data.data_classes.VideoItem


@Composable
fun VideoScreen(videoViewModel: VideoViewModel, navController: NavController) {
    val state by videoViewModel.uiState.collectAsState()
    Log.d("VideoScreen","${state}")

    if(state.isNotEmpty()){
        LazyColumn {
            items(state){
                CreateCard(videoViewModel,it,navController)
            }
        }
    }else{
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column { CircularProgressIndicator()
                Text(text="Loading") }

        }
    }


}


@Composable
fun CreateCard(videoViewModel: VideoViewModel, videoItem: VideoItem, navController: NavController) {

    Row(
        Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(10.dp).clickable {
               videoViewModel.onEvent(Events.Uri(videoItem.pathUri))
                navController.navigate(Video)
            }
    ) {
        Box {
            Card(modifier = Modifier
                .width(150.dp)
                .height(110.dp)) {
                if (videoItem.thumbnail != null) {
                    Image(
                        bitmap = videoItem.thumbnail.asImageBitmap(),
                        contentDescription = "",
                        contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = "",
                        contentScale = ContentScale.Crop, modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Text(
                text = videoItem.duration,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .background(
                        color = Color.Black,
                        shape = RoundedCornerShape(9.dp) // Rounded corners for a rectangle-like shape
                    )
                    .padding(horizontal = 4.dp), // Symmetric padding
                color = Color.White,
                fontSize = 8.sp
            )

        }
        Column(
            Modifier
                .fillMaxHeight()
                .padding(start = 10.dp, top = 20.dp)
                .fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(text = videoItem.name,Modifier.fillMaxWidth(), fontSize = 18.sp)
            Text(text = "${videoItem.size}, ${videoItem.dateAdded}", modifier = Modifier.weight(1f), color = Color.Gray, fontSize = 12.sp)

        }


    }
}

