package com.example.ut4_ead

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        // Mostrar el SplashScreen por 3 segundos antes de redirigir al MainActivity
        navigateToMainActivityWithDelay()
    }

    private fun navigateToMainActivityWithDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish() // Finaliza el SplashScreen para evitar que el usuario regrese a él
        }, 3000) // 3000 ms = 3 segundos
    }
}
