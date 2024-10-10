package com.example.gestionnovelasavanzado.ui.Activities

import SyncTask
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aplicacionnovela.R
import com.example.aplicacionnovela.ui.theme.AlarmManager
import com.example.aplicacionnovela.ui.theme.Novela
import com.example.aplicacionnovela.ui.theme.NovelaAdapter
import com.example.gestionnovelasavanzado.ui.GestionSegundoPlano.FirebaseConfig
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    // Variables
    private lateinit var novelas: MutableList<Novela>
    private lateinit var adapter: NovelaAdapter
    private lateinit var firebaseHelper: FirebaseConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar listas
        novelas = mutableListOf()

        // Configurar la lista de novelas y el adaptador
        val listView: ListView = findViewById(R.id.list_view_novelas)
        adapter = NovelaAdapter(this, novelas)
        listView.adapter = adapter

        // Inicializar FirebaseHelper
        firebaseHelper = FirebaseConfig()

        // Configurar los botones
        findViewById<View>(R.id.btn_agregar).setOnClickListener { mostrarDialogoAñadirNovela() }
        findViewById<View>(R.id.btn_eliminar).setOnClickListener { mostrarDialogoEliminarNovela() }

        // Botón para ir a la actividad favoritos
        findViewById<View>(R.id.btn_favoritos).setOnClickListener {
            val intent = Intent(this@MainActivity, FavoritosActivity::class.java)
            intent.putParcelableArrayListExtra("novelas_favoritas", obtenerNovelasFavoritas())
            startActivity(intent)
        }

        // Botón para buscar novela
        findViewById<View>(R.id.btn_buscar).setOnClickListener { mostrarDialogoBuscarNovela() }

        // Botón para sincronizar la lista de novelas con Firebase
        val syncButton: ImageButton = findViewById(R.id.sync_button)
        syncButton.setOnClickListener {
            val syncTask = SyncTask(this, novelas)
            syncTask.execute() // Cambiar aquí para usar execute()
        }

        // Sincronización automática con AlarmManager cada 2 minutos
        AlarmManager.manageSync(this)
    }

    override fun onResume() {
        super.onResume()
        // Actualizar la lista de novelas
        val novelasCargadas = firebaseHelper.obtenerNovelas()
        novelas.clear()
        novelas.addAll(novelasCargadas)
        adapter.notifyDataSetChanged()
    }


    // Método para mostrar el diálogo para añadir novela a la lista
    private fun mostrarDialogoAñadirNovela() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val view: View = inflater.inflate(R.layout.agregar_novela, null)
        val editTextTitle: EditText = view.findViewById(R.id.editTextTituloAñadir)

        builder.setView(view)
            .setTitle("Añadir Novela a la Lista")
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Añadir") { _, _ ->
                val title = editTextTitle.text.toString()
                añadirNovelaLista(title)
            }
        builder.create().show()
    }

    // Método para mostrar el diálogo para eliminar novela de la lista
    private fun mostrarDialogoEliminarNovela() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val view: View = inflater.inflate(R.layout.eliminar_novela, null)
        val editTextTitle: EditText = view.findViewById(R.id.editTextTituloEliminar)

        builder.setView(view)
            .setTitle("Eliminar Novela de la Lista")
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Eliminar") { _, _ ->
                val title = editTextTitle.text.toString()
                eliminarNovelaLista(title)
            }
        builder.create().show()
    }

    // Método para mostrar el diálogo para buscar novela en la lista
    private fun mostrarDialogoBuscarNovela() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val view: View = inflater.inflate(R.layout.buscar_novela, null)
        val editTextTitle: EditText = view.findViewById(R.id.editTextTituloBuscar)

        builder.setView(view)
            .setTitle("Buscar Novela en la Lista")
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Buscar") { _, _ ->
                val title = editTextTitle.text.toString()
                buscarNovelaLista(title)
            }
        builder.create().show()
    }

    // Método que busca la novela en la lista local
    private fun buscarNovelaLista(title: String) {
        val encontrada = novelas.any { it.titulo.equals(title, ignoreCase = true) }
        if (encontrada) {
            Toast.makeText(this, "Novela encontrada: $title", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Novela no encontrada", Toast.LENGTH_SHORT).show()
        }
    }

    // Método que busca la novela en Firebase y la añade solo a la lista
    private fun añadirNovelaLista(title: String) {
        // Verificar primero si la novela ya está en la lista antes de consultar Firebase
        if (novelas.any { it.titulo.equals(title, ignoreCase = true) }) {
            Toast.makeText(this, "La novela ya está en la lista", Toast.LENGTH_SHORT).show()
            return
        }

        // Si no está en la lista, buscar en Firebase
        firebaseHelper.cargarNovelas(title, object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Si la novela existe en Firebase la añadimos a la lista
                if (snapshot.exists()) {
                    for (novelaSnapshot in snapshot.children) {
                        val novela = novelaSnapshot.getValue(Novela::class.java)
                        if (novela != null) {
                            novelas.add(novela)
                            adapter.notifyDataSetChanged()
                            Toast.makeText(
                                this@MainActivity,
                                "Novela añadida a la lista",
                                Toast.LENGTH_SHORT
                            ).show()
                            return
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Novela no encontrada", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@MainActivity,
                    "Error al buscar novela: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    // Método para eliminar novela de la lista local
    private fun eliminarNovelaLista(titulo: String) {
        val encontrado = novelas.removeIf { it.titulo.equals(titulo, ignoreCase = true) }
        if (encontrado) {
            Toast.makeText(this, "Novela eliminada de la lista", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Novela no encontrada", Toast.LENGTH_SHORT).show()
        }
        adapter.notifyDataSetChanged()
    }

    // Método para mostrar los detalles de la novela seleccionada
    fun mostrarDetallesNovela(novela: Novela) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val view: View = inflater.inflate(R.layout.detalles_novela, null)

        // Enlazar los elementos de la vista con los atributos de la novela
        val textViewTitulo: TextView = view.findViewById(R.id.textViewTitulo)
        val textViewAutor: TextView = view.findViewById(R.id.textViewAutor)
        val textViewAnio: TextView = view.findViewById(R.id.textViewAño)
        val textViewSinopsis: TextView = view.findViewById(R.id.textViewSinopsis)

        // Mostrar los detalles de la novela
        textViewTitulo.text = novela.titulo
        textViewAutor.text = novela.autor
        textViewAnio.text = "Año: ${novela.añoPublicacion}"
        textViewSinopsis.text = novela.sinopsis

        builder.setView(view)
            .setTitle("Detalles de la Novela")
            .setPositiveButton("Cerrar", null)
            .setNeutralButton(if (novela.favorito) "Eliminar de Favoritos" else "Añadir a Favoritos") { _, _ ->
                novela.favorito = !novela.favorito
                Toast.makeText(
                    this,
                    if (novela.favorito) "Añadida a favoritos" else "Eliminada de favoritos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        builder.create().show()
    }

    // Método para obtener las novelas favoritas
    fun obtenerNovelasFavoritas(): ArrayList<Novela> {
        return ArrayList(novelas.filter { it.favorito })
    }
}
