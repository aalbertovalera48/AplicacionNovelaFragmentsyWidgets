package com.example.gestionnovelasavanzado.ui.GestionSegundoPlano


import com.example.aplicacionnovela.ui.theme.Novela
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

// Clase FirebaseHelper para interactuar con Firebase
class FirebaseConfig {

    // Instancia de FirebaseDatabase
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    // Lista de novelas
    private val novelas: MutableList<Novela> = mutableListOf()

    // Método para cargar novelas desde Firebase
    fun cargarNovelas(titulo: String, listener: ValueEventListener) {
        val databaseReference: DatabaseReference = database.getReference("novelas")
        // Filtrar por el título especificado
        val query: Query = databaseReference.orderByChild("titulo").equalTo(titulo)
        query.addListenerForSingleValueEvent(listener)
    }

    // Método para obtener las novelas
    fun obtenerNovelas(): List<Novela> {
        return novelas
    }

    // Referencia a la base de datos de Firebase para la colección "novelas"
    private val databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("novelas")

    // Método para cargar todas las novelas desde Firebase
    fun cargarTodasLasNovelas(listener: ValueEventListener) {
        databaseReference.addListenerForSingleValueEvent(listener)
    }
}
