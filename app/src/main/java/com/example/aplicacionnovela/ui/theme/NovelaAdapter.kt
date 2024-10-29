package com.example.aplicacionnovela.ui.theme
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.aplicacionnovela.R


class NovelaAdapter(private val context: Context, private val novelas: List<Novela>) : BaseAdapter() {

    override fun getCount(): Int {
        return novelas.size
    }

    override fun getItem(position: Int): Any {
        return novelas[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_novela, parent, false)

        val novela = novelas[position]
        val textViewTitulo: TextView = view.findViewById(R.id.textViewTitulo)
        val textViewAutor: TextView = view.findViewById(R.id.textViewAutor)

        textViewTitulo.text = novela.titulo
        textViewAutor.text = novela.autor

        view.setOnClickListener {
            if (context is MainActivity) {
                context.mostrarDetallesNovela(novela)
            }
        }

        return view
    }
}

