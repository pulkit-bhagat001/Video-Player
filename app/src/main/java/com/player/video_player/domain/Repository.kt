package com.player.video_player.domain

import android.app.Application
import com.player.video_player.data.data_classes.VideoItem
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getAllVideos(context:Application):Flow<List<VideoItem>>
}