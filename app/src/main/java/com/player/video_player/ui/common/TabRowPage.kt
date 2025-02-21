package com.player.video_player.ui.common

import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.VideoLibrary
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.util.fastForEachIndexed
import kotlinx.coroutines.launch
val list1 = listOf(
   // Video("All Folders",Icons.Filled.Folder, Icons.Outlined.Folder),
    Video("All Videos", Icons.Filled.VideoLibrary, Icons.Outlined.VideoLibrary)
)
@Composable
fun TabRowPage(pagerState: PagerState) {

    val scope = rememberCoroutineScope()


    TabRow(selectedTabIndex = pagerState.currentPage) {
        list1.fastForEachIndexed { index, tabItem ->
            Tab(
                selected = index == pagerState.currentPage,
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                icon = {
                    if (index == pagerState.currentPage) Icon(
                        imageVector = tabItem.selectedIcon,
                        contentDescription = ""
                    ) else Icon(imageVector = tabItem.unselectedIcon, contentDescription = "")
                },
                text = { Text(text = tabItem.name) },
                )

        }

    }


}

data class Video(
    val name: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)