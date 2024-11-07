package com.example.countriesapi.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.countriesapi.ui.navigation.Screen
import com.example.countriesapi.ui.navigation.Header
import com.example.countriesapi.ui.screens.CountryAddScreen
import com.example.countriesapi.ui.screens.CountryHomeScreen


@Composable
fun MainScreen(){
    val navController = rememberNavController()

    val title = remember { mutableStateOf("Países del mundo mundial") }

    Scaffold (

        topBar = { Header(title = title.value) }, // Usa el estado como título


        ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier =  Modifier.padding(innerPadding)
        ){
            composable(Screen.Home.route){
                title.value = "Paises del mundo mundial"
                CountryHomeScreen(innerPadding, navController)
            }
            composable(Screen.Add.route){
                title.value = "Alta de paises"
                CountryAddScreen(innerPadding, navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopBar(){
    MainScreen()
}

