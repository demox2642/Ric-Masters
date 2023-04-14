package com.example.ricmasters.ui.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MainScreen (){
    val viewModel: MainViewModel = hiltViewModel()
val doors  by viewModel.doors.observeAsState()
    Text(text = "Hello Tra ta ta !$doors")
}