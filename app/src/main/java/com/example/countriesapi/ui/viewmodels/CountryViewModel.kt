package com.example.countriesapi.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriesapi.model.Country
import com.example.countriesapi.repositories.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CountryUiState{
    object Loading:CountryUiState()
    data class Success ( val countries:List<Country>) : CountryUiState()
    data class Error(val message:String):CountryUiState()

}

sealed class CountryAddUiState {
    object Idle : CountryAddUiState()
    object Loading : CountryAddUiState()
    object Success : CountryAddUiState()
    data class Error(val message: String) : CountryAddUiState()
}

// si no se pasa el parámetro, se instancia CountryRepository
class CountryViewModel (private val repository: CountryRepository = CountryRepository()
) : ViewModel() {
    private val _uiState = MutableStateFlow<CountryUiState>(CountryUiState.Loading)

    // para acceder de forma externa
    val uiState: StateFlow<CountryUiState> = _uiState

    private val _addUiState = MutableStateFlow<CountryAddUiState>(CountryAddUiState.Idle)
    val addUiState: StateFlow<CountryAddUiState> = _addUiState

    init{
        fetchCountries()
    }

    private fun fetchCountries(){

        viewModelScope.launch {
            try{
                _uiState.value = CountryUiState.Loading
                val countries = repository.getCountries()
                _uiState.value = CountryUiState.Success(countries)
            }catch(e:Exception){
                _uiState.value = CountryUiState.Error(e.localizedMessage ?:
                "Ha ocurrido un error en carga")
            }
        }
    }

    fun reloadCountries(){
        fetchCountries()
    }


    //  para agregar un nuevo país
    private fun addCountry(country: Country) {
        viewModelScope.launch {
            _addUiState.value = CountryAddUiState.Loading // estado Cargando
            try {
                repository.addCountry(country) // Llama al método del repositorio para agregar el país
                fetchCountries() // Actualiza la lista después de la inserción
                _addUiState.value = CountryAddUiState.Success // successfully

            } catch (e: Exception) {
                _addUiState.value = CountryAddUiState.Error(e.localizedMessage ?: "Ha ocurrido un error al guardar") // Maneja el error
            }
        }
    }

    // Llama a este método desde el Composable cuando quieras agregar un país
    fun onAddCountry(country: Country) {
        addCountry(country)
    }

}