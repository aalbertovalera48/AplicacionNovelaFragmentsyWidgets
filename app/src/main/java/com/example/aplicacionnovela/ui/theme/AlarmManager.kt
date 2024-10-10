package com.example.aplicacionnovela.ui.theme

import Conexion
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.Calendar

// Clase AlarmManagerUtil para programar tareas periódicas de sincronización
object AlarmManager {

    // Método para programar la tarea de sincronización con AlarmManager
    fun manageSync(context: Context) {
        val intent = Intent(context, Conexion::class.java)

        // Establecer el flag de PendingIntent de acuerdo a la versión de Android
        val pendingIntentFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        // Crear un PendingIntent para la tarea de sincronización
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, pendingIntentFlag)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val calendar = Calendar.getInstance().apply {
            add(Calendar.MINUTE, 2)
        }

        // Programar la tarea de sincronización para que se repita cada 2 minutos
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, 120000, pendingIntent)
    }
}

