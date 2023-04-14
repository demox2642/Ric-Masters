package com.example.ricmasters.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ricmasters.R
import com.example.ricmasters.ui.screens.dialogs.ChangeDialogData
import com.example.ricmasters.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen (){
    val viewModel: MainViewModel = hiltViewModel()
    val doors  by viewModel.doors.observeAsState()
    val cameras by viewModel.cameras.observeAsState()
    val revealedCamera by viewModel.revealedCamera.observeAsState()
    val revealedDoors by viewModel.revealedDoors.observeAsState()
    val showEditDialog by viewModel.showEditDialog.observeAsState()
    val list: List<String> =  listOf(stringResource(R.string.app_cameras), stringResource(R.string.app_doors))
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(0)

    Column (modifier = Modifier.background(PrimaryBackgroundColor)){

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Text(
                stringResource(R.string.app_title),
                fontSize = 21.sp,
                color = TextGroupColor,
                fontFamily = Circle
            )
        }
        if (showEditDialog !=null){
                ChangeDialogData(editDoor = showEditDialog!!,
                    saveData = viewModel::changeDoorName,
                    closeDialog=viewModel::closeDialog
                )
        }
        TabRow(
            pagerState.currentPage,
            backgroundColor = PrimaryBackgroundColor,
            contentColor = TintBackgroundColor,
            divider = { TabRowDefaults.Divider(thickness = 2.dp, color = ControlColor) },
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                   modifier = Modifier.tabIndicatorOffset(currentTabPosition = tabPositions[pagerState.currentPage],), 2.dp, BlueControlColor
                )
            }
        ) {
            list.forEachIndexed { index, title ->
                Tab(
                    pagerState.currentPage == index,
                    {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text=title,
                            color = TextGroupColor,
                            fontFamily = Circle,
                            fontSize = 17.sp
                        )
                    }
                )
            }
        }
        HorizontalPager(list.size, state = pagerState) { page ->
            when (page) {
                0 -> {
                    viewModel.allDoorsCollapse()
                    if (!cameras.isNullOrEmpty()) {

                        CamerasScreen(cameras=cameras!!,
                            revealedCamera!!,
                            onCollapse = viewModel::onCameraItemCollapsed,
                            onExpand = viewModel::onCameraItemExpanded,
                            changeFavoriteState= viewModel::setCameraFavorite)
                    }
                }
                1 -> {
                    viewModel.allCamerasCollapse()
                    if (!doors.isNullOrEmpty()) {
                        DoorsScreen(doors = doors!!, revealedDoorsState = revealedDoors!!,
                            changeFavoriteState = viewModel::setDoorFavorite,
                            onItemCollapsed = viewModel::onDoorsItemCollapsed,
                            onItemExpanded = viewModel::onDoorsItemExpanded,
                            editDoor = viewModel::showEditDialog
                        )
                    }
                }
            }
        }
    }
}