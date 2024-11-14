package com.example.aplicacionnovela.ui.theme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplicacionnovela.R
import com.example.gestionnovelasavanzado.ui.GestionSegundoPlano.FirebaseConfig
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class NovelasListFragment : Fragment() {

    private lateinit var adapter: NovelaRecyclerViewAdapter
    private lateinit var novelas: MutableList<Novela>
    private lateinit var firebaseHelper: FirebaseConfig

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_novelas_list, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_novelas)
        recyclerView.layoutManager = LinearLayoutManager(context)
        novelas = mutableListOf()
        adapter = NovelaRecyclerViewAdapter(requireContext(), novelas)
        recyclerView.adapter = adapter

        firebaseHelper = FirebaseConfig()
        cargarNovelasDesdeFirebase()

        return view
    }

    private fun cargarNovelasDesdeFirebase() {
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
                // Manejar el error si es necesario
            }
        })
    }

    fun updateNovelas(novelas: List<Novela>) {
        this.novelas.clear()
        this.novelas.addAll(novelas)
        adapter.notifyDataSetChanged()
    }
}