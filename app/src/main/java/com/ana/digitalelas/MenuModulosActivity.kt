package com.ana.digitalelas

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MenuModulosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_modulos)

        // 1. DETECTAR IDIOMA recebido do MainActivity
        val idiomaRecebido = intent.getStringExtra("IDIOMA") ?: "pt"

        // DEBUG: Verificar qual idioma está chegando
        println("DEBUG: Idioma recebido no menu = $idiomaRecebido")
        Toast.makeText(this, "Idioma: $idiomaRecebido", Toast.LENGTH_SHORT).show()


        // 2. CONFIGURAR TÍTULO conforme idioma
        val textoTitle = findViewById<TextView>(R.id.textoTitulo)
        val modulos = if (idiomaRecebido == "es") {
            textoTitle.text = "¿Qué quieres aprender?"
            listOf(
                Modulo("Computadora", "🖥️", "Mi Computadora Básica"),
                Modulo("Teléfono", "📱", "Domina tu Smartphone"),
                Modulo("Internet", "🌐", "Navega con Seguridad"),
                Modulo("Email", "📧", "Email y Comunicación"),
                Modulo("Trabajo", "💼", "Mundo del Trabajo"),
                Modulo("Compras", "🛒", "Compras Inteligentes"),
                Modulo("Banco", "💰", "Banco Digital"),
                Modulo("Seguridad", "🔒", "Seguridad Digital")
            )
        } else {
            textoTitle.text = "O que você quer aprender?"
            listOf(
                Modulo("Desktop", "🖥️", "Meu Computador Básico"),
                Modulo("Celular", "📱", "Domine seu Smartphone"),
                Modulo("Internet", "🌐", "Navegue com Segurança"),
                Modulo("Email", "📧", "Email e Comunicação"),
                Modulo("Trabalho", "💼", "Mundo do Trabalho"),
                Modulo("Compras", "🛒", "Compras Inteligentes"),
                Modulo("Banco", "💰", "Banco Digital"),
                Modulo("Segurança", "🔒", "Segurança Digital")
            )
        }

        // 3. BOTÃO VOLTAR
        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        // 4. CONFIGURAR GRIDVIEW
        val gridView = findViewById<GridView>(R.id.gridViewModulos)
        val adapter = ModuloAdapter(this, modulos)
        gridView.adapter = adapter

        // 5. CONFIGURAR CLIQUE NOS MÓDULOS
        gridView.setOnItemClickListener { parent, view, position, id ->
            val moduloSelecionado = modulos[position]

            when (moduloSelecionado.nome) {
                "Desktop", "Computadora" -> {
                    // ABRIR MÓDULO DESKTOP
                    val intent = Intent(this,DesktopActivity::class.java)
                    intent.putExtra("IDIOMA", idiomaRecebido)
                    startActivity(intent)

                }
                "Celular", "Teléfono" -> {
                    // ABRIR MÓDULO CELULAR
                    val intent = Intent(this, CelularActivity::class.java)
                    intent.putExtra("IDIOMA", idiomaRecebido)
                    startActivity(intent)
                }
                "Internet" -> {  // 👈 **NOVO MÓDULO INTERNET**
                    val intent = Intent(this, InternetActivity::class.java)
                    intent.putExtra("IDIOMA", idiomaRecebido)
                    startActivity(intent)
                }
                "Email" -> {  // 👈 **NOVO MÓDULO EMAIL**
                    val intent = Intent(this, EmailActivity::class.java)
                    intent.putExtra("IDIOMA", idiomaRecebido)
                    startActivity(intent)
                }
                "Trabalho","Trabajo" -> {  // 👈 **NOVO MÓDULO TRABALHO**
                    val intent = Intent(this, TrabalhoActivity::class.java)
                    intent.putExtra("IDIOMA", idiomaRecebido)
                    startActivity(intent)
                }
                "Compras" -> {  // 👈 **NOVO MÓDULO Compras**
                    val intent = Intent(this, ComprasActivity::class.java)
                    intent.putExtra("IDIOMA", idiomaRecebido)
                    startActivity(intent)
                }
                "Banco" -> {  // 👈 **NOVO MÓDULO BANCO**
                    val intent = Intent(this, BancoActivity::class.java)
                    intent.putExtra("IDIOMA", idiomaRecebido)
                    startActivity(intent)
                }
                "Segurança","Seguridad"-> {  // 👈 **NOVO MÓDULO BANCO**
                    val intent = Intent(this, SegurancaActivity::class.java)
                    intent.putExtra("IDIOMA", idiomaRecebido)
                    startActivity(intent)
                }

                else -> {
                    // Para outros módulos, mostrar mensagem
                    val mensagem = if (idiomaRecebido == "es") {
                        "Módulo ${moduloSelecionado.nome} ¡próximamente!"
                    } else {
                        "Módulo ${moduloSelecionado.nome} em breve!"
                    }
                    Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


