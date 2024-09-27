package com.example.aplicacionnovela.ui.theme


data class Novela(
    val id: Int,
    val titulo: String,
    val autor: String,
    val añoPublicacion: Int,
    val sinopsis: String,
    var esfavorita: Boolean= false,
    var reseñas: MutableList<String> = mutableListOf()
)
