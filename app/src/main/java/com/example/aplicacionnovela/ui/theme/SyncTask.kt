import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.aplicacionnovela.ui.theme.Novela
import com.example.gestionnovelasavanzado.ui.GestionSegundoPlano.FirebaseConfig
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class SyncTask(
    private val context: Context,
    private val novelas: MutableList<Novela>
) : AsyncTask<Void, Void, Boolean>() {

    private val firebaseHelper = FirebaseConfig()

    override fun doInBackground(vararg params: Void?): Boolean {
        try {
            // Simulación de carga de datos
            firebaseHelper.cargarTodasLasNovelas(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    novelas.clear()
                    for (novelaSnapshot in snapshot.children) {
                        val novela = novelaSnapshot.getValue(Novela::class.java)
                        novela?.let { novelas.add(it) }
                    }
                    onPostExecute(true) // Llama a onPostExecute manualmente
                }

                override fun onCancelled(error: DatabaseError) {
                    onPostExecute(false)
                }
            })

            Thread.sleep(2000) // Ajustar el tiempo de espera según sea necesario

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    override fun onPostExecute(result: Boolean) {
        if (result) {
            Toast.makeText(context, "Sincronización completa", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Error en la sincronización", Toast.LENGTH_SHORT).show()
        }
    }
}
