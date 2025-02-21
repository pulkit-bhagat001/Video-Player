package com.player.video_player.data

import android.app.Application
import android.content.ContentUris
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import com.player.VideoPlayer.R
import com.player.video_player.data.data_classes.VideoItem
import com.player.video_player.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun correctTime(milliseconds:Long):String{
    val hours= TimeUnit.MILLISECONDS.toHours(milliseconds)
    val minutes= TimeUnit.MILLISECONDS.toMinutes(milliseconds)%60
    val seconds= TimeUnit.MILLISECONDS.toSeconds(milliseconds)%60

    return String.format("%02d:%02d:%02d",hours,minutes,seconds)
}

fun correctDate(timeStamp:Long):String{
    val date= Date(timeStamp)
    val format= SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return format.format(date)
}

fun formatSize(bytes: Long): String {
    val kilobyte = 1024.0
    val megabyte = kilobyte * 1024
    val gigabyte = megabyte * 1024

    return when {
        bytes >= gigabyte -> String.format("%.2f GB", bytes / gigabyte)
        bytes >= megabyte -> String.format("%.2f MB", bytes / megabyte)
        bytes >= kilobyte -> String.format("%.2f KB", bytes / kilobyte)
        else -> "$bytes Bytes"
    }
}
class RepoImpl : Repository {

    override suspend fun getAllVideos(context: Application): Flow<List<VideoItem>> = flow {
        val contentResolver = context.contentResolver ?: return@flow
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DISPLAY_NAME
        )
        contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            "${MediaStore.Video.Media.DATE_ADDED} DESC"
        )?.use {
            cursor->
            val list1= mutableListOf<VideoItem>()
            val idIndex=cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val durationIndex=cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val nameIndex=cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val sizeIndex=cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            val dateAddedIndex=cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)

            while(cursor.moveToNext()){

                val id=cursor.getLong(idIndex)
                val videoUri=ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,id)
                val duration=cursor.getLong(durationIndex)
                val name=cursor.getString(nameIndex)
                val size=cursor.getLong(sizeIndex)
                val dateAdded=cursor.getLong(dateAddedIndex)
                var thumbnail:Bitmap?
                try{
                    thumbnail=context.contentResolver.loadThumbnail(videoUri, Size(640,310),null)

                }catch (e:Exception){
                    Log.d("Exception","$e")
                    thumbnail=null

                }
                val finalSize= formatSize(size)
                val finalDuration= correctTime(duration)
                val finalDate= correctDate(dateAdded*1000)
                list1.add(VideoItem(name,finalDuration,finalSize,videoUri,finalDate,thumbnail))

                /*thumbnail?.let {
                    VideoItem(name,finalDuration,finalSize,videoUri,finalDate,
                        it
                    )
                }?.let { list1.add(it) }*/

            }
            Log.d("Final List","$list1")
            emit(list1.toList())

        }?:emit(emptyList())
    }
}