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

class EmailActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private var ttsInicializado = false
    private var idioma: String = "pt"
    private var liçãoAtual = 0
    private var audioPausado = false


    // LISTA DE LIÇÕES DO MÓDULO EMAIL - COMPLETO
    private val liçõesPortugues = listOf(
        Lição(
            "O que é Email?",
            "O email é como uma carta digital que chega em segundos para qualquer lugar do mundo!\n\n" +
                    "• Email: Sua identidade digital para receber mensagens\n" +
                    "• Endereço de email: Seu 'endereço' digital (ex: nome@gmail.com)\n" +
                    "• Caixa de entrada: Onde chegam suas mensagens\n" +
                    "• Enviar/Receber: Como mandar e receber emails\n" +
                    "• Anexo: Arquivos que você envia com o email\n\n" +
                    "Dica: O email é rápido, barato e ecológico - não usa papel!",
            "📧",
            "email_introducao"
        ),
        Lição(
            "Criando Meu Primeiro Email",
            "Criar um email é como conseguir sua própria caixa postal digital!\n\n" +
                    "Provedores gratuitos populares:\n" +
                    "• Gmail (Google)\n" +
                    "• Outlook (Microsoft)\n" +
                    "• Yahoo Mail\n\n" +
                    "Como criar:\n" +
                    "1. Escolha um provedor (recomendamos Gmail)\n" +
                    "2. Clique em 'Criar conta'\n" +
                    "3. Preencha seus dados pessoais\n" +
                    "4. Escolha um nome de usuário único\n" +
                    "5. Crie uma senha forte\n\n" +
                    "Dica: Use seu nome real para parecer mais profissional!",
            "👤",
            "criar_email"
        ),
        Lição(
            "Escrevendo e Enviando Emails",
            "Escrever um email é fácil! É como escrever uma carta, mas mais rápido.\n\n" +
                    "Partes de um email:\n" +
                    "• Para: Endereço de quem recebe\n" +
                    "• Assunto: Resumo do que o email trata\n" +
                    "• Corpo: Sua mensagem principal\n" +
                    "• Anexo: Arquivos que você envia junto\n\n" +
                    "Passo a passo:\n" +
                    "1. Clique em 'Escrever' ou 'Novo email'\n" +
                    "2. Digite o email do destinatário\n" +
                    "3. Escreva um assunto claro\n" +
                    "4. Redija sua mensagem\n" +
                    "5. Clique em 'Enviar'",
            "✍️",
            "escrever_email"
        ),
        Lição(
            "Organizando a Caixa de Entrada",
            "Manter sua caixa de entrada organizada ajuda a encontrar emails importantes!\n\n" +
                    "Ferramentas de organização:\n" +
                    "• Pastas: Para classificar seus emails\n" +
                    "• Filtros: Organizam automaticamente\n" +
                    "• Estrelas: Marcam emails importantes\n" +
                    "• Arquivar: Guarda sem deletar\n" +
                    "• Lixeira: Onde vão emails excluídos\n\n" +
                    "Dicas de organização:\n" +
                    "• Crie pastas por assunto (Trabalho, Família, Contas)\n" +
                    "• Apague emails desnecessários\n" +
                    "• Use a busca para encontrar rápido\n" +
                    "• Limpe a lixeira regularmente",
            "🗂️",
            "organizar_email"
        ),
        Lição(
            "Segurança no Email",
            "Proteger seu email é muito importante para evitar problemas!\n\n" +
                    "Perigos comuns:\n" +
                    "• Phishing: Emails falsos pedindo dados\n" +
                    "• Spam: Emails não solicitados\n" +
                    "• Vírus em anexos\n" +
                    "• Links perigosos\n\n" +
                    "Dicas de segurança:\n" +
                    "• Nunca clique em links suspeitos\n" +
                    "• Desconfie de emails pedindo senhas\n" +
                    "• Verifique o remetente antes de abrir\n" +
                    "• Use senha forte e ative verificação em duas etapas\n" +
                    "• Não abra anexos de desconhecidos",
            "🛡️",
            "seguranca_email"
        ),
        // NOVAS LIÇÕES ADICIONADAS
        Lição(
            "Enviando e Recebendo Anexos",
            "Anexar um arquivo é como colocar uma foto dentro de um envelope junto com sua carta!\n\n" +
                    "Como anexar:\n" +
                    "1. Clique no botão de clipe (📎) ou 'Anexar arquivo'\n" +
                    "2. Escolha o arquivo do seu computador ou celular\n" +
                    "3. Aguarde o upload terminar\n" +
                    "4. Clique em 'Enviar'\n\n" +
                    "Tipos de arquivos comuns:\n" +
                    "• Documentos (PDF, Word)\n" +
                    "• Planilhas (Excel)\n" +
                    "• Apresentações (PowerPoint)\n" +
                    "• Imagens (JPG, PNG)\n\n" +
                    "Cuidados:\n" +
                    "• Verifique se anexou o arquivo certo\n" +
                    "• Arquivos muito grandes podem não enviar\n" +
                    "• Só abra anexos de remetentes confiáveis",
            "📎",
            "email_anexos"
        ),
        Lição(
            "Organizando com Pastas e Etiquetas",
            "Uma caixa de entrada organizada é como um armário com gavetas - você encontra tudo rapidamente!\n\n" +
                    "Como organizar:\n" +
                    "• Crie pastas para assuntos específicos (Trabalho, Pessoal, Contas)\n" +
                    "• Use etiquetas coloridas para identificar urgências\n" +
                    "• Arraste emails para as pastas\n" +
                    "• Crie filtros para organização automática\n\n" +
                    "Dicas de organização:\n" +
                    "• Deixe a caixa de entrada só para emails novos\n" +
                    "• Arquive emails importantes que já respondeu\n" +
                    "• Limpe a lixeira regularmente\n" +
                    "• Use a busca para encontrar emails antigos",
            "🏷️",
            "email_pastas"
        ),
        Lição(
            "Escrevendo Emails Claros e Objetivos",
            "Um email bem escrito é como uma conversa clara - todo mundo entende e sabe o que fazer!\n\n" +
                    "Estrutura ideal:\n" +
                    "• Saudação educada (Prezado(a), Olá)\n" +
                    "• Assunto claro e direto\n" +
                    "• Mensagem objetiva\n" +
                    "• Pedido específico se necessário\n" +
                    "• Despedida educada\n\n" +
                    "Dicas de clareza:\n" +
                    "• Seja breve mas educado\n" +
                    "• Revise antes de enviar\n" +
                    "• Use parágrafos curtos\n" +
                    "• Destaque informações importantes\n" +
                    "• Confira se o assunto reflete o conteúdo",
            "✨",
            "email_clareza"
        ),
        Lição(
            "Respondendo e Encaminhando",
            "Saber quando e como responder é tão importante quanto escrever o email original!\n\n" +
                    "Quando usar cada opção:\n" +
                    "• 'Responder': Só para quem enviou\n" +
                    "• 'Responder a todos': Para toda a lista de emails\n" +
                    "• 'Encaminhar': Para enviar a outra pessoa\n\n" +
                    "Etiqueta ao encaminhar:\n" +
                    "• Sempre explique por que está encaminhando\n" +
                    "• Respeite a privacidade dos remetentes\n" +
                    "• Mantenha o histórico da conversa\n" +
                    "• Não encamine correntes ou spam",
            "🔄",
            "email_encaminhar"
        ),
        Lição(
            "Problemas Comuns e Soluções",
            "Problemas com email acontecem, mas a maioria tem solução simples!\n\n" +
                    "Problemas frequentes:\n" +
                    "• Esqueci minha senha: Use 'Recuperar senha'\n" +
                    "• Email não envia: Verifique a conexão com internet\n" +
                    "• Não recebo emails: Verifique a caixa de spam\n" +
                    "• Anexo muito grande: Comprima o arquivo\n\n" +
                    "Soluções rápidas:\n" +
                    "• Sempre teste primeiro em outro dispositivo\n" +
                    "• Reinicie o computador/celular\n" +
                    "• Limpe o cache do navegador\n" +
                    "• Verifique se o provedor está funcionando",
            "🛠️",
            "email_problemas"
        )
    )

    private val liçõesEspanol = listOf(
        Lição(
            "¿Qué es el Email?",
            "¡El email es como una carta digital que llega en segundos a cualquier parte del mundo!\n\n" +
                    "• Email: Tu identidad digital para recibir mensajes\n" +
                    "• Dirección de email: Tu 'dirección' digital (ej: nombre@gmail.com)\n" +
                    "• Bandeja de entrada: Donde llegan tus mensajes\n" +
                    "• Enviar/Recibir: Cómo mandar y recibir emails\n" +
                    "• Adjunto: Archivos que envías con el email\n\n" +
                    "Consejo: ¡El email es rápido, barato y ecológico - no usa papel!",
            "📧",
            "email_introduccion"
        ),
        Lição(
            "Creando Mi Primer Email",
            "¡Crear un email es como conseguir tu propio buzón digital!\n\n" +
                    "Proveedores gratuitos populares:\n" +
                    "• Gmail (Google)\n" +
                    "• Outlook (Microsoft)\n" +
                    "• Yahoo Mail\n\n" +
                    "Cómo crear:\n" +
                    "1. Elige un proveedor (recomendamos Gmail)\n" +
                    "2. Haz clic en 'Crear cuenta'\n" +
                    "3. Completa tus datos personales\n" +
                    "4. Elige un nombre de usuario único\n" +
                    "5. Crea una contraseña fuerte\n\n" +
                    "Consejo: ¡Usa tu nombre real para parecer más profesional!",
            "👤",
            "crear_email"
        ),
        Lição(
            "Escribiendo y Enviando Emails",
            "¡Escribir un email es fácil! Es como escribir una carta, pero más rápido.\n\n" +
                    "Partes de un email:\n" +
                    "• Para: Dirección de quien recibe\n" +
                    "• Asunto: Resumen de lo que trata el email\n" +
                    "• Cuerpo: Tu mensaje principal\n" +
                    "• Adjunto: Archivos que envías junto\n\n" +
                    "Paso a paso:\n" +
                    "1. Haz clic en 'Escribir' o 'Nuevo email'\n" +
                    "2. Escribe el email del destinatario\n" +
                    "3. Escribe un asunto claro\n" +
                    "4. Redacta tu mensaje\n" +
                    "5. Haz clic en 'Enviar'",
            "✍️",
            "escribir_email"
        ),
        Lição(
            "Organizando la Bandeja de Entrada",
            "¡Mantener tu bandeja de entrada organizada ayuda a encontrar emails importantes!\n\n" +
                    "Herramientas de organización:\n" +
                    "• Carpetas: Para clasificar tus emails\n" +
                    "• Filtros: Organizan automáticamente\n" +
                    "• Estrellas: Marcan emails importantes\n" +
                    "• Archivar: Guarda sin eliminar\n" +
                    "• Papelera: Donde van emails eliminados\n\n" +
                    "Consejos de organización:\n" +
                    "• Crea carpetas por asunto (Trabajo, Familia, Cuentas)\n" +
                    "• Elimina emails innecesarios\n" +
                    "• Usa la búsqueda para encontrar rápido\n" +
                    "• Limpia la papelera regularmente",
            "🗂️",
            "organizar_email"
        ),
        Lição(
            "Seguridad en el Email",
            "¡Proteger tu email es muy importante para evitar problemas!\n\n" +
                    "Peligros comunes:\n" +
                    "• Phishing: Emails falsos pidiendo datos\n" +
                    "• Spam: Emails no solicitados\n" +
                    "• Virus en archivos adjuntos\n" +
                    "• Enlaces peligrosos\n\n" +
                    "Consejos de seguridad:\n" +
                    "• Nunca hagas clic en enlaces sospechosos\n" +
                    "• Desconfía de emails pidiendo contraseñas\n" +
                    "• Verifica el remitente antes de abrir\n" +
                    "• Usa contraseña fuerte y activa verificación en dos pasos\n" +
                    "• No abras adjuntos de desconocidos",
            "🛡️",
            "seguridad_email"
        ),
        // NUEVAS LECCIONES AÑADIDAS
        Lição(
            "Enviando y Recibiendo Adjuntos",
            "¡Adjuntar un archivo es como poner una foto dentro de un sobre junto con tu carta!\n\n" +
                    "Cómo adjuntar:\n" +
                    "1. Haz clic en el botón de clip (📎) o 'Adjuntar archivo'\n" +
                    "2. Elige el archivo de tu computadora o celular\n" +
                    "3. Espera a que termine la subida\n" +
                    "4. Haz clic en 'Enviar'\n\n" +
                    "Tipos de archivos comunes:\n" +
                    "• Documentos (PDF, Word)\n" +
                    "• Hojas de cálculo (Excel)\n" +
                    "• Presentaciones (PowerPoint)\n" +
                    "• Imágenes (JPG, PNG)\n\n" +
                    "Precauciones:\n" +
                    "• Verifica que adjuntaste el archivo correcto\n" +
                    "• Archivos muy grandes pueden no enviarse\n" +
                    "• Solo abre adjuntos de remitentes confiables",
            "📎",
            "email_adjuntos"
        ),
        Lição(
            "Organizando con Carpetas y Etiquetas",
            "¡Una bandeja de entrada organizada es como un armario con cajones - encuentras todo rápidamente!\n\n" +
                    "Cómo organizar:\n" +
                    "• Crea carpetas para asuntos específicos (Trabajo, Personal, Cuentas)\n" +
                    "• Usa etiquetas de colores para identificar urgencias\n" +
                    "• Arrastra emails a las carpetas\n" +
                    "• Crea filtros para organización automática\n\n" +
                    "Consejos de organización:\n" +
                    "• Deja la bandeja de entrada solo para emails nuevos\n" +
                    "• Archiva emails importantes que ya respondiste\n" +
                    "• Limpia la papelera regularmente\n" +
                    "• Usa la búsqueda para encontrar emails antiguos",
            "🏷️",
            "email_carpetas"
        ),
        Lição(
            "Escribiendo Emails Claros y Objetivos",
            "¡Un email bien escrito es como una conversación clara - todos entienden y saben qué hacer!\n\n" +
                    "Estructura ideal:\n" +
                    "• Saludo educado (Estimado(a), Hola)\n" +
                    "• Asunto claro y directo\n" +
                    "• Mensaje objetiva\n" +
                    "• Pedido específico si es necesario\n" +
                    "• Despedida educada\n\n" +
                    "Consejos de claridad:\n" +
                    "• Sé breve pero educado\n" +
                    "• Revisa antes de enviar\n" +
                    "• Usa párrafos cortos\n" +
                    "• Destaca información importante\n" +
                    "• Verifica que el asunto refleje el contenido",
            "✨",
            "email_claridad"
        ),
        Lição(
            "Respondiendo y Reenviando",
            "¡Saber cuándo y cómo responder es tan importante como escribir el email original!\n\n" +
                    "Cuándo usar cada opción:\n" +
                    "• 'Responder': Solo para quien envió\n" +
                    "• 'Responder a todos': Para toda la lista de emails\n" +
                    "• 'Reenviar': Para enviar a otra persona\n\n" +
                    "Etiqueta al reenviar:\n" +
                    "• Siempre explica por qué reenvías\n" +
                    "• Respeta la privacidad de los remitentes\n" +
                    "• Mantén el historial de la conversación\n" +
                    "• No reenvíes cadenas o spam",
            "🔄",
            "email_reenviar"
        ),
        Lição(
            "Problemas Comunes y Soluciones",
            "¡Los problemas con el email ocurren, pero la mayoría tiene solución simple!\n\n" +
                    "Problemas frecuentes:\n" +
                    "• Olvidé mi contraseña: Usa 'Recuperar contraseña'\n" +
                    "• Email no envía: Verifica la conexión a internet\n" +
                    "• No recibo emails: Revisa la bandeja de spam\n" +
                    "• Adjunto muy grande: Comprime el archivo\n\n" +
                    "Soluciones rápidas:\n" +
                    "• Siempre prueba primero en otro dispositivo\n" +
                    "• Reinicia la computadora/celular\n" +
                    "• Limpia la cache del navegador\n" +
                    "• Verifica si el proveedor está funcionando",
            "🛠️",
            "email_problemas"
        )
    )




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desktop) // Usa o mesmo layout do Desktop

        // Configurar idioma
        idioma = intent.getStringExtra("IDIOMA") ?: "pt"

        // Configurar título
        val txtTituloTela = findViewById<TextView>(R.id.txtTituloTela)
        txtTituloTela.text = if (idioma == "es") "Correo - electrónico" else "Email - Comunicação"

        // Inicializar componentes
        tts = TextToSpeech(this, this)
        configurarBotoes()
        configurarCliqueImagem()
        mostrarLicaoAtual()
    }

    //  **TODAS AS FUNÇÕES ABAIXO SÃO IGUAIS AO DesktopActivity *
    // (configurarBotoes, mostrarLicaoAtual, falarLicaoAtual, funções do quiz, etc.)
    // Copie EXATAMENTE as mesmas funções do DesktopActivity

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
                    "¡Felicitaciones! Completaste el módulo Correo  electrónico! 🎉"
                } else {
                    "Parabéns! Você completou o módulo Email! 🎉"
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

        // No EmailActivity - função do botão Quiz
        btnQuiz.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("IDIOMA", idioma)
            intent.putExtra("MODULO", "Email") // DEVE SER "Email"
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

    // ... Cole aqui TODAS as outras funções do DesktopActivity ...

    override fun onDestroy() {
        if (ttsInicializado) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}
data class LiçãoEmail(
    val titulo: String,
    val conteudo: String,
    val icone: String,
    val imagem: String
)