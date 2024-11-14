package com.example.aplicacionnovela.ui.theme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.aplicacionnovela.R
import com.example.aplicacionnovela.ui.theme.Novela

class NovelaDetailsFragment : Fragment() {

    private lateinit var novela: Novela

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_novela_details, container, false)
        // Initialize views and set novela details
        val textViewTitulo: TextView = view.findViewById(R.id.textViewTitulo)
        val textViewAutor: TextView = view.findViewById(R.id.textViewAutor)
        val textViewAño: TextView = view.findViewById(R.id.textViewAño)
        val textViewSinopsis: TextView = view.findViewById(R.id.textViewSinopsis)
        val textViewReseñas: TextView = view.findViewById(R.id.textViewReseñas)

        textViewTitulo.text = "Título: ${novela.titulo}"
        textViewAutor.text = "Autor: ${novela.autor}"
        textViewAño.text = "Año: ${novela.añoPublicacion}"
        textViewSinopsis.text = "Sinopsis: ${novela.sinopsis}"
        textViewReseñas.text = "Reseñas: ${novela.reseñas.joinToString(", ")}"

        return view
    }

    fun setNovela(novela: Novela) {
        this.novela = novela
    }
}