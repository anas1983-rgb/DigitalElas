package com.ana.digitalelas

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private var ttsInicializado = false
    // Definir o idioma padrão (para o TTS) como Português
    private var idiomaSelecionado: String = "pt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar TTS com a Activity como ouvinte
        tts = TextToSpeech(this, this)

        configurarBotoes()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            ttsInicializado = true

            // 1. Tenta definir Português como idioma padrão do TTS
            val result = tts.setLanguage(Locale("pt", "BR"))

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "O idioma Português não é suportado.")
                Toast.makeText(this, "Aviso: Idioma Português não suportado pelo TTS.", Toast.LENGTH_LONG).show()
                // Continua, usando o idioma padrão do sistema
            } else {
                Toast.makeText(this, "Sistema de áudio pronto! (Português)", Toast.LENGTH_SHORT).show()
            }
        } else {
            ttsInicializado = false
            Log.e("TTS", "Erro ao inicializar TTS, status: $status")
            Toast.makeText(this, "Erro ao inicializar sistema de áudio", Toast.LENGTH_LONG).show()
        }
    }

    private fun configurarBotoes() {
        val btnPortugues = findViewById<Button>(R.id.btnPortugues)
        val btnEspanhol = findViewById<Button>(R.id.btnEspanhol)
        val btnOuvirOpcoes = findViewById<ImageButton>(R.id.btnOuvirOpcoes)

        // Verificação de nulls para evitar NullPointerException caso o ID esteja errado no XML
        if (btnPortugues == null || btnEspanhol == null || btnOuvirOpcoes == null) {
            Log.e("MainActivity", "Erro de layout: Um dos botões de idioma não foi encontrado (IDs incorretos).")
            // Mostrar um Toast para o usuário, mas o log é mais importante para o desenvolvedor
            Toast.makeText(this, "Erro interno: Botões não encontrados.", Toast.LENGTH_LONG).show()
            return // Interrompe a configuração
        }

        // --- Botão Português ---
        btnPortugues.setOnClickListener {
            // Atualiza o idioma para reprodução correta (pt)
            idiomaSelecionado = "pt"
            pararAudio()

            // Texto em Português para confirmação
            val textoAudio = "Português selecionado. Bem-vinda ao Digital Elas!"

            // Configura o TTS para Português, fala o texto, e navega
            falarTexto(textoAudio, Locale("pt", "BR"))

            // Navega após um pequeno delay (se necessário) ou imediatamente
            navegarParaMenuModulos("pt")
        }

        // --- Botão Espanhol ---
        btnEspanhol.setOnClickListener {
            // Atualiza o idioma para reprodução correta (es)
            idiomaSelecionado = "es"
            pararAudio()

            // Texto em Espanhol para confirmação
            val textoAudio = "Español seleccionado. ¡Bienvenida a Digital Elas!"

            // Configura o TTS para Espanhol, fala o texto, e navega
            falarTexto(textoAudio, Locale("es", "ES"))

            // Navega imediatamente
            navegarParaMenuModulos("es")
        }

        // --- Botão Ouvir Opções ---
        btnOuvirOpcoes.setOnClickListener {
            pararAudio()

            if (!ttsInicializado) {
                Toast.makeText(this, "Aguarde, sistema de áudio inicializando...", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // O texto bilíngue não precisa de alteração de idioma na função falarTexto,
            // já que a intenção é ler o texto como está. No entanto, é bom forçar o Locale neutro ou padrão.
            val textoAudio = "Escolha seu idioma: Botão Português ou Botão Espanhol. " +
                    "Elija su idioma: Botón Portugués o Botón Español."

            // Usar o locale atual (que deve ser o padrão ou pt-BR)
            falarTexto(textoAudio, tts.language)

            // Feedback visual
            Toast.makeText(this, "🎧 Reproduzindo opções de idioma...", Toast.LENGTH_SHORT).show()
        }
    }

    // Função auxiliar para navegação
    private fun navegarParaMenuModulos(idioma: String) {
        val intent = Intent(this, MenuModulosActivity::class.java)
        intent.putExtra("IDIOMA", idioma)
        // Log para confirmar a transição
        Log.i("DigitalElas", "DEBUG: Idioma recebido no menu = $idioma")
        startActivity(intent)
    }

    private fun pararAudio() {
        if (ttsInicializado && tts.isSpeaking) {
            tts.stop()
        }
    }

    // Função revisada para receber o Locale a ser usado
    private fun falarTexto(texto: String, locale: Locale) {
        if (!ttsInicializado) {
            return
        }

        try {
            // Tenta definir o idioma do TTS antes de falar
            val result = tts.setLanguage(locale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.w("TTS", "Idioma ${locale.displayLanguage} não suportado para o texto: '$texto'")
                // Se não suportado, usa o padrão do TTS
                tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null, "opcoes_idioma")
            } else {
                tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null, "opcoes_idioma")
            }
        } catch (e: Exception) {
            Log.e("TTS", "Erro ao reproduzir áudio: ${e.message}")
            Toast.makeText(this, "Erro ao reproduzir áudio", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        // Garantir que o TTS seja encerrado corretamente
        if (::tts.isInitialized && ttsInicializado) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}