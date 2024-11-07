package com.example.countriesapi.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.countriesapi.model.Country
import com.example.countriesapi.ui.viewmodels.CountryUiState
import com.example.countriesapi.ui.viewmodels.CountryViewModel


@Composable
fun CountryHomeScreen(innerPadding: PaddingValues,
                          navController: NavController,
                          countryViewModel: CountryViewModel = viewModel()
){
    // se declara el uiState desde el countryViewModel
    val uiState by countryViewModel.uiState.collectAsState()

    // carga el listado al volver a la pantalla
    LaunchedEffect(Unit) {
        countryViewModel.reloadCountries()
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("add")
                // Acción  clic en el botón
            },
                containerColor = Color(0xFF808080),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), // padding para que no se empalmen
            contentAlignment = Alignment.TopStart
        ) {
            when(uiState){

                is CountryUiState.Loading ->{
                    CircularProgressIndicator( modifier = Modifier.align(Alignment.Center))
                }

                is CountryUiState.Success ->{
                    val countries = ( uiState as CountryUiState.Success).countries
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ){
                        items(countries){ country ->
                            CountryItem(country)
                        }
                    }
                }
                is CountryUiState.Error -> {
                    val message = (uiState as CountryUiState.Error).message
                    Text(
                        text = message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { countryViewModel.reloadCountries() }) {
                        Text("Reintentar")
                    }
                }
            }
        }
    }
}

@Composable
fun CountryItem(country: Country) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            country.image?.let { flagUrl ->
                // Todo : Agregar AsyncImage
                AsyncImage(model = flagUrl,
                    contentDescription = country.name,
                    modifier = Modifier.size(64.dp))
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = country.name, style = TextStyle(fontSize = 24.sp))
                Spacer(modifier = Modifier.height(4.dp))
                val capital = country.capital
                Text(text = "Capital: ${capital}")
            }
        }
    }
}

//funcion para el preview de requisito
@Composable
fun funcionparapreview(countries: List<Country>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)//contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(countries) { country ->
                CountryItem(country) // Llama a CountryItem para cada país en la lista
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Previewderequisito() {
    val countries = paisdeejemploparapreview()
    funcionparapreview(countries = countries)

}

fun paisdeejemploparapreview(): List<Country> {
    return listOf(
        Country(name = "Mexico", capital = "CdMx", image = "https://flagcdn.com/w320/mx.png")
    )
}
