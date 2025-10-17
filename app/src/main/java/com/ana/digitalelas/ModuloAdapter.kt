package com.ana.digitalelas

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ModuloAdapter(
    context: Context,
    private val modulos: List<Modulo>
) : ArrayAdapter<Modulo>(context, 0, modulos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_modulo, parent, false)

        val modulo = modulos[position]

        // Encontrar as Views - TODAS como TextView
        val icone = view.findViewById<TextView>(R.id.iconeModulo)
        val nome = view.findViewById<TextView>(R.id.nomeModulo)
        val descricao = view.findViewById<TextView>(R.id.descricaoModulo)

        // Configurar os textos
        icone.text = modulo.icone        // Emoji como texto
        nome.text = modulo.nome          // Nome do módulo
        descricao.text = modulo.descricao // Descrição

        return view
    }
}