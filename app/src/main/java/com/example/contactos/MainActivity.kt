package com.example.contactos

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.GridView
import android.widget.ListView
import android.widget.Switch
import android.widget.ViewSwitcher
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    var lista: ListView? = null
    var grid: GridView? = null
    var viewSwitcher: ViewSwitcher? = null


    companion object {
        var adaptador: AdaptadorCustom? = null
        var adaptadorGrid: AdaptadorCustomGrid? = null
        var contactos:ArrayList<Contacto>? = null
        var inGrid: Boolean = false

        fun agregarContacto(contacto:Contacto) {
            adaptador?.addItem(contacto)
            adaptadorGrid?.addItem(contacto)
        }

        fun obtenerContacto(index:Int):Contacto {
            if(this.inGrid) {
                return adaptadorGrid?.getItem(index) as Contacto
            } else {
                return adaptador?.getItem(index) as Contacto
            }
        }

        fun eliminarContacto(index:Int) {
            adaptador?.removeItem(index)
            adaptadorGrid?.removeItem(index)
        }

        fun actualizarContacto(index:Int, contacto:Contacto) {
            adaptador?.updateItem(index, contacto)
            adaptadorGrid?.updateItem(index, contacto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        contactos = ArrayList()
        contactos?.add(Contacto("Marcos", "Rivas", "Contoso", 25, 70.0F, "Tamaulipas 215", "55 1789245", "marcos@contoso.com", R.drawable.foto_01))
        contactos?.add(Contacto("Elvis", "Inoa", "Induveca", 25, 70.0F, "Tamaulipas 215", "55 1789245", "marcos@contoso.com", R.drawable.foto_02))
        contactos?.add(Contacto("Onguito", "Ortiz", "TKL", 25, 70.0F, "Tamaulipas 215", "55 1789245", "marcos@contoso.com", R.drawable.foto_03))
        contactos?.add(Contacto("Carlos", "Motas", "Claro", 25, 70.0F, "Tamaulipas 215", "55 1789245", "marcos@contoso.com", R.drawable.foto_04))
        contactos?.add(Contacto("Alex", "Rodriguez", "MLB", 25, 70.0F, "Tamaulipas 215", "55 1789245", "marcos@contoso.com", R.drawable.foto_05))
        contactos?.add(Contacto("David", "Ortiz", "Metaldon", 25, 70.0F, "Tamaulipas 215", "55 1789245", "marcos@contoso.com", R.drawable.foto_06))

        lista = findViewById(R.id.lista)
        grid = findViewById(R.id.grid)
        adaptador = AdaptadorCustom(this, contactos!!)
        adaptadorGrid = AdaptadorCustomGrid(this, contactos!!)
        viewSwitcher = findViewById(R.id.viewSwitcher)

        lista?.adapter = adaptador
        grid?.adapter = adaptadorGrid

        lista?.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, Detalle::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }
        grid?.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, Detalle::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.searchView)
        val searchView = itemBusqueda?.actionView as SearchView

        val itemSwitch = menu?.findItem(R.id.switchView)
        itemSwitch?.setActionView(R.layout.switch_item)
        val switchView = itemSwitch.actionView?.findViewById<Switch>(R.id.sCambiaVista)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Buscar contacto..."

        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            // Preparar datos
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                // Filtrar
                adaptador?.filtrar(newText!!)
                adaptadorGrid?.filtrar(newText!!)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // Filtrar
                return true
            }
        })

        switchView?.setOnCheckedChangeListener { buttonView, isChecked ->
            viewSwitcher?.showNext()
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {

            R.id.iNuevo -> {
                val intent = Intent(this, Nuevo::class.java)
                startActivity(intent)
                return true
            }
            else -> {return super.onOptionsItemSelected(item)}
        }
    }

    override fun onResume() {
        super.onResume()

        adaptador?.notifyDataSetChanged()
        adaptadorGrid?.notifyDataSetChanged()
    }
}