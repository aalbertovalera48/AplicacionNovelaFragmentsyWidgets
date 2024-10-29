package com.example.aplicacionnovela.ui.theme

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
import com.example.gestionnovelasavanzado.ui.GestionSegundoPlano.FirebaseConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var novelas: MutableList<Novela>
    private lateinit var adapter: NovelaAdapter
    private lateinit var firebaseHelper: FirebaseConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        // Vemos si el usuario esta logueado
        val currentUser = auth.currentUser
        if (currentUser == null) {
            // Si no esta logueado lo mandamos a la pantalla de login
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        novelas = mutableListOf()
        val listView: ListView = findViewById(R.id.list_view_novelas)
        adapter = NovelaAdapter(this, novelas)
        listView.adapter = adapter

        firebaseHelper = FirebaseConfig()

        findViewById<View>(R.id.btn_agregar).setOnClickListener { mostrarDialogoAñadirNovela() }
        findViewById<View>(R.id.btn_eliminar).setOnClickListener { mostrarDialogoEliminarNovela() }
        findViewById<View>(R.id.btn_favoritos).setOnClickListener {
            val intent = Intent(this@MainActivity, FavoritosActivity::class.java)
            intent.putParcelableArrayListExtra("novelas_favoritas", obtenerNovelasFavoritas())
            startActivity(intent)
        }
        findViewById<View>(R.id.btn_buscar).setOnClickListener { mostrarDialogoBuscarNovela() }
        val syncButton: ImageButton = findViewById(R.id.sync_button)
        syncButton.setOnClickListener {
            val syncTask = SyncTask(this, novelas, adapter)
            syncTask.execute()
        }

        findViewById<View>(R.id.settings_button).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        AlarmManager.manageSync(this)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        firebaseHelper.cargarTodasLasNovelas(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                novelas.clear()
                for (novelaSnapshot in snapshot.children) {
                    val novela = novelaSnapshot.getValue(Novela::class.java)
                    novela?.let { novelas.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error al cargar novelas: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarDialogoAñadirReseña(novela: Novela) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val view: View = inflater.inflate(R.layout.agregar_resena, null)
        val editTextReseña: EditText = view.findViewById(R.id.editTextResenaAnadir)

        builder.setView(view)
            .setTitle("Añadir Reseña")
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Añadir") { _, _ ->
                val reseña = editTextReseña.text.toString()
                añadirReseñaNovela(novela, reseña)
            }
        builder.create().show()
    }

    private fun añadirReseñaNovela(novela: Novela, reseña: String) {
        val updatedReseñas = novela.reseñas.toMutableList().apply { add(reseña) }
        novela.reseñas = updatedReseñas
        firebaseHelper.databaseReference.child(novela.id!!).setValue(novela)
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "Reseña añadida", Toast.LENGTH_SHORT).show()
    }
    private fun mostrarDialogoAñadirNovela() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val view: View = inflater.inflate(R.layout.agregar_novela, null)
        val editTextTitulo: EditText = view.findViewById(R.id.editTextTituloAñadir)
        val editTextAutor: EditText = view.findViewById(R.id.editTextAutorAñadir)
        val editTextAnio: EditText = view.findViewById(R.id.editTextAnioAñadir)
        val editTextSinopsis: EditText = view.findViewById(R.id.editTextSinopsisAñadir)

        builder.setView(view)
            .setTitle("Añadir Novela a la Lista")
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Añadir") { _, _ ->
                val titulo = editTextTitulo.text.toString()
                val autor = editTextAutor.text.toString()
                val añoPublicacion = editTextAnio.text.toString().toIntOrNull() ?: 0
                val sinopsis = editTextSinopsis.text.toString()
                añadirNovelaLista(titulo, autor, añoPublicacion, sinopsis)
            }
        builder.create().show()
    }

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

    private fun buscarNovelaLista(title: String) {
        val encontrada = novelas.any { it.titulo.equals(title, ignoreCase = true) }
        if (encontrada) {
            Toast.makeText(this, "Novela encontrada: $title", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Novela no encontrada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun añadirNovelaLista(
        title: String,
        autor: String,
        añoPublicacion: Int,
        sinopsis: String
    ) {
        if (novelas.any { it.titulo.equals(title, ignoreCase = true) }) {
            Toast.makeText(this, "La novela ya está en la lista", Toast.LENGTH_SHORT).show()
            return
        }

        val nuevaNovela = Novela(
            id = null,
            titulo = title,
            autor = autor,
            añoPublicacion = añoPublicacion,
            sinopsis = sinopsis,
            favorito = false
        )

        firebaseHelper.agregarNovela(nuevaNovela)
        novelas.add(nuevaNovela)
        adapter.notifyDataSetChanged()
        Toast.makeText(this, "Novela añadida a la lista", Toast.LENGTH_SHORT).show()
    }

    private fun eliminarNovelaLista(titulo: String) {
        firebaseHelper.eliminarNovela(titulo, object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (novelaSnapshot in snapshot.children) {
                        val novela = novelaSnapshot.getValue(Novela::class.java)
                        if (novela != null) {
                            firebaseHelper.databaseReference.child(novela.id!!).removeValue()
                            novelas.remove(novela)
                            adapter.notifyDataSetChanged()
                            Toast.makeText(this@MainActivity, "Novela eliminada de la lista", Toast.LENGTH_SHORT).show()
                            return
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Novela no encontrada", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error al eliminar novela: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun mostrarDetallesNovela(novela: Novela) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val view: View = inflater.inflate(R.layout.detalles_novela, null)

        val textViewTitulo: TextView = view.findViewById(R.id.textViewTitulo)
        val textViewAutor: TextView = view.findViewById(R.id.textViewAutor)
        val textViewAnio: TextView = view.findViewById(R.id.textViewAño)
        val textViewSinopsis: TextView = view.findViewById(R.id.textViewSinopsis)
        val textViewReseñas: TextView = view.findViewById(R.id.textViewReseñas)

        textViewTitulo.text = novela.titulo
        textViewAutor.text = novela.autor
        textViewAnio.text = "Año: ${novela.añoPublicacion}"
        textViewSinopsis.text = novela.sinopsis

        val reseñasText = if (novela.reseñas.isEmpty()) {
            "No reviews yet."
        } else {
            novela.reseñas.joinToString(separator = "\n\n") { "Reseña: - $it" }
        }
        textViewReseñas.text = reseñasText

        builder.setView(view)
            .setTitle("Detalles de la Novela")
            .setPositiveButton("Cerrar", null)
            .setNeutralButton(if (novela.favorito) "Eliminar de Favoritos" else "Añadir a Favoritos") { _, _ ->
                novela.favorito = !novela.favorito
                Toast.makeText(this, if (novela.favorito) "Añadida a favoritos" else "Eliminada de favoritos", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Añadir Reseña") { _, _ ->
                mostrarDialogoAñadirReseña(novela)
            }
        builder.create().show()
    }

    fun obtenerNovelasFavoritas(): ArrayList<Novela> {
        return ArrayList(novelas.filter { it.favorito })
    }
}