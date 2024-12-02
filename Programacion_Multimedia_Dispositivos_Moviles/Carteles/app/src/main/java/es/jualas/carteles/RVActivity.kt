package es.jualas.carteles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RVActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.toolbar_title)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Lista de ejemplos de ítems de carteles de películas
        val items = listOf(
            Item("Película A", 1950, "70x100 cm", "Excelente", "Ilustrador A", "EE.UU.", 500.0),
            Item("Película B", 1960, "60x90 cm", "Bueno", "Ilustrador B", "Reino Unido", 350.0)
        )

        val adapter = ItemAdapter(items)
        recyclerView.adapter = adapter
    }
}