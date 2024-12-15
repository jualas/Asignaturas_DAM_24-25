package es.jualas.carteles

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // En Android 12 o superior, se recomienda usar el nuevo sistema SplashScreen API
            setTheme(R.style.Theme_Carteles)
            navigateToNextActivity()
        } else {
            // Para versiones anteriores, muestra el SplashScreen tradicional
            setContentView(R.layout.activity_splash_screen)
            Handler(Looper.getMainLooper()).postDelayed({
                navigateToNextActivity()
            }, 3000) // 3 segundos
        }
    }

    private fun navigateToNextActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
