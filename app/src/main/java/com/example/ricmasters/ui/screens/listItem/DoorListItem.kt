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
import com.example.domain.models.DoorsDomain
import com.example.ricmasters.R
import com.example.ricmasters.ui.theme.TextBodyColor
import com.example.ricmasters.ui.theme.TextTitleColor
import com.example.ricmasters.ui.theme.TintBackgroundColor
import kotlin.math.roundToInt

@Composable
fun DoorListItem(
    door: DoorsDomain,
    isRevealed: Boolean,
    onCollapse: () -> Unit,
    onExpand: () -> Unit
){

    val offsetTransition = updateTransition(
        remember {
            MutableTransitionState(isRevealed).apply { targetState = !isRevealed }
        },
        "Transition"
    ).animateFloat(
        { tween(durationMillis = 500) },
        "OffsetTransition",
        { if (isRevealed) -(112f * Resources.getSystem().displayMetrics.density + 0.5f) else 0f },
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
        Column(modifier = Modifier.background(TintBackgroundColor)) {
            if (door.snapshot != null) {
                Card {
                    AsyncImage(
                        door.snapshot,
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
                }
            }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column() {
                        Text(
                           text = door.name,
                           color =  TextTitleColor,
                          fontSize =   17.sp
                        )
                        if (door.snapshot != null) {
                            Text(
                                stringResource(R.string.app_online),
                                color = TextBodyColor,
                                fontSize = 14.sp,
                            )
                        }
                    }
                    Image(
                        painterResource(R.drawable.ic_lock),
                        stringResource(R.string.app_lock),
                        Modifier
                            .size(32.dp)
                            .padding(top = 5.dp)
                    )
                }
        }
    }
}

@Preview
@Composable
fun DoorListItemPreview(){
    val doorItemImage = DoorsDomain(
    name="Door Door, Door Door",
    snapshot= "https://serverspace.ru/wp-content/uploads/2019/06/backup-i-snapshot.png",
    room="FIRST",
    id= 6,
    favorites=true
    )
    val doorItem = DoorsDomain(
        name="Door Door, Door ",
        snapshot= null,
        room="FIRST",
        id= 6,
        favorites=true
    )
    Column() {
        Row() {
            DoorListItem(doorItemImage,false,{},{})
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            DoorListItem(doorItem,false,{},{})
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            DoorListItem(doorItem,true,{},{})
        }
    }
}