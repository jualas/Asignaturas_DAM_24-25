package es.jualas.carteles

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lógica para decidir qué actividad mostrar
        val isUserLoggedIn = checkUserLoggedIn()

        if (isUserLoggedIn) {
            // Si el usuario ya está autenticado, navega a la actividad principal
            startActivity(Intent(this, ContactoActivity::class.java))
        } else {
            // Si el usuario no está autenticado, muestra la actividad de inicio de sesión
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // Finaliza MainApp para que no se quede en el stack de actividades
        finish()
    }

    private fun checkUserLoggedIn(): Boolean {
        // Implementa la lógica para verificar si el usuario ya está autenticado
        return false
    }
}