package com.ana.digitalelas

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MenuModulosActivity : AppCompatActivity() {

    private val modulos = listOf(
        Modulo("Desktop", "🖥️", "Meu Computador Básico"),
        Modulo("Celular", "📱", "Domine seu Smartphone"),
        Modulo("Internet", "🌐", "Navegue com Segurança"),
        Modulo("Email", "📧", "Email e Comunicação"),
        Modulo("Trabalho", "💼", "Mundo do Trabalho"),
        Modulo("Compras", "🛒", "Compras Inteligentes"),
        Modulo("Banco", "💰", "Banco Digital"),
        Modulo("Segurança", "🔒", "Segurança Digital")

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_modulos)

        // Detectar idioma
        val idioma = intent.getStringExtra("IDIOMA") ?: "pt"

        // Botão Voltar
        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish() // Volta para tela anterior
        }

        // Configurar GridView
        val gridView = findViewById<GridView>(R.id.gridViewModulos)
        val adapter = ModuloAdapter(this, modulos)
        gridView.adapter = adapter

        // Quando clicar em um módulo
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val moduloSelecionado = modulos[position]

            // Aqui vamos abrir a tela do módulo selecionado
            when (moduloSelecionado.nome) {
                "Desktop" -> {
                    val intent = Intent(this, DesktopActivity::class.java)
                    intent.putExtra("IDIOMA", idioma) // ✅ Passar idioma
                    startActivity(intent)
                }

                "Celular" -> {
                    val intent = Intent(this, CelularActivity::class.java)
                    intent.putExtra("IDIOMA", idioma) // ✅ Passar idioma
                    startActivity(intent)
                }

                // Outros módulos virão depois...
                else -> {
                    // Por enquanto, mostra mensagem
                    val mensagem = if (idioma == "es") {
                        "Módulo ${moduloSelecionado.nome} pronto!"
                    } else {
                        "Módulo ${moduloSelecionado.nome} em breve!"
                    }
                    android.widget.Toast.makeText(
                        this,
                        mensagem,
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

// Classe para representar um módulo
data class Modulo(
    val nome: String,
    val icone: String,
    val descricao: String
)