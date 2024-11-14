package com.example.aplicacionnovela.ui.theme

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionnovela.R

class NovelaRecyclerViewAdapter(
    private val context: Context,
    private val novelas: List<Novela>
) : RecyclerView.Adapter<NovelaRecyclerViewAdapter.NovelaViewHolder>() {

    inner class NovelaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitulo: TextView = view.findViewById(R.id.textViewTitulo)
        val textViewAutor: TextView = view.findViewById(R.id.textViewAutor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NovelaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_novela, parent, false)
        return NovelaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NovelaViewHolder, position: Int) {
        val novela = novelas[position]
        holder.textViewTitulo.text = novela.titulo
        holder.textViewAutor.text = novela.autor

        holder.itemView.setOnClickListener {
            if (context is MainActivity) {
                context.mostrarDetallesNovela(novela)
            }
        }
    }

    override fun getItemCount(): Int {
        return novelas.size
    }
}