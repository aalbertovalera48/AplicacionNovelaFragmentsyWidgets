import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.aplicacionnovela.ui.theme.Novela
import com.example.aplicacionnovela.ui.theme.NovelaRecyclerViewAdapter
import com.example.gestionnovelasavanzado.ui.GestionSegundoPlano.FirebaseConfig
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class SyncTask(
    private val context: Context,
    private val novelas: MutableList<Novela>,
    private val adapter: NovelaRecyclerViewAdapter
) : AsyncTask<Void, Void, Boolean>() {

    private val firebaseHelper = FirebaseConfig()

    override fun doInBackground(vararg params: Void?): Boolean {
        var success = false
        val latch = java.util.concurrent.CountDownLatch(1)

        firebaseHelper.cargarTodasLasNovelas(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                novelas.clear()
                for (novelaSnapshot in snapshot.children) {
                    val novela = novelaSnapshot.getValue(Novela::class.java)
                    novela?.let { novelas.add(it) }
                }
                success = true
                latch.countDown()
            }

            override fun onCancelled(error: DatabaseError) {
                success = false
                latch.countDown()
            }
        })

        latch.await()
        return success
    }

    override fun onPostExecute(result: Boolean) {
        if (result) {
            adapter.notifyDataSetChanged()
            Toast.makeText(context, "Synchronization complete", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Synchronization error", Toast.LENGTH_SHORT).show()
        }
    }
}