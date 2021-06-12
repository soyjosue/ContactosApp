 package com.example.contactos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar

class Nuevo : AppCompatActivity() {

    var fotoIndex: Int = 0
    val fotos = arrayOf(R.drawable.foto_01, R.drawable.foto_02, R.drawable.foto_03, R.drawable.foto_04, R.drawable.foto_05, R.drawable.foto_06)

    var foto:ImageView? = null

    var index:Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Habilitar boton retroceso
        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        foto = findViewById(R.id.ivFoto)
        foto?.setImageResource(obtenerFoto(fotoIndex))

        foto?.setOnClickListener{
            seleccionarFoto()
        }

        // Reconocer accion de nuevo vs editar
        if (intent.hasExtra("ID")) {
            index = intent.getStringExtra("ID").toInt()
            rellenarDatos(index)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nuevo, menu)
        if (intent.hasExtra("ID")) {
            menu?.getItem(0)?.setIcon(R.drawable.icono_editar)
        } else {
            menu?.getItem(0)?.setIcon(R.drawable.icono_guardar_contacto)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {

            android.R.id.home -> {
                finish()
                return true
            }

            R.id.iCrearNuevo -> {
                // Crear un nuevo elemento de tipo Contacto
                val nombre = findViewById<EditText>(R.id.tvNombre)
                val apellido = findViewById<EditText>(R.id.etApellido)
                val empresa = findViewById<EditText>(R.id.tvEmpresa)
                val edad = findViewById<EditText>(R.id.tvEdad)
                val peso = findViewById<EditText>(R.id.tvPeso)
                val telefono = findViewById<EditText>(R.id.tvTelefono)
                val email = findViewById<EditText>(R.id.tvEmail)
                val direccion = findViewById<EditText>(R.id.tvDireccion )

                // Validar Campos
                var campos = ArrayList<String>()
                campos.add(nombre.text.toString())
                campos.add(apellido.text.toString())
                campos.add(empresa.text.toString())
                campos.add(edad.text.toString())
                campos.add(peso.text.toString())
                campos.add(telefono.text.toString())
                campos.add(email.text.toString())
                campos.add(direccion.text.toString())

                var flag = 0
                for(campo in campos) { if(campo.isNullOrEmpty())  flag++  }

                if (flag > 0) {
                    Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                } else {
                    if(index > -1) {
                        // Editar Contacto
                        MainActivity.actualizarContacto(index, Contacto(campos.get(0), campos.get(1), campos.get(2), campos.get(3).toInt(), campos.get(4).toFloat(), campos.get(5), campos.get(6), campos.get(7), obtenerFoto(fotoIndex)) )
                    } else {
                        // Agregar Contacto
                        MainActivity.agregarContacto(Contacto(campos.get(0), campos.get(1), campos.get(2), campos.get(3).toInt(), campos.get(4).toFloat(), campos.get(5), campos.get(6), campos.get(7), obtenerFoto(fotoIndex) ) )
                    }
                    finish()
                    Log.d("NO ELEMENTOS", MainActivity.contactos?.count().toString())
                }
                return true
            }
            else -> { return super.onOptionsItemSelected(item) }
        }
    }

    fun seleccionarFoto() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona imagen de perfil")

        val adaptadorDialogo = ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item)
        adaptadorDialogo.add("Foto 01")
        adaptadorDialogo.add("Foto 02")
        adaptadorDialogo.add("Foto 03")
        adaptadorDialogo.add("Foto 04")
        adaptadorDialogo.add("Foto 05")
        adaptadorDialogo.add("Foto 06")

        builder.setAdapter(adaptadorDialogo){
            dialog, which ->
                fotoIndex = which
                foto?.setImageResource(obtenerFoto(fotoIndex))
        }

        builder.setNegativeButton("Cancelar") {
            dialog, which -> dialog.dismiss()
        }
        builder.show()
    }

    fun obtenerFoto(index: Int): Int {
        return fotos.get(index)
    }

    fun rellenarDatos(index:Int) {
        val contacto:Contacto = MainActivity.obtenerContacto(index)

        var ivFoto = findViewById<ImageView>(R.id.ivFoto)
        var tvNombre = findViewById<EditText>(R.id.tvNombre)
        var tvApellido = findViewById<EditText>(R.id.etApellido)
        var tvEmpresa = findViewById<EditText>(R.id.tvEmpresa)
        var tvEdad = findViewById<EditText>(R.id.tvEdad)
        var tvPeso = findViewById<EditText>(R.id.tvPeso)
        var tvTelefono = findViewById<EditText>(R.id.tvTelefono)
        var tvEmail = findViewById<EditText>(R.id.tvEmail)
        var tvDireccion = findViewById<EditText>(R.id.tvDireccion)

        ivFoto.setImageResource(contacto.foto)
        tvNombre.setText(contacto.nombre, TextView.BufferType.EDITABLE)
        tvApellido.setText(contacto.apellidos, TextView.BufferType.EDITABLE)
        tvEmpresa.setText(contacto.empresa)
        tvEdad.setText(contacto.edad.toString())
        tvPeso.setText(contacto.peso.toString())
        tvTelefono.setText(contacto.telefono)
        tvEmail.setText(contacto.email)
        tvDireccion.setText(contacto.direccion)
    }
}