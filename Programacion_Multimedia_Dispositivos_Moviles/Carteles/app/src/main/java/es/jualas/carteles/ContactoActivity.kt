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
    private lateinit var binding: ActivityContactoBinding
    private val CALL_PERMISSION_REQUEST_CODE = 1
    private val LOCATION_PERMISSION_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactoBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    private fun checkCallPermissionAndMakeCall() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), CALL_PERMISSION_REQUEST_CODE)
        } else {
            makePhoneCall()
        }
    }

    private fun makePhoneCall() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${getString(R.string.contact_phone)}"))
        startActivity(intent)
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${getString(R.string.contact_email)}")
            putExtra(Intent.EXTRA_SUBJECT, "Consulta desde la app")
        }
        startActivity(intent)
    }

    private fun checkLocationPermissionAndOpenMap() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else {
            openMap()
        }
    }

    private fun openMap() {
        val address = getString(R.string.contact_address)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$address"))
        startActivity(intent)
    }

    // Función para abrir WhatsApp (necesitarás añadir un botón para esto en tu layout)
    private fun openWhatsApp() {
    val phoneNumber = getString(R.string.contact_phone)
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber")
    try {
        startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(this, "WhatsApp no está instalado en este dispositivo", Toast.LENGTH_SHORT).show()
    }
}

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