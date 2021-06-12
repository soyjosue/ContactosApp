package com.example.contactos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import org.w3c.dom.Text

class Detalle : AppCompatActivity() {

    var index:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        index = intent.getStringExtra("ID").toInt()
        // Log.d("INDEX", index.toString())

        mapearDatos()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalle, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.iEditar -> {
                val intent = Intent(this, Nuevo::class.java)
                intent.putExtra("ID", index.toString())
                startActivity(intent)
                return true
            }

            R.id.iEliminar ->{
                MainActivity.eliminarContacto(index)
                finish()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        mapearDatos()
    }

    fun mapearDatos() {
        val contacto:Contacto = MainActivity.obtenerContacto(index)

        var ivFoto = findViewById<ImageView>(R.id.ivFoto)
        var tvNombre = findViewById<TextView>(R.id.tvNombre)
        var tvEmpresa = findViewById<TextView>(R.id.tvEmpresa)
        var tvEdad = findViewById<TextView>(R.id.tvEdad)
        var tvPeso = findViewById<TextView>(R.id.tvPeso)
        var tvTelefono = findViewById<TextView>(R.id.tvTelefono)
        var tvEmail = findViewById<TextView>(R.id.tvEmail)
        var tvDireccion = findViewById<TextView>(R.id.tvDireccion)

        ivFoto.setImageResource(contacto.foto)
        tvNombre.text = "${contacto.nombre} ${contacto.apellidos}"
        tvEmpresa.text = contacto.empresa
        tvEdad.text = "${contacto.edad.toString()} años"
        tvPeso.text = "${contacto.peso.toString()} kg"
        tvTelefono.text = contacto.telefono
        tvEmail.text = contacto.email
        tvDireccion.text = contacto.direccion
    }
}