package com.example.ricmasters.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.domain.models.DoorsDomain
import com.example.ricmasters.R
import com.example.ricmasters.ui.screens.listItem.DoorListItem
import com.example.ricmasters.ui.screens.models.EditDoor

@Composable
fun DoorsScreen(doors: List<DoorsDomain>,
                revealedDoorsState: List<Int>,
                changeFavoriteState:(Int, Boolean)-> Unit,
                onItemCollapsed:(Int)->Unit,
                onItemExpanded:(Int)->Unit,
                editDoor: (EditDoor)-> Unit
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(doors) { _, door ->
            Box(Modifier.fillMaxWidth()) {
                Row(
                    Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .align(Alignment.CenterEnd)
                ) {
                    IconButton(onClick = {editDoor(EditDoor(
                        doorId = door.id,
                        name = door.name
                    ))},
                    modifier = Modifier.padding(start = 5.dp, top = 10.dp)
                        ) {
                        Image(
                            painter = painterResource(R.drawable.ic_edit),
                            stringResource(R.string.app_edit),
                            modifier = Modifier.size(46.dp)
                        )
                    }
                    IconButton(onClick = {changeFavoriteState(door.id, !door.favorites)},
                        modifier = Modifier.padding(start = 5.dp, top = 10.dp)
                    ) {
                        Image(
                            painter = painterResource(                    if (door.favorites) {
                                R.drawable.ic_favorite
                            } else {
                                R.drawable.ic_no_favorite
                            }),
                            stringResource(R.string.app_favorite),
                            modifier = Modifier.size(46.dp)
                        )
                    }

                }
                DoorListItem(
                    door, revealedDoorsState.contains(door.id),
                    { onItemCollapsed(door.id) },
                    { onItemExpanded(door.id) })
            }
        }
    }
}