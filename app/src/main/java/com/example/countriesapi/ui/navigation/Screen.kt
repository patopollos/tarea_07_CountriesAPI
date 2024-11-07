package com.example.countriesapi.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector


sealed class Screen (val route:String, val title:String, val icon: ImageVector) {
    // rutas de navegación
    data object Home : Screen("home", "Inicio", Icons.Default.Home)
    data object Add: Screen("add", "Agregar", Icons.Default.Add)

}