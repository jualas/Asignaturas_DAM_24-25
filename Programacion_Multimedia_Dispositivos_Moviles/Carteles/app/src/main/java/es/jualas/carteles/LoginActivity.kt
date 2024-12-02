package es.jualas.carteles

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import es.jualas.carteles.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el layout
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verificar si estamos en orientación horizontal y el ScrollView existe
        val scrollView = binding.root.findViewById<ScrollView?>(R.id.scrollViewMain)
        scrollView?.isSaveEnabled = false

        // Configuración de vistas y listeners según el diseño actual
        configureViews()

        // Agregar OnClickListener a la imagen
        val imageView: ImageView = findViewById(R.id.imageView)
        imageView.setOnClickListener {
            val intent = Intent(this, RVActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configureViews() {
        // Lógica común para ambas orientaciones
        // Binding mensajes al hacer clic en los botones
        binding.registerButton.setOnClickListener {
            showSnackBar(it, "Bienvenido")
        }

        // Binding Olvidó la contraseña
        binding.forgotPasswordButton.setOnClickListener {
            showSnackBar(it, "Olvidó la contraseña")
        }

        // iniciar sesión
        binding.loginButton.setOnClickListener {
            val username = binding.usernameInputLayout.editText?.text.toString()
            val password = binding.passwordInputLayout.editText?.text.toString()

            if (username.isEmpty()) {
                binding.usernameInputLayout.error = "El nombre de usuario no puede estar vacío"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.passwordInputLayout.error = "La contraseña no puede estar vacía"
                return@setOnClickListener
            }

            if (validateCredentials(username, password)) {
                // Inicio de sesión exitoso
                Snackbar.make(it, "Iniciando sesión...", Snackbar.LENGTH_SHORT).show()

                // Navegar a ContactoActivity
                val intent = Intent(this, ContactoActivity::class.java)
                startActivity(intent)
                finish() // Opcional: cierra LoginActivity si no quieres que el usuario pueda volver atrás
            } else {
                // Inicio de sesión fallido
                Snackbar.make(it, "Credenciales incorrectas", Snackbar.LENGTH_SHORT).show()
            }
        }

        // Binding Iniciar sesión con Facebook
        binding.buttonfacebook.setOnClickListener {
            showSnackBar(it, "Iniciar sesión con Facebook")
        }

        // Binding Iniciar sesión con Google
        binding.signGoogle.setOnClickListener {
            showSnackBar(it, "Iniciar sesión con Google")
        }

        // Lógica específica para orientación horizontal
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            configureLandscapeViews()
        } else {
            configurePortraitViews()
        }
    }

    // Método para validar las credenciales del usuario opcion provisional
    private fun validateCredentials(username: String, password: String): Boolean {
        return username == "admin" && password == "admin"
    }

    private fun configureLandscapeViews() {
        // Lógica específica para orientación horizontal
        Log.d("LoginActivity", "Configuración en orientación horizontal")
    }

    private fun configurePortraitViews() {
        // Lógica específica para orientación vertical
        Log.d("LoginActivity", "Configuración en orientación vertical")
    }

    // Método para mostrar un SnackBar
    private fun showSnackBar(view: android.view.View, buttonText: String) {
        Snackbar.make(view, "Has marcado el botón: $buttonText", Snackbar.LENGTH_SHORT).show()
    }
}