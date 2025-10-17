package com.ana.digitalelas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ModuloAdapter(
    context: Context,
    private val modulos: List<Modulo>
) : ArrayAdapter<Modulo>(context, 0, modulos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_modulo, parent, false)

        val modulo = modulos[position]

        val icone = view.findViewById<ImageView>(R.id.iconeModulo)
        val nome = view.findViewById<TextView>(R.id.nomeModulo)

        // Usar emoji como ícone (simples por enquanto)
        nome.text = "${modulo.icone}\n${modulo.nome}\n${modulo.descricao}"

        return view
    }
}