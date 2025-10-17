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

class CelularActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private var ttsInicializado = false
    private var idioma: String = "pt"
    private var liçãoAtual = 0
    private var audioPausado = false

    // Lista de lições para o módulo CELULAR
    private val liçõesPortugues = listOf(
        LiçãoCelular(
            "Conhecendo seu Smartphone",
            "Seu celular é como uma casa portátil: a tela é a janela, os botões são as portas, a câmera são seus olhos digitais. Cada parte tem uma função especial!\n\n" +
                    "• Tela Touch: Sua janela para o mundo digital - toque para interagir\n" +
                    "• Botão Power: A chave que liga e desliga sua casa portátil\n" +
                    "• Botões de Volume: O controle do som ambiente\n" +
                    "• Câmeras: Seus olhos para capturar momentos\n" +
                    "• Alto-falante: Sua voz para ouvir músicas e chamadas\n" +
                    "• Conector de Carregamento: A tomada da sua casa digital\n\n" +
                    "Dica: Explore cada parte devagar, como conhecer uma casa nova!",
            "📱",
            "celular_partes"
        ),
        LiçãoCelular(
            "Ligação e Contatos",
            "Fazer ligações é como bater na porta de alguém: disca o número e espera atender. Os contatos são sua agenda digital de telefones!\n\n" +
                    "Como fazer uma ligação:\n" +
                    "1. Abra o aplicativo Telefone\n" +
                    "2. Digite o número com o teclado\n" +
                    "3. Clique no ícone verde de telefone\n" +
                    "4. Espere a pessoa atender\n\n" +
                    "Como salvar um contato:\n" +
                    "1. Vá em Contatos → Adicionar\n" +
                    "2. Digite o nome e número\n" +
                    "3. Salve como novo contato\n\n" +
                    "Dica: Salve contatos importantes com foto para reconhecer rápido!",
            "📞",
            "celular_ligacoes"
        ),
        LiçãoCelular(
            "Mensagens de Texto",
            "Mensagens são bilhetes digitais instantâneos: escreva e envie em segundos para qualquer lugar!\n\n" +
                    "Tipos de mensagens:\n" +
                    "• SMS: Mensagem rápida do celular\n" +
                    "• WhatsApp: Mensagem com internet\n" +
                    "• Email: Carta digital mais formal\n\n" +
                    "Como enviar SMS:\n" +
                    "1. Abra Mensagens → Nova mensagem\n" +
                    "2. Digite o número ou escolha contato\n" +
                    "3. Escreva sua mensagem\n" +
                    "4. Clique em Enviar\n\n" +
                    "Dica: Use emojis 😊 para dar emoção às mensagens!",
            "💬",
            "celular_mensagens"
        ),
        LiçãoCelular(
            "Tirando Fotos e Vídeos",
            "Sua câmera é um álbum de fotos que cabe no bolso - capture momentos com um toque!\n\n" +
                    "Como tirar fotos:\n" +
                    "1. Abra o aplicativo Câmera\n" +
                    "2. Aponte para o que quer fotografar\n" +
                    "3. Toque no botão circular para capturar\n" +
                    "4. Veja a foto na Galeria\n\n" +
                    "Modos da câmera:\n" +
                    "• Foto: Imagem estática\n" +
                    "• Vídeo: Grava com movimento\n" +
                    "• Selfie: Foto de você mesmo\n" +
                    "• Panorama: Foto bem larga\n\n" +
                    "Dica: Mantenha a mão firme para fotos não saírem tremidas!",
            "📸",
            "celular_camera"
        ),
        LiçãoCelular(
            "Navegando na Internet",
            "Seu celular é uma biblioteca mundial no bolso - pesquise qualquer coisa em segundos!\n\n" +
                    "Como navegar:\n" +
                    "1. Abra Chrome, Firefox ou seu navegador\n" +
                    "2. Toque na barra de endereços\n" +
                    "3. Digite www.exemplo.com.br\n" +
                    "4. Clique em Ir ou Enter\n\n" +
                    "Sites úteis para começar:\n" +
                    "• Google.com - busca tudo\n" +
                    "• YouTube.com - vídeos\n" +
                    "• Wikipedia.org - enciclopédia\n" +
                    "• Climatempo.com.br - previsão do tempo\n\n",
            "🌐",
            "celular_internet"
        ),
        LiçãoCelular(
            "Apps - Instalando e Usando",
            "Apps são como ferramentas na sua caixa: cada um tem uma função específica e útil!\n\n" +
                    "Como instalar apps:\n" +
                    "1. Abra Play Store (Android) ou App Store (iPhone)\n" +
                    "2. Pesquise o app que quer\n" +
                    "3. Clique em Instalar\n" +
                    "4. Aguarde o download terminar\n\n" +
                    "Apps essenciais:\n" +
                    "• WhatsApp - mensagens\n" +
                    "• Câmera - fotos\n" +
                    "• Maps - mapas e rotas\n" +
                    "• Banco - aplicativo do banco\n\n" +
                    "Dica: Organize apps por pastas na tela inicial!",
            "📲",
            "celular_apps"
        ),
        LiçãoCelular(
            "Configurações Básicas",
            "As configurações são o painel de controle do seu celular - ajuste tudo do seu jeito!\n\n" +
                    "Configurações importantes:\n" +
                    "• Wi-Fi: Conecte à internet sem usar dados\n" +
                    "• Bluetooth: Conecte a fones e outros dispositivos\n" +
                    "• Brilho: Ajuste para melhor visibilidade\n" +
                    "• Som: Volume de chamadas e músicas\n" +
                    "• Tela: Tempo para escurecer sozinha\n\n" +
                    "Como acessar:\n" +
                    "1. Abra o app Configurações\n" +
                    "2. Navegue pelas opções\n" +
                    "3. Toque para ajustar\n\n" +
                    "Dica: Ative Wi-Fi em casa para economizar dados!",
            "⚙️",
            "celular_configuracoes"
        ),
        LiçãoCelular(
            "Segurança no Celular",
            "Proteger seu celular é como trancar sua casa: senhas fortes mantêm seus dados seguros!\n\n" +
                    "Medidas de segurança:\n" +
                    "• Senha/PIN: Use números que só você sabe\n" +
                    "• Pattern: Desenhe um desenho secreto\n" +
                    "• Digital: Sua digital é única e segura\n" +
                    "• Facial: Seu rosto é a chave\n\n" +
                    "Como configurar senha:\n" +
                    "1. Configurações → Segurança\n" +
                    "2. Escolha tipo de bloqueio\n" +
                    "3. Siga as instruções\n\n" +
                    "Dica: Não use datas de nascimento ou 1234!",
            "🔒",
            "celular_seguranca"
        ),
        LiçãoCelular(
            "Bateria e Economia de Energia",
            "A bateria é o combustível do seu celular - quando acaba, precisa recarregar na tomada!\n\n" +
                    "Dicas para economizar:\n" +
                    "• Reduza brilho da tela\n" +
                    "• Feche apps não usados\n" +
                    "• Desative Wi-Fi/Bluetooth quando não usar\n" +
                    "• Use modo economia de bateria\n\n" +
                    "Como carregar corretamente:\n" +
                    "1. Conecte o carregador na tomada\n" +
                    "2. Conecte o cabo no celular\n" +
                    "3. Aguarde carregar completamente\n" +
                    "4. Desconecte quando pronto\n\n" +
                    "Dica: Não deixe carregar a noite toda!",
            "🔋",
            "celular_bateria"
        ),
        LiçãoCelular(
            "Acessibilidade",
            "Recursos de acessibilidade são óculos e aparelhos auditivos digitais - facilitam o uso para todos!\n\n" +
                    "Recursos úteis:\n" +
                    "• Fonte maior: Texto fácil de ler\n" +
                    "• Leitor de tela: Fala o que está na tela\n" +
                    "• Alto contraste: Cores mais definidas\n" +
                    "• Toque ampliado: Área de toque maior\n" +
                    "• Legendas: Texto em vídeos\n\n" +
                    "Como ativar:\n" +
                    "1. Configurações → Acessibilidade\n" +
                    "2. Escolha o recurso desejado\n" +
                    "3. Ative e ajuste\n\n" +
                    "Dica: Experimente cada recurso para ver qual ajuda mais!",
            "👁️",
            "celular_acessibilidade"
        )
    )

    private val liçõesEspanol = listOf(
        LiçãoCelular(
            "Conociendo tu Smartphone",
            "Tu celular es como una casa portátil: la pantalla es la ventana, los botones son las puertas, la cámara son tus ojos digitales. ¡Cada parte tiene una función especial!\n\n" +
                    "• Pantalla Táctil: Tu ventana al mundo digital - toca para interactuar\n" +
                    "• Botón Power: La llave que enciende y apaga tu casa portátil\n" +
                    "• Botones de Volumen: El control del sonido ambiente\n" +
                    "• Cámaras: Tus ojos para capturar momentos\n" +
                    "• Altavoz: Tu voz para escuchar música y llamadas\n" +
                    "• Conector de Carga: El enchufe de tu casa digital\n\n" +
                    "Consejo: ¡Explora cada parte despacio, como conocer una casa nueva!",
            "📱",
            "celular_partes"
        ),
        LiçãoCelular(
            "Llamadas y Contactos",
            "Hacer llamadas es como tocar la puerta de alguien: marca el número y espera que contesten. ¡Los contactos son tu agenda digital de teléfonos!\n\n" +
                    "Cómo hacer una llamada:\n" +
                    "1. Abre la aplicación Teléfono\n" +
                    "2. Digita el número con el teclado\n" +
                    "3. Haz clic en el ícono verde de teléfono\n" +
                    "4. Espera a que la persona conteste\n\n" +
                    "Cómo guardar un contacto:\n" +
                    "1. Ve a Contactos → Agregar\n" +
                    "2. Escribe el nombre y número\n" +
                    "3. Guarda como nuevo contacto\n\n" +
                    "Consejo: ¡Guarda contactos importantes con foto para reconocer rápido!",
            "📞",
            "celular_ligacoes"
        ),
        LiçãoCelular(
            "Mensajes de Texto",
            "Los mensajes son billetes digitales instantáneos: ¡escribe y envía en segundos a cualquier lugar!\n\n" +
                    "Tipos de mensajes:\n" +
                    "• SMS: Mensaje rápido del celular\n" +
                    "• WhatsApp: Mensaje con internet\n" +
                    "• Email: Carta digital más formal\n\n" +
                    "Cómo enviar SMS:\n" +
                    "1. Abre Mensajes → Nuevo mensaje\n" +
                    "2. Escribe el número o elige contacto\n" +
                    "3. Escribe tu mensaje\n" +
                    "4. Haz clic en Enviar\n\n" +
                    "Consejo: ¡Usa emojis 😊 para dar emoción a los mensajes!",
            "💬",
            "celular_mensagens"
        ),
        LiçãoCelular(
            "Tomando Fotos y Videos",
            "Tu cámara es un álbum de fotos que cabe en el bolsillo - ¡captura momentos con un toque!\n\n" +
                    "Cómo tomar fotos:\n" +
                    "1. Abre la aplicación Cámara\n" +
                    "2. Apunta a lo que quieres fotografiar\n" +
                    "3. Toca el botón circular para capturar\n" +
                    "4. Ve la foto en la Galería\n\n" +
                    "Modos de la cámara:\n" +
                    "• Foto: Imagen estática\n" +
                    "• Video: Graba con movimiento\n" +
                    "• Selfie: Foto de ti mismo\n" +
                    "• Panorama: Foto muy ancha\n\n" +
                    "Consejo: ¡Mantén la mano firme para que las fotos no salgan movidas!",
            "📸",
            "celular_camera"
        ),
        LiçãoCelular(
            "Navegando en Internet",
            "Tu celular es una biblioteca mundial en el bolsillo - ¡busca cualquier cosa en segundos!\n\n" +
                    "Cómo navegar:\n" +
                    "1. Abre Chrome, Firefox o tu navegador\n" +
                    "2. Toca la barra de direcciones\n" +
                    "3. Escribe www.ejemplo.com\n" +
                    "4. Haz clic en Ir o Enter\n\n" +
                    "Sitios útiles para empezar:\n" +
                    "• Google.com - busca todo\n" +
                    "• YouTube.com - videos\n" +
                    "• Wikipedia.org - enciclopedia\n" +
                    "• Tiempo.com - pronóstico del tiempo\n\n",
            "🌐",
            "celular_internet"
        ),
        LiçãoCelular(
            "Apps - Instalando y Usando",
            "Las apps son como herramientas en tu caja: ¡cada una tiene una función específica y útil!\n\n" +
                    "Cómo instalar apps:\n" +
                    "1. Abre Play Store (Android) o App Store (iPhone)\n" +
                    "2. Busca la app que quieres\n" +
                    "3. Haz clic en Instalar\n" +
                    "4. Espera a que termine la descarga\n\n" +
                    "Apps esenciales:\n" +
                    "• WhatsApp - mensajes\n" +
                    "• Cámara - fotos\n" +
                    "• Maps - mapas y rutas\n" +
                    "• Banco - aplicación del banco\n\n" +
                    "Consejo: ¡Organiza apps por carpetas en la pantalla principal!",
            "📲",
            "celular_apps"
        ),
        LiçãoCelular(
            "Configuraciones Básicas",
            "Las configuraciones son el panel de control de tu celular - ¡ajusta todo a tu manera!\n\n" +
                    "Configuraciones importantes:\n" +
                    "• Wi-Fi: Conéctate a internet sin usar datos\n" +
                    "• Bluetooth: Conecta auriculares y otros dispositivos\n" +
                    "• Brillo: Ajusta para mejor visibilidad\n" +
                    "• Sonido: Volumen de llamadas y músicas\n" +
                    "• Pantalla: Tiempo para oscurecerse sola\n\n" +
                    "Cómo acceder:\n" +
                    "1. Abre la app Configuraciones\n" +
                    "2. Navega por las opciones\n" +
                    "3. Toca para ajustar\n\n" +
                    "Consejo: ¡Activa Wi-Fi en casa para ahorrar datos!",
            "⚙️",
            "celular_configuracoes"
        ),
        LiçãoCelular(
            "Seguridad en el Celular",
            "Proteger tu celular es como cerrar tu casa con llave: ¡contraseñas fuertes mantienen tus datos seguros!\n\n" +
                    "Medidas de seguridad:\n" +
                    "• Contraseña/PIN: Usa números que solo tú sepas\n" +
                    "• Patrón: Dibuja un dibujo secreto\n" +
                    "• Huella Digital: Tu huella es única y segura\n" +
                    "• Facial: Tu rostro es la llave\n\n" +
                    "Cómo configurar contraseña:\n" +
                    "1. Configuración → Seguridad\n" +
                    "2. Elige tipo de bloqueo\n" +
                    "3. Sigue las instrucciones\n\n" +
                    "Consejo: ¡No uses fechas de nacimiento o 1234!",
            "🔒",
            "celular_seguranca"
        ),
        LiçãoCelular(
            "Batería y Ahorro de Energía",
            "La batería es el combustible de tu celular - ¡cuando se acaba, necesita recargarse en el enchufe!\n\n" +
                    "Consejos para ahorrar:\n" +
                    "• Reduce brillo de la pantalla\n" +
                    "• Cierra apps no usadas\n" +
                    "• Desactiva Wi-Fi/Bluetooth cuando no uses\n" +
                    "• Usa modo ahorro de batería\n\n" +
                    "Cómo cargar correctamente:\n" +
                    "1. Conecta el cargador al enchufe\n" +
                    "2. Conecta el cable al celular\n" +
                    "3. Espera a que cargue completamente\n" +
                    "4. Desconecta cuando esté listo\n\n" +
                    "Consejo: ¡No dejes cargando toda la noche!",
            "🔋",
            "celular_bateria"
        ),
        LiçãoCelular(
            "Accesibilidad",
            "Los recursos de accesibilidad son gafas y audífonos digitales - ¡facilitan el uso para todos!\n\n" +
                    "Recursos útiles:\n" +
                    "• Fuente más grande: Texto fácil de leer\n" +
                    "• Lector de pantalla: Habla lo que está en la pantalla\n" +
                    "• Alto contraste: Colores más definidos\n" +
                    "• Toque ampliado: Área de toque más grande\n" +
                    "• Subtítulos: Texto en videos\n\n" +
                    "Cómo activar:\n" +
                    "1. Configuración → Accesibilidad\n" +
                    "2. Elige el recurso deseado\n" +
                    "3. Activa y ajusta\n\n" +
                    "Consejo: ¡Prueba cada recurso para ver cuál ayuda más!",
            "👁️",
            "celular_acessibilidade"
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desktop)

        // 1. DETECTAR IDIOMA
        idioma = intent.getStringExtra("IDIOMA") ?: "pt"

        // 2. MUDAR TÍTULO DA TELA
        val txtTituloTela = findViewById<TextView>(R.id.txtTituloTela)
        if (txtTituloTela != null) {
            val titulo = if (idioma == "es") {
                "Teléfono - Domina tu Smartphone"
            } else {
                "Celular - Domine seu Smartphone"
            }
            txtTituloTela.text = titulo
        }

        // 3. INICIALIZAR COMPONENTES
        tts = TextToSpeech(this, this)
        configurarBotoes()
        configurarCliqueImagem()
        mostrarLiçãoAtual()
    }

    private fun criarImagensPlaceholder() {
        println("🔄 CRIANDO IMAGENS PLACEHOLDER PARA DESKTOP")

        // Para cada lição, verifica se a imagem existe
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

        btnVoltar.setOnClickListener {
            pausarAudio()
            finish()
        }

        btnAnterior.setOnClickListener {
            pausarAudio()
            if (liçãoAtual > 0) {
                liçãoAtual--
                mostrarLiçãoAtual()
            }
        }

        btnProximo.setOnClickListener {
            pausarAudio()
            val lições = if (idioma == "es") liçõesEspanol else liçõesPortugues

            if (liçãoAtual < lições.size - 1) {
                liçãoAtual++
                mostrarLiçãoAtual()
            } else {
                val mensagem = if (idioma == "es") {
                    "¡Completaste las lecciones! ¿Quieres hacer el quiz?"
                } else {
                    "Você completou as lições! Quer fazer o quiz?"
                }
                Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()

                // Mostrar botão do quiz
                btnQuiz.visibility = View.VISIBLE
            }
        }

        // BOTÃO OUVIR COM PLAY/PAUSE CORRIGIDO
        btnOuvir.setOnClickListener {
            if (tts.isSpeaking) {
                // Se está falando -> PARAR
                pausarAudio()
                btnOuvir.setImageResource(android.R.drawable.ic_media_play)
            } else {
                // Se não está falando -> INICIAR
                falarLiçãoAtual()
                btnOuvir.setImageResource(android.R.drawable.ic_media_pause)
            }
        }

        // BOTÃO DO QUIZ
        btnQuiz.setOnClickListener {
            abrirQuiz()
        }
    }


    private fun mostrarLiçãoAtual() {
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

        // RESETAR BOTÃO DE ÁUDIO SEMPRE PARA "PLAY"
        btnOuvir.setImageResource(android.R.drawable.ic_media_play)

        // PARAR QUALQUER ÁUDIO EM ANDAMENTO
        pausarAudio()

        btnAnterior.isEnabled = liçãoAtual > 0

        // CONTROLE DO BOTÃO PRÓXIMO E QUIZ
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

    private fun falarLiçãoAtual() {
        if (!ttsInicializado) {
            val mensagem = if (idioma == "es") "Sistema de voz aún no está listo" else "Sistema de voz ainda não está pronto"
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
            return
        }

        // PARA ÁUDIO ANTERIOR SE ESTIVER FALANDO
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

    private fun retomarAudio() {
        if (ttsInicializado) {
            falarLiçãoAtual()
            val mensagem = if (idioma == "es") "Audio continuando..." else "Áudio continuando..."
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
        }
    }

    private fun abrirQuiz() {
        val intent = Intent(this, QuizActivity::class.java)
        intent.putExtra("IDIOMA", idioma)
        intent.putExtra("MODULO", "Celular")
        startActivity(intent)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            ttsInicializado = true
            val mensagem = if (idioma == "es") "¡Sistema de voz listo!" else "Sistema de voz pronto!"
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
        } else {
            val mensagem = if (idioma == "es") "Error en el sistema de voz" else "Erro no sistema de voz"
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
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

data class LiçãoCelular(
    val titulo: String,
    val conteudo: String,
    val icone: String,
    val imagem: String
)