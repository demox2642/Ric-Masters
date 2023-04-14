package com.example.ricmasters.ui.screens.listItem

import android.content.res.Resources
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.models.CameraDomain
import com.example.ricmasters.R
import com.example.ricmasters.ui.theme.Circle
import com.example.ricmasters.ui.theme.TextGroupColor
import com.example.ricmasters.ui.theme.TextTitleColor
import com.example.ricmasters.ui.theme.TintBackgroundColor
import kotlin.math.roundToInt

@Composable
fun CameraListItem(camera: CameraDomain, isRevealed: Boolean, onCollapse: () -> Unit, onExpand: () -> Unit) {
    val offsetTransition = updateTransition(
        remember {
            MutableTransitionState(isRevealed).apply { targetState = !isRevealed }
        },
        "Transition"
    ).animateFloat(
        { tween(durationMillis = 500) },
        "OffsetTransition",
        { if (isRevealed) -(56f * Resources.getSystem().displayMetrics.density + 0.5f) else 0f },
    ).value.roundToInt()
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .offset { IntOffset(offsetTransition, 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    when {
                        dragAmount >= 6 -> onCollapse()
                        dragAmount < -6 -> onExpand()
                    }
                }
            }
    ) {
        Text(
                text =camera.room.toString(),
               modifier = Modifier
                    .padding(start = 2.dp, bottom = 16.dp),
                color = TextGroupColor,
               fontSize = 21.sp,
                fontFamily = Circle
            )
        Column(modifier = Modifier.background(TintBackgroundColor)) {
            Card {
                AsyncImage(
                    camera.snapshot,
                    stringResource(R.string.app_camera_preview),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                       // .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
                    painterResource(R.drawable.no_image),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(230.dp),
                    Arrangement.SpaceAround,
                    Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(R.drawable.ic_play_button),
                        stringResource(R.string.app_play_button),
                        modifier = Modifier
                            .size(60.dp)
                            .padding(top = 5.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(7.dp)
                ) {
                    if (camera.rec) {
                        Image(
                            painterResource(R.drawable.ic_rec),
                            stringResource(R.string.app_record),
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterStart)
                        )
                    }
                    if (camera.favorites) {
                        Image(
                            painterResource(R.drawable.ic_star),
                            stringResource(R.string.app_favorite),
                            modifier = Modifier
                                .size(20.dp)
                                .align(Alignment.CenterEnd)
                        )
                    }
                }
            }
                Row(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(horizontal = 16.dp)
                       .padding(top = 16.dp, bottom = 26.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = camera.name,
                        color =  TextTitleColor,
                        fontSize =   17.sp
                    )
                }
        }
    }
}


@Preview
@Composable
fun CameraListItemPreview(){
    val cameraRec = CameraDomain(
        name= "Camera 2",
    snapshot= "https://serverspace.ru/wp-content/uploads/2019/06/backup-i-snapshot.png",
    room= "FIRS",
    id= 3,
    favorites= true,
    rec= true
    )

    val cameraNoRec=CameraDomain(
        name= "Camera 2",
        snapshot= "https://serverspace.ru/wp-content/uploads/2019/06/backup-i-snapshot.png",
        room= "",
        id= 3,
        favorites= true,
        rec= false
    )
    Column() {
        Row() {
            CameraListItem(cameraRec,false,{},{})
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            CameraListItem(cameraNoRec,false,{},{})
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            CameraListItem(cameraNoRec,true,{},{})
        }

    }

}