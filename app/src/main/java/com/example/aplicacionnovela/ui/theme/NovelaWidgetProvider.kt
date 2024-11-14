package com.example.aplicacionnovela.ui.theme

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import com.example.aplicacionnovela.R
import com.example.gestionnovelasavanzado.ui.GestionSegundoPlano.FirebaseConfig
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class NovelaWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntentFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, pendingIntentFlag)

        val views = RemoteViews(context.packageName, R.layout.widget_novela)
        views.setOnClickPendingIntent(R.id.widget_title, pendingIntent)

        val firebaseHelper = FirebaseConfig()
        firebaseHelper.cargarTodasLasNovelas(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favoritos = mutableListOf<Novela>()
                for (novelaSnapshot in snapshot.children) {
                    val novela = novelaSnapshot.getValue(Novela::class.java)
                    if (novela?.favorito == true) {
                        favoritos.add(novela)
                    }
                }

                val favoritosText = favoritos.joinToString(separator = "\n") { it.titulo }
                views.setTextViewText(R.id.widget_title, favoritosText)
                appWidgetManager.updateAppWidget(appWidgetId, views)
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error si es necesario
            }
        })
    }
}