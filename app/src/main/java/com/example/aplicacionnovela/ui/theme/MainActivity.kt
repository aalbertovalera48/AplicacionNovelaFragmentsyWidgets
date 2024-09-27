package com.example.aplicacionnovela.ui.theme;

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val novelas = remember { mutableStateListOf<Novela>() }
            NavHost(navController = navController, startDestination = "novelas_list") {
                composable("novelas_list") {
                    NovelasListScreen(navController = navController, novelas = novelas)
                }
                composable("add_novela") {
                    AddNovelaScreen(navController = navController, onAgregar = { novela ->
                        val id =
                            if (novelas.isEmpty()) 1 else novelas.maxOf { it.id } + 1  // Genera un nuevo ID
                        val nuevaNovelaConId =
                            novela.copy(id = id)  // Copia la novela con el nuevo ID
                        novelas.add(nuevaNovelaConId)  // Agrega la novela a la lista
                    })
                }
                composable("novela_details/{novelaId}") { backStackEntry ->
                    val novelaId = backStackEntry.arguments?.getString("novelaId")?.toIntOrNull()
                    val novela = novelas.find { it.id == novelaId }
                    novela?.let {
                        NovelaDetailsScreen(navController = navController, novela = it)
                    }
                }
            }
        }
    }}

