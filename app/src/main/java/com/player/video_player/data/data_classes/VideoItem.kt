package com.player.video_player.data.data_classes

import android.graphics.Bitmap
import android.net.Uri

data class VideoItem(
    val name:String,
    val duration:String,
    val size:String,
    val pathUri:Uri,
    val dateAdded:String,
    val thumbnail:Bitmap?

)
data class NewState(
    val pathUri: Uri
)
