package com.ana.digitalelas

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class InternetActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private var ttsInicializado = false
    private var idioma: String = "pt"
    private var liçãoAtual = 0
    private var audioPausado = false


    // LISTA DE LIÇÕES DO MÓDULO INTERNET
    private val liçõesPortugues = listOf(
        Lição(
            "Conhecendo a Internet",
            "A internet é como uma grande cidade digital onde você pode visitar lugares, conversar com pessoas e aprender coisas novas!\n\n" +
                    "• Navegador: Seu carro para viajar na internet\n" +
                    "• Site: Um lugar que você visita (loja, biblioteca, jornal)\n" +
                    "• Link: Uma rua que leva a outros lugares\n" +
                    "• URL: O endereço exato de cada site\n" +
                    "• Wi-Fi: Conexão sem fio com a internet\n\n" +
                    "Dica: A internet é como uma biblioteca gigante - você pode encontrar informação sobre qualquer assunto!",
            "🌐",
            "internet_introducao"
        ),
        Lição(
            "Meu Primeiro Navegador",
            "O navegador é o programa que abre as portas da internet. É como ter um carro para viajar pelo mundo digital!\n\n" +
                    "Partes do navegador:\n" +
                    "• Barra de endereços: Onde você digita o destino\n" +
                    "• Botões voltar/avançar: Como retrovisor do carro\n" +
                    "• Botão atualizar: Para recarregar a página\n" +
                    "• Favoritos: Seus lugares preferidos guardados\n" +
                    "• Histórico: Onde você já esteve\n\n" +
                    "Como usar:\n" +
                    "1. Digite o endereço na barra (ex: google.com)\n" +
                    "2. Pressione Enter para viajar\n" +
                    "3. Use os botões para navegar entre páginas",
            "🚗",
            "navegador_partes"
        ),
        Lição(
            "Google - Encontrando Tudo",
            "O Google é como um super detective que encontra qualquer informação em segundos!\n\n" +
                    "Como pesquisar:\n" +
                    "• Digite palavras-chave no campo de busca\n" +
                    "• Seja específico para melhores resultados\n" +
                    "• Use aspas para buscar frases exatas\n" +
                    "• Veja os resultados e clique no que interessa\n\n" +
                    "Dicas de pesquisa:\n" +
                    "• 'receita bolo chocolate' - para receitas\n" +
                    "• 'filme 2024 cinema' - para filmes\n" +
                    "• 'notícias hoje' - para notícias atuais\n" +
                    "• 'como fazer...' - para tutoriais",
            "🔍",
            "google_pesquisa"
        ),
        Lição(
            "Navegação Segura",
            "Na internet, assim como na rua, precisamos tomar cuidados para não cair em armadilhas!\n\n" +
                    "Sinais de perigo:\n" +
                    "• Sites sem 'https://' no início\n" +
                    "• Pop-ups pedindo dados pessoais\n" +
                    "• Ofertas milagrosas demais para ser verdade\n" +
                    "• Links enviados por desconhecidos\n\n" +
                    "Dicas de segurança:\n" +
                    "• Verifique sempre o cadeado verde no navegador\n" +
                    "• Não clique em links suspeitos\n" +
                    "• Desconfie de promoções absurdas\n" +
                    "• Mantenha antivírus atualizado",
            "🛡️",
            "navegacao_segura"
        ),
        Lição(
            "Redes Sociais Básicas",
            "Redes sociais são como praças digitais onde encontramos amigos e familiares!\n\n" +
                    "Principais redes:\n" +
                    "• Facebook: Para conectar com amigos e família\n" +
                    "• WhatsApp: Para mensagens rápidas e chamadas\n" +
                    "• Instagram: Para fotos e vídeos\n" +
                    "• YouTube: Para ver vídeos sobre tudo\n\n" +
                    "Como usar com segurança:\n" +
                    "• Configure a privacidade das suas informações\n" +
                    "• Não aceite estranhos como amigos\n" +
                    "• Pense antes de postar - a internet não esquece\n" +
                    "• Cuidado com informações pessoais",
            "👥",
            "redes_sociais"
        )
    )

    private val liçõesEspanol = listOf(
        Lição(
            "Conociendo Internet",
            "¡Internet es como una gran ciudad digital donde puedes visitar lugares, conversar con personas y aprender cosas nuevas!\n\n" +
                    "• Navegador: Tu auto para viajar por internet\n" +
                    "• Sitio web: Un lugar que visitas (tienda, biblioteca, periódico)\n" +
                    "• Enlace: Una calle que lleva a otros lugares\n" +
                    "• URL: La dirección exacta de cada sitio\n" +
                    "• Wi-Fi: Conexión inalámbrica a internet\n\n" +
                    "Consejo: ¡Internet es como una biblioteca gigante - puedes encontrar información sobre cualquier tema!",
            "🌐",
            "internet_introduccion"
        ),
        Lição(
            "Mi Primer Navegador",
            "¡El navegador es el programa que abre las puertas de internet. Es como tener un auto para viajar por el mundo digital!\n\n" +
                    "Partes del navegador:\n" +
                    "• Barra de direcciones: Donde escribes el destino\n" +
                    "• Botones atrás/adelante: Como espejo retrovisor del auto\n" +
                    "• Botón actualizar: Para recargar la página\n" +
                    "• Favoritos: Tus lugares preferidos guardados\n" +
                    "• Historial: Donde ya has estado\n\n" +
                    "Cómo usar:\n" +
                    "1. Escribe la dirección en la barra (ej: google.com)\n" +
                    "2. Presiona Enter para viajar\n" +
                    "3. Usa los botones para navegar entre páginas",
            "🚗",
            "navegador_partes"
        ),
        Lição(
            "Google - Encontrando Todo",
            "¡Google es como un súper detective que encuentra cualquier información en segundos!\n\n" +
                    "Cómo buscar:\n" +
                    "• Escribe palabras clave en el campo de búsqueda\n" +
                    "• Sé específico para mejores resultados\n" +
                    "• Usa comillas para buscar frases exactas\n" +
                    "• Ve los resultados y haz clic en lo que interesa\n\n" +
                    "Consejos de búsqueda:\n" +
                    "• 'receta pastel chocolate' - para recetas\n" +
                    "• 'película 2024 cine' - para películas\n" +
                    "• 'noticias hoy' - para noticias actuales\n" +
                    "• 'cómo hacer...' - para tutoriales",
            "🔍",
            "google_busqueda"
        ),
        Lição(
            "Navegación Segura",
            "¡En internet, igual que en la calle, necesitamos tener cuidado para no caer en trampas!\n\n" +
                    "Señales de peligro:\n" +
                    "• Sitios sin 'https://' al inicio\n" +
                    "• Pop-ups pidiendo datos personales\n" +
                    "• Ofertas milagrosas demasiado buenas para ser verdad\n" +
                    "• Enlaces enviados por desconocidos\n\n" +
                    "Consejos de seguridad:\n" +
                    "• Verifica siempre el candado verde en el navegador\n" +
                    "• No hagas clic en enlaces sospechosos\n" +
                    "• Desconfía de promociones absurdas\n" +
                    "• Mantén el antivirus actualizado",
            "🛡️",
            "navegacion_segura"
        ),
        Lição(
            "Redes Sociales Básicas",
            "¡Las redes sociales son como plazas digitales donde encontramos amigos y familiares!\n\n" +
                    "Principales redes:\n" +
                    "• Facebook: Para conectar con amigos y familia\n" +
                    "• WhatsApp: Para mensajes rápidos y llamadas\n" +
                    "• Instagram: Para fotos y videos\n" +
                    "• YouTube: Para ver videos sobre todo\n\n" +
                    "Cómo usar con seguridad:\n" +
                    "• Configura la privacidad de tu información\n" +
                    "• No aceptes extraños como amigos\n" +
                    "• Piensa antes de publicar - internet no olvida\n" +
                    "• Cuidado con información personal",
            "👥",
            "redes_sociales"
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desktop) // Usa o mesmo layout do Desktop

        // Configurar idioma
        idioma = intent.getStringExtra("IDIOMA") ?: "pt"

        // Configurar título
        val txtTituloTela = findViewById<TextView>(R.id.txtTituloTela)
        txtTituloTela.text = if (idioma == "es") "Internet - Navegación" else "Internet - Navegação"

        // Inicializar componentes
        tts = TextToSpeech(this, this)
        configurarBotoes()
        configurarCliqueImagem()
        mostrarLicaoAtual()
    }


    private fun criarImagensPlaceholder() {
        println("🔄 CRIANDO IMAGENS PLACEHOLDER PARA DESKTOP")

        val lições = if (idioma == "es") liçõesEspanol else liçõesPortugues
        lições.forEachIndexed { index, lição ->
            val resourceId = resources.getIdentifier(lição.imagem, "drawable", packageName)
            if (resourceId == 0) {
                println("❌ FALTANDO: ${lição.imagem} para lição ${index + 1}")
            } else {
                println("✅ EXISTE: ${lição.imagem}")
            }
        }
    }

    private fun configurarCliqueImagem() {
        val imgLicao = findViewById<ImageView>(R.id.imgLicao)

        imgLicao.setOnClickListener {
            val lições = if (idioma == "es") liçõesEspanol else liçõesPortugues
            val lição = lições[liçãoAtual]

            val intent = Intent(this, ImageFullscreenActivity::class.java)
            intent.putExtra("NOME_IMAGEM", lição.imagem)
            startActivity(intent)
        }
    }

    private fun configurarBotoes() {
        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltarDesktop)
        val btnAnterior = findViewById<Button>(R.id.btnAnterior)
        val btnProximo = findViewById<Button>(R.id.btnProximo)
        val btnOuvir = findViewById<ImageButton>(R.id.btnOuvirLicao)
        val btnQuiz = findViewById<Button>(R.id.btnQuiz)

        // Botão Voltar
        btnVoltar.setOnClickListener {
            pausarAudio()
            finish()
        }

        // Botão Anterior
        btnAnterior.setOnClickListener {
            pausarAudio()
            if (liçãoAtual > 0) {
                liçãoAtual--
                mostrarLicaoAtual()
            }
        }

        // Botão Próximo
        btnProximo.setOnClickListener {
            pausarAudio()
            val lições = if (idioma == "es") liçõesEspanol else liçõesPortugues

            if (liçãoAtual < lições.size - 1) {
                liçãoAtual++
                mostrarLicaoAtual()
            } else {
                val mensagem = if (idioma == "es") {
                    "¡Felicitaciones! Completaste el módulo Internet! 🎉"
                } else {
                    "Parabéns! Você completou o módulo Internet! 🎉"
                }
                Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
                btnQuiz.visibility = View.VISIBLE
            }
        }

        // Botão Ouvir
        btnOuvir.setOnClickListener {
            if (tts.isSpeaking) {
                pausarAudio()
                btnOuvir.setImageResource(android.R.drawable.ic_media_play)
            } else {
                falarLicaoAtual()
                btnOuvir.setImageResource(android.R.drawable.ic_media_pause)
            }
        }

        // No InternetActivity - função do botão Quiz
        btnQuiz.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("IDIOMA", idioma)
            intent.putExtra("MODULO", "Internet") // DEVE SER "Internet"
            startActivity(intent)
        }
    }

    private fun mostrarLicaoAtual() {
        val lições = if (idioma == "es") liçõesEspanol else liçõesPortugues
        val lição = lições[liçãoAtual]

        findViewById<TextView>(R.id.txtTituloLicao).text = lição.titulo
        findViewById<TextView>(R.id.txtConteudoLicao).text = lição.conteudo

        val textoProgresso = if (idioma == "es") {
            "Lección ${liçãoAtual + 1} de ${lições.size}"
        } else {
            "Lição ${liçãoAtual + 1} de ${lições.size}"
        }
        findViewById<TextView>(R.id.txtProgresso).text = textoProgresso

        carregarImagemLicao(findViewById(R.id.imgLicao), lição.imagem)

        val btnAnterior = findViewById<Button>(R.id.btnAnterior)
        val btnProximo = findViewById<Button>(R.id.btnProximo)
        val btnQuiz = findViewById<Button>(R.id.btnQuiz)
        val btnOuvir = findViewById<ImageButton>(R.id.btnOuvirLicao)

        btnOuvir.setImageResource(android.R.drawable.ic_media_play)
        pausarAudio()

        btnAnterior.isEnabled = liçãoAtual > 0

        if (liçãoAtual == lições.size - 1) {
            btnProximo.text = if (idioma == "es") "Fim" else "Fim"
            btnQuiz.visibility = View.VISIBLE
        } else {
            btnProximo.text = if (idioma == "es") "Sig" else "Próx"
            btnQuiz.visibility = View.GONE
        }
    }

    private fun carregarImagemLicao(imageView: ImageView, nomeImagem: String) {
        try {
            val resourceId = resources.getIdentifier(nomeImagem, "drawable", packageName)
            if (resourceId != 0) {
                imageView.setImageResource(resourceId)
            } else {
                imageView.setImageResource(android.R.drawable.ic_menu_help)
                println("AVISO: Imagem não encontrada: $nomeImagem")
            }
        } catch (e: Exception) {
            imageView.setImageResource(android.R.drawable.ic_menu_help)
            println("ERRO ao carregar imagem $nomeImagem: ${e.message}")
        }
    }

    private fun falarLicaoAtual() {
        if (!ttsInicializado) {
            val mensagem = if (idioma == "es") "Sistema de voz aún no está listo" else "Sistema de voz ainda não está pronto"
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
            return
        }

        pausarAudio()

        val lições = if (idioma == "es") liçõesEspanol else liçõesPortugues
        val lição = lições[liçãoAtual]
        val locale = if (idioma == "es") Locale("es", "ES") else Locale("pt", "BR")
        tts.language = locale

        val textoParaFalar = "${lição.titulo}. . . ${lição.conteudo}"
        tts.speak(textoParaFalar, TextToSpeech.QUEUE_FLUSH, null, "lição_${liçãoAtual}")

        val mensagemAudio = if (idioma == "es") "Reproduciendo lección..." else "Reproduzindo lição..."
        Toast.makeText(this, mensagemAudio, Toast.LENGTH_SHORT).show()
    }

    private fun pausarAudio() {
        if (ttsInicializado && tts.isSpeaking) {
            tts.stop()
            val btnOuvir = findViewById<ImageButton>(R.id.btnOuvirLicao)
            btnOuvir.setImageResource(android.R.drawable.ic_media_play)
            audioPausado = false
            val mensagem = if (idioma == "es") "Audio pausado" else "Áudio pausado"
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
        }
    }





    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            ttsInicializado = true
            val locale = if (idioma == "es") Locale("es", "ES") else Locale("pt", "BR")
            val result = tts.setLanguage(locale)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Idioma não suportado", Toast.LENGTH_SHORT).show()
            }
        } else {
            ttsInicializado = false
            Toast.makeText(this, "Erro no sistema de voz", Toast.LENGTH_SHORT).show()
        }
    }



    override fun onDestroy() {
        if (ttsInicializado) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}

