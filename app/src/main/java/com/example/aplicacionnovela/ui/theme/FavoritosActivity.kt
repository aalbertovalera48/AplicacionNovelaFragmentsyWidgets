package com.example.gestionnovelasavanzado.ui.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionnovela.R
import com.example.aplicacionnovela.ui.theme.Novela
import com.example.aplicacionnovela.ui.theme.NovelaAdapter


class FavoritosActivity : AppCompatActivity() {

    // Variables
    private lateinit var novelasFavoritas: List<Novela>
    private lateinit var adapter: NovelaAdapter
    private lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favoritos_activity)

        // Creación del ListView
        val listViewFavoritos: ListView = findViewById(R.id.list_view_favoritos)

        // Obtener las novelas favoritas de MainActivity a partir del intent
        novelasFavoritas = intent.getParcelableArrayListExtra("novelas_favoritas") ?: emptyList()

        // Crear el adaptador con las novelas favoritas
        adapter = NovelaAdapter(this, novelasFavoritas)
        listViewFavoritos.adapter = adapter

        // Botón para volver al MainActivity
        btnVolver = findViewById(R.id.btn_volver)
        btnVolver.setOnClickListener { finish() }
    }
}
