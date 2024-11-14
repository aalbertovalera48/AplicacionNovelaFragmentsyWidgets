package com.example.aplicacionnovela.ui.theme

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionnovela.R
import com.example.gestionnovelasavanzado.ui.GestionSegundoPlano.FirebaseConfig
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class FavoritosActivity : AppCompatActivity() {

    private lateinit var novelasFavoritas: MutableList<Novela>
    private lateinit var adapter: NovelaAdapter
    private lateinit var btnVolver: Button
    private lateinit var btnCargarFavoritos: Button
    private lateinit var firebaseHelper: FirebaseConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favoritos_activity)

        val listViewFavoritos: ListView = findViewById(R.id.list_view_favoritos)

        novelasFavoritas = mutableListOf()
        adapter = NovelaAdapter(this, novelasFavoritas)
        listViewFavoritos.adapter = adapter

        btnVolver = findViewById(R.id.btn_volver)
        btnVolver.setOnClickListener { finish() }

        btnCargarFavoritos = findViewById(R.id.btn_cargar_favoritos)
        btnCargarFavoritos.setOnClickListener { cargarNovelasFavoritas() }

        firebaseHelper = FirebaseConfig()
    }

    private fun cargarNovelasFavoritas() {
        firebaseHelper.cargarTodasLasNovelas(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                novelasFavoritas.clear()
                for (novelaSnapshot in snapshot.children) {
                    val novela = novelaSnapshot.getValue(Novela::class.java)
                    if (novela?.favorito == true) {
                        novelasFavoritas.add(novela)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error si es necesario
            }
        })
    }
}