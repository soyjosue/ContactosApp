package com.example.contactos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AdaptadorCustomGrid(var context: Context, items: ArrayList<Contacto>): BaseAdapter() {
    // Almacenar los elementos que se van a mostrar en el ListView
    var items:ArrayList<Contacto>? = null
    var copiaItems: ArrayList<Contacto>? = null

    init {
        this.items = ArrayList(items)
        this.copiaItems = items
    }

    override fun getCount(): Int {
        // Regresar el numero de elementos de mi lista
        return this.items?.count()!!
    }

    fun addItem(item:Contacto) {
        copiaItems?.add(item)
        items = ArrayList(copiaItems!!)
        notifyDataSetChanged()
    }

    fun removeItem(index:Int) {
        copiaItems?.removeAt(index)
        items = ArrayList(copiaItems!!)
        notifyDataSetChanged()
    }

    fun updateItem(index: Int, newItem:Contacto) {
        copiaItems?.set(index, newItem)
        items = ArrayList(copiaItems!!)
        notifyDataSetChanged()
    }

    fun filtrar(str: String) {
        items?.clear()

        if(str.isEmpty()) {
            items = ArrayList(copiaItems!!)
            notifyDataSetChanged()
            return
        }

        var busqueda = str
        busqueda = busqueda.toLowerCase()

        for (item in copiaItems!!) {
            val nombre = item.nombre.toLowerCase()
            val apellido = item.apellidos.toLowerCase()

            if(nombre.contains(busqueda) || apellido.contains(busqueda)) {
                items?.add(item)
            }
        }

        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Any {
        return this.items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var viewHolder:ViewHolder? = null
        var vista:View? = convertView

        // Validar si la vista esta vacia o no
        if (vista == null) {
            vista = LayoutInflater.from(context).inflate(R.layout.template_contacto_grid, null)
            viewHolder = ViewHolder(vista)
            vista.tag = viewHolder
        } else {
            viewHolder = vista.tag as? ViewHolder
        }

        val item = getItem(position) as Contacto

        // Asignacion de valores a elementos graficos.
        viewHolder?.nombre?.text = "${item.nombre} ${item.apellidos}"
        viewHolder?.foto?.setImageResource(item.foto)

        return vista!!

    }

    private class ViewHolder(vista:View) {
        var nombre: TextView? = null
        var foto: ImageView? = null


        init {
            this.nombre = vista.findViewById(R.id.tvNombre)
            this.foto = vista.findViewById(R.id.ivFoto)
        }
    }
}