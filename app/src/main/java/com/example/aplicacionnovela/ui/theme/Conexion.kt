import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast

// Clase ConexionReceiver que hereda de BroadcastReceiver y que detecta la conexión
class Conexion : BroadcastReceiver() {

    // Método onReceive para detectar la conexión y avisar mediante un mensaje
    override fun onReceive(context: Context, intent: Intent) {
        // Verificar si hay conexión
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isConnected = isNetworkConnected(connectivityManager)

        // Si hay conexión se inicia la sincronización
        if (isConnected) {
            Toast.makeText(context, "Conexión detectada, iniciando sincronización...", Toast.LENGTH_SHORT).show()
            // Iniciar la sincronización
            SyncTask(context, mutableListOf()).execute() // Pasa una lista vacía o inicialízala según tus necesidades
        } else {
            Toast.makeText(context, "No hay conexión a Internet.", Toast.LENGTH_SHORT).show()
        }
    }

    // Método para verificar la conexión a la red
    private fun isNetworkConnected(connectivityManager: ConnectivityManager): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}
