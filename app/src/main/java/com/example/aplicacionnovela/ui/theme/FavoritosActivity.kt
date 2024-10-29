package com.example.aplicacionnovela.ui.theme

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionnovela.R

class FavoritosActivity : AppCompatActivity() {

    private lateinit var novelasFavoritas: List<Novela>
    private lateinit var adapter: NovelaAdapter
    private lateinit var btnVolver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favoritos_activity)

        val listViewFavoritos: ListView = findViewById(R.id.list_view_favoritos)

        novelasFavoritas = intent.getParcelableArrayListExtra("novelas_favoritas") ?: emptyList()

        adapter = NovelaAdapter(this, novelasFavoritas)
        listViewFavoritos.adapter = adapter

        btnVolver = findViewById(R.id.btn_volver)
        btnVolver.setOnClickListener { finish() }
    }
}