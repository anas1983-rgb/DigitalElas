package com.ana.digitalelas

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ImageFullscreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_fullscreen)

        // Obter nome da imagem enviada pela tela anterior
        val nomeImagem = intent.getStringExtra("NOME_IMAGEM") ?: "computador_partes"

        // Configurar a imagem em tela cheia
        val imageView = findViewById<ImageView>(R.id.imgFullscreen)

        try {
            // Carregar a imagem
            val resourceId = resources.getIdentifier(nomeImagem, "drawable", packageName)
            if (resourceId != 0) {
                imageView.setImageResource(resourceId)
            }
        } catch (e: Exception) {
            // Em caso de erro, usar imagem padrão
            imageView.setImageResource(android.R.drawable.ic_menu_help)
        }

        // Fechar ao tocar na imagem
        imageView.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}