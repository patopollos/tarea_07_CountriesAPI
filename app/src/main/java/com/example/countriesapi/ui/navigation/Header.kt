package com.example.countriesapi.ui.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(title:String){

    TopAppBar( title = { Text(title) },
        colors = topAppBarColors(
            containerColor = Color(0xFF808080),
            titleContentColor =  Color.Black
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTopBar(){
    Header("Inicio")
}