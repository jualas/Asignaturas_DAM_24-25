package es.jualas.carteles

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import es.jualas.carteles.databinding.ActivityLoginBinding
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    // Declaración de la variable para View Binding
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar el layout usando View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        // Establecer el contenido de la vista
        setContentView(view)

        // Lista de botones a los que se les añadirá el OnClickListener
        val buttons = listOf(
            binding.button1,
            binding.button2,
            binding.button3,
            binding.buttonfacebook,
            binding.signInButton
        )

        // Asignar OnClickListener a cada botón para mostrar un SnackBar
        buttons.forEach { button ->
            button.setOnClickListener {
                val buttonText = (it as Button).text
                Snackbar.make(view, "Has marcado el botón: $buttonText", Snackbar.LENGTH_SHORT).show()
            }
        }

        // Encontrar el ShapeableImageView por su ID
        val logoImageView: ShapeableImageView = findViewById(R.id.imageView)
        // Configurar el listener para navegar a RVActivity
        logoImageView.setOnClickListener {
            // Intent para navegar a RVActivity
            val intent = Intent(this, RVActivity::class.java)
            startActivity(intent) // Iniciar la actividad RVActivity
        }
    }
}