package com.example.aplicacionnovela.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NovelasListScreen(navController: NavController, novelas: List<Novela>) {
    var novelasState by remember { mutableStateOf(novelas) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Lista de Novelas", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(novelasState) { novela ->
                NovelaItem(
                    novela,
                    onClick = { navController.navigate("novela_details/${novela.id}") },
                    onFavoritoClick = {
                        novelasState = novelasState.map {
                            if (it.id == novela.id) it.copy(esfavorita = !it.esfavorita) else it
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("add_novela") }) {
            Text("Agregar Nueva Novela")
        }
    }
}


@Composable
fun NovelaDetailsScreen(navController: NavController, novela: Novela) {
    var nuevaResena by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Detalles de la Novela: ${novela.titulo}", style = MaterialTheme.typography.titleMedium)
        Text("Autor: ${novela.autor}")
        Text("Año: ${novela.añoPublicacion}")
        Text("Sinopsis: ${novela.sinopsis}")

        Spacer(modifier = Modifier.height(16.dp))

        // Reseñas
        Text("Reseñas:")
        LazyColumn {
            items(novela.reseñas) { resena ->
                Text("- $resena")
            }
        }

        OutlinedTextField(
            value = nuevaResena,
            onValueChange = { nuevaResena = it },
            label = { Text("Añadir Reseña") }
        )

        Button(onClick = {
            if (nuevaResena.isNotEmpty()) {
                novela.reseñas.add(nuevaResena)
                nuevaResena = ""
            }
        }) {
            Text("Añadir Reseña")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Volver a la Lista")
        }
    }
}


@Composable
fun AddNovelaScreen(navController: NavController, onAgregar: (Novela) -> Unit) {
    var titulo by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var añoPublicacion by remember { mutableStateOf("") }
    var sinopsis by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Agregar Nueva Novela", style = MaterialTheme.typography.titleMedium)

        TextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") })
        TextField(value = autor, onValueChange = { autor = it }, label = { Text("Autor") })
        TextField(value = añoPublicacion, onValueChange = { añoPublicacion = it }, label = { Text("Año de publicación") })
        TextField(value = sinopsis, onValueChange = { sinopsis = it }, label = { Text("Sinopsis") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (titulo.isNotEmpty() && autor.isNotEmpty() && añoPublicacion.isNotEmpty() && sinopsis.isNotEmpty()) {
                try {
                    val nuevaNovela = Novela(
                        id = 0,  // Inicializa con un id temporal (esto se ajusta fuera de esta función)
                        titulo = titulo,
                        autor = autor,
                        añoPublicacion = añoPublicacion.toInt(),
                        sinopsis = sinopsis
                    )
                    onAgregar(nuevaNovela)  // Llamar al callback con la nueva novela
                    navController.popBackStack()  // Volver a la lista
                } catch (e: NumberFormatException) {
                    // Manejar la excepción si el año no es válido
                }
            }
        }) {
            Text("Guardar Novela")
        }
    }
}


@Composable
fun NovelaItem(novela: Novela, onClick: () -> Unit, onFavoritoClick: () -> Unit) {
    Card(modifier = Modifier
        .padding(8.dp)
        .clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(novela.titulo, style = MaterialTheme.typography.titleMedium)
                Text("Autor: ${novela.autor}")
                Text("Año: ${novela.añoPublicacion}")
                Text("Sinopsis: ${novela.sinopsis}")
            }
            IconButton(onClick = onFavoritoClick) {
                Icon(
                    imageVector = if (novela.esfavorita) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = if (novela.esfavorita) "Quitar de favoritos" else "Añadir a favoritos"
                )
            }
        }
    }
}

