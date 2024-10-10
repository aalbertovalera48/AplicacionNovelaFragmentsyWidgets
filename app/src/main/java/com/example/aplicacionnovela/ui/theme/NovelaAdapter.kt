package com.example.aplicacionnovela.ui.theme
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.aplicacionnovela.R
import com.example.gestionnovelasavanzado.ui.Activities.MainActivity


// Clase NovelaAdapter que extiende BaseAdapter y se utiliza para mostrar la lista de novelas
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

    // Método para inflar la vista de cada elemento de la lista
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_novela, parent, false)

        val novela = novelas[position]
        val textViewTitulo: TextView = view.findViewById(R.id.textViewTitulo)
        val textViewAutor: TextView = view.findViewById(R.id.textViewAutor)

        textViewTitulo.text = novela.titulo
        textViewAutor.text = novela.autor

        // Añadir un evento al clic de la novela
        view.setOnClickListener {
            if (context is MainActivity) {
                context.mostrarDetallesNovela(novela) // Llama a mostrarDetallesNovela
            }
        }

        return view
    }
}

