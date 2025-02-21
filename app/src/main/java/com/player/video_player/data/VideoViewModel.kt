package com.player.video_player.data

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.player.video_player.data.data_classes.Events
import com.player.video_player.data.data_classes.VideoItem
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(application: Application,private val repo:RepoImpl):AndroidViewModel(application) {
    private val _uiState= MutableStateFlow(emptyList<VideoItem>())
    val uiState=_uiState.asStateFlow()

    private val _selectedUri=MutableStateFlow<Uri?>(null)
    val selectedUri=_selectedUri.asStateFlow()

    fun onEvent(event:Events){
        when(event){
            is Events.Uri -> {
                _selectedUri.value=event.pathUri
            }
        }
    }

    init {

        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllVideos(application).collectLatest{
                _uiState.value=it
                Log.d("Inside init","$it")


            }
        }
    }

}