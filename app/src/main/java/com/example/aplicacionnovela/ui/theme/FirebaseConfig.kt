package com.example.gestionnovelasavanzado.ui.GestionSegundoPlano

import com.example.aplicacionnovela.ui.theme.Novela
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class FirebaseConfig {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference: DatabaseReference = database.getReference("novelas")

    fun cargarNovelas(titulo: String, listener: ValueEventListener) {
        val query: Query = databaseReference.orderByChild("titulo").equalTo(titulo)
        query.addListenerForSingleValueEvent(listener)
    }

    fun cargarTodasLasNovelas(listener: ValueEventListener) {
        databaseReference.addListenerForSingleValueEvent(listener)
    }

    fun agregarNovela(novela: Novela) {
        val key = databaseReference.push().key
        key?.let {
            novela.id = it
            databaseReference.child(it).setValue(novela)
        }
    }

    fun eliminarNovela(titulo: String, listener: ValueEventListener) {
        val query: Query = databaseReference.orderByChild("titulo").equalTo(titulo)
        query.addListenerForSingleValueEvent(listener)
    }
}