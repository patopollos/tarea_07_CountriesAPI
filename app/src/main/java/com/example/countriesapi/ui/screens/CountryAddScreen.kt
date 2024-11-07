package com.example.countriesapi.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.countriesapi.model.Country
import com.example.countriesapi.ui.viewmodels.CountryAddUiState
import com.example.countriesapi.ui.viewmodels.CountryViewModel
import androidx.compose.runtime.LaunchedEffect


@Composable
fun CountryAddScreen(innerPadding: PaddingValues,
                     navController: NavController,
                     countryViewModel: CountryViewModel = viewModel()
){

    val addUiState by countryViewModel.addUiState.collectAsState()

    var name by remember { mutableStateOf("") }
    var capital by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }


    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("home") // se regresa a home
            },
                //containerColor = Color.DarkGray ,
                containerColor = Color(0xFF808080),
                contentColor = Color.White// Color azul marino
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                //
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(paddingValues), // el padding para evitar que se empalmen
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Ingresa todos los datos solictados, todos.",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF808080),
                    modifier = Modifier.fillMaxWidth().padding(18.dp)
                )

                TextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 20.dp)
                )
                TextField(
                    value = capital,
                    onValueChange = {
                        capital = it
                    },
                    label = { Text("Capital") },
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 30.dp)
                )
                TextField(
                    value = imageUrl,
                    onValueChange = {
                        imageUrl = it
                    },
                    label = { Text("Url de la Bandera") },
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 30.dp)
                )
                Button(onClick = {
                    // Validar que los campos no estén vacíos
                    if (name.isBlank() || capital.isBlank() || imageUrl.isBlank()) {
                        errorMessage = "Todos los campos son obligatorios."
                    } else {
                        val newCountry = Country(name = name, capital = capital, image = imageUrl)
                        countryViewModel.onAddCountry(newCountry) // Llama al método para agregar el país
                    }
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF808080),
                        contentColor = Color.White // Color del texto o ícono en el botón
                    ),
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 30.dp)) {
                    Text("Agregar País")
                }

                // mensaje de error
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }


                // revisa el estado de adición y muestra el contenido
                when (addUiState) {
                    is CountryAddUiState.Loading -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                    is CountryAddUiState.Success -> {
                        Text("País agregado correctamente", color = Color(0xFF4C9444))
                        LaunchedEffect(Unit) {
                            navController.popBackStack() // Navega de regreso tras agregar el país
                        }
                    }
                    is CountryAddUiState.Error -> {
                        val message = (addUiState as CountryAddUiState.Error).message
                        Text(text = message, color = Color.Red)
                    }
                    else -> {}
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCountryAddScreen() {
    CountryAddScreen(innerPadding = PaddingValues(16.dp), navController = rememberNavController())
}