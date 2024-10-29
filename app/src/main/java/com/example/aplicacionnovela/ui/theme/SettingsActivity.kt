package com.example.aplicacionnovela.ui.theme

import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.aplicacionnovela.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var themeSwitch: Switch
    private lateinit var saveButton: Button
    private lateinit var backupButton: Button
    private lateinit var restoreButton: Button
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        setTheme(if (isDarkMode) R.style.AppTheme_Dark else R.style.AppTheme)

        setContentView(R.layout.activity_settings)

        themeSwitch = findViewById(R.id.themeSwitch)
        saveButton = findViewById(R.id.saveButton)
        backupButton = findViewById(R.id.backupButton)
        restoreButton = findViewById(R.id.restoreButton)
        backButton = findViewById(R.id.backButton)

        themeSwitch.isChecked = isDarkMode

        saveButton.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putBoolean("dark_mode", themeSwitch.isChecked)
            editor.apply()

            if (themeSwitch.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

            recreate()
        }

        backupButton.setOnClickListener {
            val data = "Backup data"
            FileHelper.writeToFile(this, "backup.txt", data, true)
        }

        restoreButton.setOnClickListener {
            val data = FileHelper.readFromFile(this, "backup.txt", true)
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}