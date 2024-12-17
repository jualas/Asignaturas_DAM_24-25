package es.jualas.carteles

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import es.jualas.carteles.databinding.ActivityContactoBinding

class ContactoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactoBinding  // Instancia del objeto Binding con acceso a las vistas en el layout activity_contacto.xml
    private val CALL_PERMISSION_REQUEST_CODE = 1 // Código de solicitud para el permiso de llamada
    private val LOCATION_PERMISSION_REQUEST_CODE = 2 // Código de solicitud para el permiso de ubicación

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflar el layout usando ViewBinding
        binding = ActivityContactoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar los listeners para los botones
        setupListeners()
    }

    private fun setupListeners() {
        // Botón de llamada
        binding.callIcon.setOnClickListener { checkCallPermissionAndMakeCall() }

        // Botón de email
        binding.emailIcon.setOnClickListener { sendEmail() }

        // Botón de mapa
        binding.mapIcon.setOnClickListener { checkLocationPermissionAndOpenMap() }

        // Añadir un nuevo botón para WhatsApp en tu layout y configurar su listener aquí
        binding.whatsappIcon.setOnClickListener { openWhatsApp() }
    }

    // Verifica si se tiene permiso para realizar llamadas y, si no, lo solicita
    private fun checkCallPermissionAndMakeCall() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                CALL_PERMISSION_REQUEST_CODE
            )
        } else {
            makePhoneCall()
        }
    }

    // Realiza una llamada telefónica al número de contacto
    private fun makePhoneCall() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${getString(R.string.contact_phone)}"))
        startActivity(intent)
    }

    // Envía un correo electrónico al email de contacto
    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${getString(R.string.contact_email)}")
            putExtra(Intent.EXTRA_SUBJECT, "Consulta desde la app")
        }
        startActivity(intent)
    }

    // Verifica si se tiene permiso para acceder a la ubicación y, si no, lo solicita
    private fun checkLocationPermissionAndOpenMap() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            openMap()
        }
    }

    // Abre la aplicación de mapas con la dirección de contacto
    private fun openMap() {
        val address = getString(R.string.contact_address)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$address"))
        startActivity(intent)
    }

    // Abre WhatsApp con el número de contacto
    private fun openWhatsApp() {
        val phoneNumber = getString(R.string.contact_phone)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber")
        try {
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "WhatsApp no está instalado en este dispositivo", Toast.LENGTH_SHORT).show()
        }
    }// Maneja los resultados de las solicitudes de permisos

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CALL_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    makePhoneCall()
                } else {
                    Toast.makeText(this, "Permiso de llamada denegado", Toast.LENGTH_SHORT).show()
                }
            }

            LOCATION_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    openMap()
                } else {
                    Toast.makeText(this, "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}