package com.example.ricmasters.ui.screens.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ricmasters.R
import com.example.ricmasters.ui.screens.models.EditDoor
import com.example.ricmasters.ui.theme.Circle
import com.example.ricmasters.ui.theme.PrimaryBackgroundColor
import com.example.ricmasters.ui.theme.TextTitleColor

@Composable
fun ChangeDialogData(
     editDoor: EditDoor,
     saveData: (EditDoor)->Unit,
     closeDialog: () -> Unit
){
    val doorName = remember { mutableStateOf(editDoor.name) }
    Dialog(
        onDismissRequest = {},
        content = {
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth(),
                elevation = 10.dp
            ) {
                Column(
                    modifier = Modifier
                        .background(PrimaryBackgroundColor)
                        .padding(12.dp)
                ) {
                    Row() {
                        Text(
                            stringResource(R.string.app_door_name),
                            Modifier.padding(5.dp),
                            TextTitleColor,
                            18.sp,
                            fontFamily = Circle
                        )
                    }
                    Row() {
                        TextField(doorName.value, { doorName.value = it })
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                       modifier = Modifier
                           .padding(8.dp)
                           .fillMaxWidth(),
                        Arrangement.SpaceBetween
                    ) {
                        Button(onClick = { closeDialog() }) {
                            Text(text = stringResource(R.string.app_dialog_cansel), fontSize =18.sp, fontFamily = Circle)
                        }
                        Button(onClick = { saveData(EditDoor(editDoor.doorId, doorName.value))  }) {
                            Text(text = stringResource(R.string.app_dialog_save), fontSize =18.sp, fontFamily = Circle)
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun ChangeDialogDataPreview(){
    ChangeDialogData(EditDoor(12,"zadfgzdf"),{}){}
}
