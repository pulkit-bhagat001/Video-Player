package com.player.video_player.data.data_classes

import android.net.Uri

sealed class Events {
    data class Uri(val pathUri: android.net.Uri):Events()
}