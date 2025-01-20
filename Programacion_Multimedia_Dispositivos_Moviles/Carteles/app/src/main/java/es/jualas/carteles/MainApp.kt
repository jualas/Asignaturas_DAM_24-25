package es.jualas.carteles

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
class MainApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Restaura el tema principal
        setTheme(R.style.Theme_Carteles)
        super.onCreate(savedInstanceState)

        // Lógica para decidir qué actividad mostrar
        val isUserLoggedIn = checkUserLoggedIn()

        if (isUserLoggedIn) {
            startActivity(Intent(this, ContactoActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        finish() // Cierra MainApp
    }

    private fun checkUserLoggedIn(): Boolean {
        // Implementa la lógica para verificar si el usuario ya está autenticado
        return false
    }
}