package es.jualas.carteles

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen") // Para evitar que se muestre la barra de estado durante el splash screen
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Usar un Handler para retrasar la ejecución de un bloque de código
        Handler(Looper.getMainLooper()).postDelayed({
            // Crear un Intent para iniciar LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            // Finalizar SplashScreenActivity para que no se quede en el stack de actividades
            finish()
        }, 3000) // 3 segundos de retraso
    }
}