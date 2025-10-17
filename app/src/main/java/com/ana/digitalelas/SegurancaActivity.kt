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



class SegurancaActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private var ttsInicializado = false
    private var idioma: String = "pt"
    private var liçãoAtual = 0
    private var audioPausado = false

    // LISTA DE LIÇÕES DO MÓDULO SEGURANÇA - PORTUGUÊS
    private val liçõesPortugues = listOf(
        Lição(
            "Introdução à Segurança Digital",
            "A segurança digital é como ter um sistema de alarme para sua casa virtual - protege suas informações pessoais e seu dinheiro!\n\n" +
                    "Por que a segurança digital é importante:\n" +
                    "• Protege seus dados pessoais\n" +
                    "• Evite roubo de identidade\n" +
                    "• Protege seu dinheiro em contas bancárias\n" +
                    "• Mantém suas conversas privadas\n" +
                    "• Preserva suas fotos e documentos\n\n" +
                    "Pilares da segurança digital:\n" +
                    "• Senhas fortes e únicas\n" +
                    "• Antivírus atualizado\n" +
                    "• Cuidado com links suspeitos\n" +
                    "• Backup regular dos dados",
            "🛡️",
            "seguranca_introducao"
        ),
        Lição(
            "Criando Senhas Fortes",
            "Uma senha forte é como uma fechadura de alta segurança - difícil de ser violada!\n\n" +
                    "Características de uma senha forte:\n" +
                    "• Mínimo de 8 caracteres\n" +
                    "• Letras maiúsculas e minúsculas\n" +
                    "• Números (pelo menos 2)\n" +
                    "• Símbolos especiais (@, #, $, %)\n" +
                    "• Não use dados pessoais\n\n" +
                    "Exemplos de senhas fortes:\n" +
                    "• Correto: 'C@sa123Segura!'\n" +
                    "• Fraco: 'senha123'\n" +
                    "• Fraco: '01011980' (data de nascimento)\n" +
                    "• Fraco: 'nome123'\n\n" +
                    "Dica: Use frases que só você conhece!",
            "🔐",
            "senhas_fortes"
        ),
        Lição(
            "Gerenciador de Senhas",
            "Um gerenciador de senhas é como um cofre digital - guarda todas suas chaves com segurança!\n\n" +
                    "Vantagens do gerenciador:\n" +
                    "• Lembra todas as senhas por você\n" +
                    "• Gera senhas fortes automaticamente\n" +
                    "• Preenche automaticamente em sites\n" +
                    "• Sincroniza entre dispositivos\n\n" +
                    "Gerenciadores populares:\n" +
                    "• LastPass\n" +
                    "• 1Password\n" +
                    "• Bitwarden (gratuito)\n" +
                    "• Gerenciador do Google/Navegador\n\n" +
                    "Como usar:\n" +
                    "1. Crie uma conta no gerenciador\n" +
                    "2. Guarde sua senha mestra com segurança\n" +
                    "3. Adicione suas contas existentes\n" +
                    "4. Use para criar novas senhas",
            "🗄️",
            "gerenciador_senhas"
        ),
        Lição(
            "Autenticação em Duas Etapas",
            "A autenticação em duas etapas é como ter duas portas para entrar em casa - mesmo que alguém tenha a primeira chave, não passa da segunda!\n\n" +
                    "Como funciona:\n" +
                    "1. Digita sua senha normal (primeira etapa)\n" +
                    "2. Digita um código temporário (segunda etapa)\n" +
                    "3. Só então acessa sua conta\n\n" +
                    "Formas de receber o código:\n" +
                    "• SMS no celular\n" +
                    "• App autenticador (Google Authenticator)\n" +
                    "• E-mail de verificação\n" +
                    "• Biometria adicional\n\n" +
                    "Onde ativar:\n" +
                    "• Google, Facebook, Instagram\n" +
                    "• Bancos e apps financeiros\n" +
                    "• E-mail e redes sociais\n" +
                    "• Qualquer conta importante",
            "🔒",
            "autenticacao_duas_etapas"
        ),
        Lição(
            "Identificando Phishing e Golpes",
            "Phishing é como um pescador jogando isca - tenta fisgar você com ofertas falsas!\n\n" +
                    "Sinais de phishing:\n" +
                    "• E-mails com erros de português\n" +
                    "• Links que não batem com o nome\n" +
                    "• Urgência artificial ('sua conta será bloqueada')\n" +
                    "• Pedidos de dados pessoais\n" +
                    "• Endereços de e-mail estranhos\n\n" +
                    "Exemplos comuns:\n" +
                    "• 'Seu pacote não pode ser entregue'\n" +
                    "• 'Sua conta do banco será suspensa'\n" +
                    "• 'Você ganhou um prêmio'\n" +
                    "• 'Atualize seus dados agora'\n\n" +
                    "Dica: Sempre verifique o remetente!",
            "🎣",
            "phishing_golpes"
        ),
        Lição(
            "Navegação Segura na Internet",
            "Navegar seguro na internet é como andar em uma cidade desconhecida - precisa saber quais ruas evitar!\n\n" +
                    "Dicas para navegar com segurança:\n" +
                    "• Verifique se o site tem 'https://'\n" +
                    "• Olhe pelo cadeado na barra de endereços\n" +
                    "• Evite sites com muitos anúncios pop-up\n" +
                    "• Não baixe arquivos de fontes desconhecidas\n" +
                    "• Use antivírus atualizado\n\n" +
                    "Sinais de sites perigosos:\n" +
                    "• Sem 'https://' (apenas 'http://')\n" +
                    "• Muitos anúncios invasivos\n" +
                    "• Pop-ups pedindo instalação\n" +
                    "• URLs estranhas e complicadas\n\n" +
                    "Dica: Use extensões de segurança no navegador!",
            "🌐",
            "navegacao_segura_net"
        ),
        Lição(
            "Segurança em Redes Wi-Fi",
            "Usar Wi-Fi público é como conversar em voz alta em um lugar cheio - todos podem ouvir!\n\n" +
                    "Riscos do Wi-Fi público:\n" +
                    "• Hackers podem interceptar dados\n" +
                    "• Senhas podem ser roubadas\n" +
                    "• Suas atividades podem ser monitoradas\n" +
                    "• Dispositivos podem ser invadidos\n\n" +
                    "Como se proteger em Wi-Fi público:\n" +
                    "• Evite acessar bancos e compras\n" +
                    "• Use VPN (Rede Virtual Privada)\n" +
                    "• Desative compartilhamento de arquivos\n" +
                    "• Sempre faça logout após usar\n\n" +
                    "Dica: Prefira usar seus dados móveis!",
            "📡",
            "wifi_seguro"
        ),
        Lição(
            "Backup e Proteção de Dados",
            "Fazer backup é como ter uma cópia das chaves da sua casa - se perder uma, ainda tem outra!\n\n" +
                    "O que fazer backup:\n" +
                    "• Fotos e vídeos importantes\n" +
                    "• Documentos pessoais\n" +
                    "• Contatos do celular\n" +
                    "• Mensagens importantes\n" +
                    "• Configurações de apps\n\n" +
                    "Onde fazer backup:\n" +
                    "• Nuvem (Google Drive, iCloud)\n" +
                    "• HD externo\n" +
                    "• Pendrive\n" +
                    "• Computador\n\n" +
                    "Frequência recomendada:\n" +
                    "• Backup automático diário para nuvem\n" +
                    "• Backup manual mensal para HD externo\n\n" +
                    "Dica: Configure backup automático!",
            "💾",
            "backup_dados"
        ),
        Lição(
            "Privacidade em Redes Sociais",
            "Proteger sua privacidade nas redes sociais é como fechar as cortinas de casa - só mostra o que quer!\n\n" +
                    "Configurações de privacidade importantes:\n" +
                    "• Perfil privado (apenas amigos)\n" +
                    "• Limitar quem vê suas postagens\n" +
                    "• Controlar marcações em fotos\n" +
                    "• Revisar o que aparece na linha do tempo\n\n" +
                    "O que NÃO compartilhar:\n" +
                    "• Endereço completo\n" +
                    "• Telefone pessoal\n" +
                    "• Data de nascimento completa\n" +
                    "• Localização em tempo real\n" +
                    "• Fotos de documentos\n\n" +
                    "Dica: Revise suas configurações mensalmente!",
            "👤",
            "privacidade_redes_sociais"
        ),
        Lição(
            "Protegendo Dispositivos Móveis",
            "Proteger seu celular é como trancar sua casa - impede que estranhos entrem!\n\n" +
                    "Medidas essenciais de segurança:\n" +
                    "• Senha ou biometria para desbloquear\n" +
                    "• Aplicativos apenas da loja oficial\n" +
                    "• Atualizações do sistema em dia\n" +
                    "• Antivírus instalado\n" +
                    "• Cuidado com apps de fontes desconhecidas\n\n" +
                    "O que fazer se perder o celular:\n" +
                    "1. Bloqueie remotamente (Find My Device)\n" +
                    "2. Altere senhas de apps importantes\n" +
                    "3. Contate sua operadora\n" +
                    "4. Avisse seu banco\n\n" +
                    "Dica: Ative a localização do dispositivo!",
            "📱",
            "protecao_dispositivos"
        )
    )

    // LISTA DE LIÇÕES DO MÓDULO SEGURANÇA - ESPANHOL
    private val liçõesEspanol = listOf(
        Lição(
            "Introducción a la Seguridad Digital",
            "¡La seguridad digital es como tener un sistema de alarma para tu casa virtual - protege tus informaciones personales y tu dinero!\n\n" +
                    "Por qué la seguridad digital es importante:\n" +
                    "• Protege tus datos personales\n" +
                    "• Evita robo de identidad\n" +
                    "• Protege tu dinero en cuentas bancarias\n" +
                    "• Mantiene tus conversaciones privadas\n" +
                    "• Preserva tus fotos y documentos\n\n" +
                    "Pilares de la seguridad digital:\n" +
                    "• Contraseñas fuertes y únicas\n" +
                    "• Antivirus actualizado\n" +
                    "• Cuidado con enlaces sospechosos\n" +
                    "• Backup regular de los datos",
            "🛡️",
            "seguridad_introduccion"
        ),
        Lição(
            "Creando Contraseñas Fuertes",
            "¡Una contraseña fuerte es como una cerradura de alta seguridad - difícil de violar!\n\n" +
                    "Características de una contraseña fuerte:\n" +
                    "• Mínimo de 8 caracteres\n" +
                    "• Letras mayúsculas y minúsculas\n" +
                    "• Números (por lo menos 2)\n" +
                    "• Símbolos especiales (@, #, $, %)\n" +
                    "• No uses datos personales\n\n" +
                    "Ejemplos de contraseñas fuertes:\n" +
                    "• Correcto: 'C@sa123Segura!'\n" +
                    "• Débil: 'contraseña123'\n" +
                    "• Débil: '01011980' (fecha de nacimiento)\n" +
                    "• Débil: 'nombre123'\n\n" +
                    "Consejo: ¡Usa frases que solo tú conoces!",
            "🔐",
            "contrasenas_fuertes"
        ),
        Lição(
            "Gestor de Contraseñas",
            "¡Un gestor de contraseñas es como una caja fuerte digital - guarda todas tus llaves con seguridad!\n\n" +
                    "Ventajas del gestor:\n" +
                    "• Recuerda todas las contraseñas por ti\n" +
                    "• Genera contraseñas fuertes automáticamente\n" +
                    "• Completa automáticamente en sitios\n" +
                    "• Sincroniza entre dispositivos\n\n" +
                    "Gestores populares:\n" +
                    "• LastPass\n" +
                    "• 1Password\n" +
                    "• Bitwarden (gratuito)\n" +
                    "• Gestor de Google/Navegador\n\n" +
                    "Cómo usar:\n" +
                    "1. Crea una cuenta en el gestor\n" +
                    "2. Guarda tu contraseña maestra con seguridad\n" +
                    "3. Agrega tus cuentas existentes\n" +
                    "4. Usa para crear nuevas contraseñas",
            "🗄️",
            "gestor_contrasenas"
        ),
        Lição(
            "Autenticación en Dos Pasos",
            "¡La autenticación en dos pasos es como tener dos puertas para entrar en casa - incluso si alguien tiene la primera llave, no pasa de la segunda!\n\n" +
                    "Cómo funciona:\n" +
                    "1. Digita tu contraseña normal (primer paso)\n" +
                    "2. Digita un código temporal (segundo paso)\n" +
                    "3. Solo entonces accedes a tu cuenta\n\n" +
                    "Formas de recibir el código:\n" +
                    "• SMS en el celular\n" +
                    "• App autenticador (Google Authenticator)\n" +
                    "• E-mail de verificación\n" +
                    "• Biometría adicional\n\n" +
                    "Dónde activar:\n" +
                    "• Google, Facebook, Instagram\n" +
                    "• Bancos y apps financieras\n" +
                    "• E-mail y redes sociales\n" +
                    "• Cualquier cuenta importante",
            "🔒",
            "autenticacion_dos_pasos"
        ),
        Lição(
            "Identificando Phishing y Estafas",
            "¡Phishing es como un pescador lanzando carnada - intenta pescarte con ofertas falsas!\n\n" +
                    "Señales de phishing:\n" +
                    "• E-mails con errores de español\n" +
                    "• Enlaces que no coinciden con el nombre\n" +
                    "• Urgencia artificial ('tu cuenta será bloqueada')\n" +
                    "• Solicitudes de datos personales\n" +
                    "• Direcciones de e-mail extrañas\n\n" +
                    "Ejemplos comunes:\n" +
                    "• 'Tu paquete no pudo ser entregado'\n" +
                    "• 'Tu cuenta del banco será suspendida'\n" +
                    "• 'Ganaste un premio'\n" +
                    "• 'Actualiza tus datos ahora'\n\n" +
                    "Consejo: ¡Siempre verifica el remitente!",
            "🎣",
            "phishing_estafas"
        ),
        Lição(
            "Navegación Segura en Internet",
            "¡Navegar seguro en internet es como caminar en una ciudad desconocida - necesita saber qué calles evitar!\n\n" +
                    "Consejos para navegar con seguridad:\n" +
                    "• Verifica si el sitio tiene 'https://'\n" +
                    "• Mira por el candado en la barra de dirección\n" +
                    "• Evita sitios con muchos anuncios pop-up\n" +
                    "• No descargues archivos de fuentes desconocidas\n" +
                    "• Usa antivirus actualizado\n\n" +
                    "Señales de sitios peligrosos:\n" +
                    "• Sin 'https://' (solo 'http://')\n" +
                    "• Muchos anuncios invasivos\n" +
                    "• Pop-ups pidiendo instalación\n" +
                    "• URLs extrañas y complicadas\n\n" +
                    "Consejo: ¡Usa extensiones de seguridad en el navegador!",
            "🌐",
            "navegacion_segura"
        ),
        Lição(
            "Seguridad en Redes Wi-Fi",
            "¡Usar Wi-Fi público es como conversar en voz alta en un lugar lleno - todos pueden oír!\n\n" +
                    "Riesgos del Wi-Fi público:\n" +
                    "• Hackers pueden interceptar datos\n" +
                    "• Contraseñas pueden ser robadas\n" +
                    "• Tus actividades pueden ser monitoreadas\n" +
                    "• Dispositivos pueden ser invadidos\n\n" +
                    "Cómo protegerse en Wi-Fi público:\n" +
                    "• Evita acceder a bancos y compras\n" +
                    "• Usa VPN (Red Virtual Privada)\n" +
                    "• Desactiva compartir archivos\n" +
                    "• Siempre cierra sesión después de usar\n\n" +
                    "Consejo: ¡Prefiere usar tus datos móviles!",
            "📡",
            "wifi_seguro"
        ),
        Lição(
            "Backup y Protección de Datos",
            "¡Hacer backup es como tener una copia de las llaves de tu casa - si pierdes una, todavía tienes otra!\n\n" +
                    "Qué hacer backup:\n" +
                    "• Fotos y videos importantes\n" +
                    "• Documentos personales\n" +
                    "• Contactos del celular\n" +
                    "• Mensajes importantes\n" +
                    "• Configuraciones de apps\n\n" +
                    "Dónde hacer backup:\n" +
                    "• Nube (Google Drive, iCloud)\n" +
                    "• Disco duro externo\n" +
                    "• Pendrive\n" +
                    "• Computadora\n\n" +
                    "Frecuencia recomendada:\n" +
                    "• Backup automático diario para nube\n" +
                    "• Backup manual mensual para disco externo\n\n" +
                    "Consejo: ¡Configura backup automático!",
            "💾",
            "backup_datos"
        ),
        Lição(
            "Privacidad en Redes Sociales",
            "¡Proteger tu privacidad en redes sociales es como cerrar las cortinas de casa - solo muestra lo que quieres!\n\n" +
                    "Configuraciones de privacidad importantes:\n" +
                    "• Perfil privado (solo amigos)\n" +
                    "• Limitar quién ve tus publicaciones\n" +
                    "• Controlar etiquetas en fotos\n" +
                    "• Revisar lo que aparece en la línea de tiempo\n\n" +
                    "Qué NO compartir:\n" +
                    "• Dirección completa\n" +
                    "• Teléfono personal\n" +
                    "• Fecha de nacimiento completa\n" +
                    "• Ubicación en tiempo real\n" +
                    "• Fotos de documentos\n\n" +
                    "Consejo: ¡Revisa tus configuraciones mensualmente!",
            "👤",
            "privacidad_redes_sociales"
        ),
        Lição(
            "Protegiendo Dispositivos Móviles",
            "¡Proteger tu celular es como cerrar con llave tu casa - impide que extraños entren!\n\n" +
                    "Medidas esenciales de seguridad:\n" +
                    "• Contraseña o biometría para desbloquear\n" +
                    "• Aplicaciones solo de la tienda oficial\n" +
                    "• Actualizaciones del sistema al día\n" +
                    "• Antivirus instalado\n" +
                    "• Cuidado con apps de fuentes desconocidas\n\n" +
                    "Qué hacer si pierdes el celular:\n" +
                    "1. Bloquea remotamente (Find My Device)\n" +
                    "2. Cambia contraseñas de apps importantes\n" +
                    "3. Contacta tu operadora\n" +
                    "4. Avisa tu banco\n\n" +
                    "Consejo: ¡Activa la ubicación del dispositivo!",
            "📱",
            "proteccion_dispositivos"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desktop)

        // 1. DETECTAR IDIOMA
        idioma = intent.getStringExtra("IDIOMA") ?: "pt"

        // 2. MUDAR TÍTULO DA TELA
        val txtTituloTela = findViewById<TextView>(R.id.txtTituloTela)
        txtTituloTela.text = if (idioma == "es" ) "Seguridad - Seguridad Digital" else "Segurança - Segurança Digital"

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
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("IDIOMA", idioma)
            intent.putExtra("MODULO", "Segurança") // DEVE SER "Segurança"
            startActivity(intent)
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

