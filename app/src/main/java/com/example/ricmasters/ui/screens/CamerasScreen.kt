package com.example.ricmasters.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.models.CameraDomain
import com.example.ricmasters.R
import com.example.ricmasters.ui.screens.listItem.CameraListItem
import com.example.ricmasters.ui.theme.Circle
import com.example.ricmasters.ui.theme.PrimaryBackgroundColor
import com.example.ricmasters.ui.theme.TextGroupColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CamerasScreen(cameras: List<CameraDomain>,revealedCameraState: List<Int>,
                  onCollapse:(Int)->Unit,
                  onExpand:(Int)->Unit,
                  changeFavoriteState:(Int, Boolean)-> Unit
) {
    val grouped = cameras.groupBy { it.room }
    LazyColumn(
       modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        grouped.forEach { room, cameras->
            stickyHeader  {
                Row(modifier = Modifier.fillMaxWidth().background(PrimaryBackgroundColor), horizontalArrangement = Arrangement.Start) {
                    Text(
                        text = room.toString(),
                        modifier = Modifier
                            .padding(16.dp),
                        color=TextGroupColor,
                        fontSize = 21.sp,
                        fontFamily = Circle
                    )
                }
            }

            itemsIndexed(cameras) { _, camera ->
                Box(Modifier.fillMaxWidth()) {
                    Row(
                        Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .align(Alignment.CenterEnd)
                    ) {
                        IconButton(onClick = {changeFavoriteState(camera.id, !camera.favorites)},
                            modifier = Modifier.padding(start = 5.dp, top = 10.dp)
                        ) {
                            Image(
                                painter = painterResource(     if (camera.favorites) {
                                    R.drawable.ic_favorite
                                } else {
                                    R.drawable.ic_no_favorite
                                }),
                                stringResource(R.string.app_favorite),
                                modifier = Modifier.size(46.dp)
                            )
                        }
                    }
                    CameraListItem(camera = camera , isRevealed = revealedCameraState.contains(camera.id), onCollapse = {onCollapse(camera.id) }, onExpand = {onExpand(camera.id)})
                }
            }
        }

    }
}