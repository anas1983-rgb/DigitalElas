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


class TrabalhoActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private var ttsInicializado = false
    private var idioma: String = "pt"
    private var liçãoAtual = 0
    private var audioPausado = false


    // LISTA DE LIÇÕES DO MÓDULO TRABALHO - PORTUGUÊS
    private val liçõesPortugues = listOf(
        Lição(
            "Preparando para o Mercado de Trabalho",
            "Entrar no mercado de trabalho é como começar uma nova jornada - requer preparação e planejamento!\n\n" +
                    "• Currículo: Sua carta de apresentação profissional\n" +
                    "• Entrevista: Conversa para conhecer a empresa e ser conhecido\n" +
                    "• Carta de apresentação: Texto que acompanha o currículo\n" +
                    "• Portfólio: Coleção dos seus melhores trabalhos\n" +
                    "• Linkedin: Rede social profissional\n\n" +
                    "Primeiros passos:\n" +
                    "1. Identifique suas habilidades e interesses\n" +
                    "2. Pesquise empresas e áreas de atuação\n" +
                    "3. Prepare documentos profissionais\n" +
                    "4. Pratique para entrevistas",
            "🎯",
            "trabalho_preparacao"
        ),
        Lição(
            "Criando um Currículo Atraente",
            "O currículo é seu cartão de visita profissional - deve ser claro, organizado e destacar suas qualidades!\n\n" +
                    "Partes essenciais do currículo:\n" +
                    "• Dados pessoais: Nome, contato, localização\n" +
                    "• Objetivo profissional: O que você busca\n" +
                    "• Formação acadêmica: Cursos e estudos\n" +
                    "• Experiência profissional: Onde já trabalhou\n" +
                    "• Habilidades: O que você sabe fazer\n\n" +
                    "Dicas importantes:\n" +
                    "• Seja honesto e verdadeiro\n" +
                    "• Use linguagem profissional\n" +
                    "• Revise antes de enviar\n" +
                    "• Adapte para cada vaga",
            "📄",
            "curriculo_criacao"
        ),
        Lição(
            "LinkedIn - Sua Rede Profissional",
            "O LinkedIn é como uma feira de empregos digital - conecta profissionais e oportunidades!\n\n" +
                    "Como criar um perfil atrativo:\n" +
                    "• Foto profissional: Sorridente e apropriada\n" +
                    "• Cabeçalho claro: Sua área de atuação\n" +
                    "• Resumo interessante: Conte sua história\n" +
                    "• Experiências detalhadas: Descreva suas funções\n" +
                    "• Habilidades endossadas: O que outros confirmam\n\n" +
                    "Como usar:\n" +
                    "1. Conecte-se com colegas e profissionais\n" +
                    "2. Participe de grupos da sua área\n" +
                    "3. Compartilhe conteúdo relevante\n" +
                    "4. Busque oportunidades",
            "💼",
            "linkedin_perfil"
        ),
        Lição(
            "Preparando para Entrevistas",
            "A entrevista é como um primeiro encontro profissional - impressione com confiança e preparação!\n\n" +
                    "Antes da entrevista:\n" +
                    "• Pesquise sobre a empresa\n" +
                    "• Pratique respostas comuns\n" +
                    "• Escolha roupa adequada\n" +
                    "• Chegue com antecedência\n" +
                    "• Leve documentos necessários\n\n" +
                    "Durante a entrevista:\n" +
                    "• Mantenha contato visual\n" +
                    "• Fale claramente e ouça atentamente\n" +
                    "• Seja positivo e entusiasmado\n" +
                    "• Faça perguntas inteligentes",
            "🤝",
            "entrevista_preparacao"
        ),
        Lição(
            "Comunicação no Ambiente de Trabalho",
            "Comunicar-se bem no trabalho é como falar a mesma língua - evita mal-entendidos e constrói confiança!\n\n" +
                    "Formas de comunicação profissional:\n" +
                    "• E-mails: Para assuntos formais e documentados\n" +
                    "• Reuniões: Para discussões em grupo\n" +
                    "• Mensagens instantâneas: Para questões rápidas\n" +
                    "• Telefonemas: Para urgências e conversas pessoais\n\n" +
                    "Dicas de comunicação eficaz:\n" +
                    "• Seja claro e objetivo\n" +
                    "• Escute mais do que fala\n" +
                    "• Respeite opiniões diferentes\n" +
                    "• Peça feedback regularmente",
            "🗣️",
            "comunicacao_trabalho"
        ),
        Lição(
            "Trabalho em Equipe",
            "Trabalhar em equipe é como tocar em uma orquestra - cada um tem seu instrumento, mas a música é uma só!\n\n" +
                    "Características de bons colegas de equipe:\n" +
                    "• Colaborativos: Ajudam os outros\n" +
                    "• Responsáveis: Cumprem suas tarefas\n" +
                    "• Flexíveis: Adaptam-se às mudanças\n" +
                    "• Respeitosos: Valorizam as diferenças\n" +
                    "• Proativos: Antecipam necessidades\n\n" +
                    "Como contribuir positivamente:\n" +
                    "1. Conheça seu papel na equipe\n" +
                    "2. Comunique-se abertamente\n" +
                    "3. Compartilhe conhecimentos\n" +
                    "4. Celebre conquistas coletivas",
            "👥",
            "trabalho_equipe"
        ),
        Lição(
            "Gestão do Tempo e Produtividade",
            "Administrar bem o tempo é como organizar uma mala - tudo tem seu lugar e momento certo!\n\n" +
                    "Técnicas de produtividade:\n" +
                    "• Lista de tarefas: Organize por prioridade\n" +
                    "• Prazos realistas: Estime tempo adequado\n" +
                    "• Elimine distrações: Foque no importante\n" +
                    "• Faça pausas: Recarregue as energias\n" +
                    "• Revise resultados: Aprenda com experiências\n\n" +
                    "Ferramentas úteis:\n" +
                    "• Agenda digital ou física\n" +
                    "• Aplicativos de organização\n" +
                    "• Lembretes e alertas\n" +
                    "• Planejamento semanal",
            "⏰",
            "gestao_tempo"
        ),
        Lição(
            "Direitos Trabalhistas Básicos",
            "Conhecer seus direitos é como ter um mapa na jornada profissional - protege você de se perder!\n\n" +
                    "Direitos fundamentais:\n" +
                    "• Salário: Pagamento pelo trabalho realizado\n" +
                    "• Férias: Período de descanso remunerado\n" +
                    "• 13º salário: Gratificação natalina\n" +
                    "• FGTS: Fundo de garantia\n" +
                    "• Horas extras: Pagamento por horas extras\n\n" +
                    "Documentos importantes:\n" +
                    "• Carteira de trabalho: Registro profissional\n" +
                    "• Contrato de trabalho: Acordo com empresa\n" +
                    "• Holerite: Comprovante de pagamento\n" +
                    "• Comprovantes de contribuição",
            "⚖️",
            "direitos_trabalhistas"
        ),
        Lição(
            "Desenvolvimento Profissional Contínuo",
            "Aprender sempre é como regar uma planta - faz crescer e florescer profissionalmente!\n\n" +
                    "Formas de se desenvolver:\n" +
                    "• Cursos online: Aprendizado flexível\n" +
                    "• Workshops: Habilidades práticas\n" +
                    "• Leitura: Conhecimento teórico\n" +
                    "• Networking: Troca de experiências\n" +
                    "• Mentoria: Aprendizado com experientes\n\n" +
                    "Benefícios do desenvolvimento contínuo:\n" +
                    "• Melhora suas habilidades\n" +
                    "• Aumenta oportunidades\n" +
                    "• Mantém-se atualizado\n" +
                    "• Cresce profissionalmente",
            "📚",
            "desenvolvimento_profissional"
        ),
        Lição(
            "Equilíbrio entre Vida Pessoal e Profissional",
            "Equilibrar trabalho e vida pessoal é como andar de bicicleta - precisa manter o equilíbrio para não cair!\n\n" +
                    "Estratégias de equilíbrio:\n" +
                    "• Estabeleça horários: Separe trabalho e descanso\n" +
                    "• Aprenda a dizer não: Evite sobrecarga\n" +
                    "• Cuide da saúde: Alimentação e exercícios\n" +
                    "• Reserve tempo para lazer: Família e hobbies\n" +
                    "• Desconecte-se: Desligue do trabalho\n\n" +
                    "Sinais de desequilíbrio:\n" +
                    "• Cansaço constante\n" +
                    "• Irritabilidade\n" +
                    "• Dificuldade para dormir\n" +
                    "• Falta de tempo para si",
            "⚖️",
            "equilibrio_vida_trabalho"
        )
    )

    // LISTA DE LIÇÕES DO MÓDULO TRABALHO - ESPANHOL
    private val liçõesEspanol = listOf(
        Lição(
            "Preparándose para el Mercado Laboral",
            "¡Entrar al mercado laboral es como comenzar un nuevo viaje - requiere preparación y planificación!\n\n" +
                    "• Currículum: Tu carta de presentación profesional\n" +
                    "• Entrevista: Conversación para conocer la empresa y ser conocido\n" +
                    "• Carta de presentación: Texto que acompaña el currículum\n" +
                    "• Portafolio: Colección de tus mejores trabajos\n" +
                    "• LinkedIn: Red social profesional\n\n" +
                    "Primeros pasos:\n" +
                    "1. Identifica tus habilidades e intereses\n" +
                    "2. Investiga empresas y áreas de actuación\n" +
                    "3. Prepara documentos profesionales\n" +
                    "4. Practica para entrevistas",
            "🎯",
            "trabajo_preparacion"
        ),
        Lição(
            "Creando un Currículum Atractivo",
            "¡El currículum es tu tarjeta de presentación profesional - debe ser claro, organizado y destacar tus cualidades!\n\n" +
                    "Partes esenciales del currículum:\n" +
                    "• Datos personales: Nombre, contacto, ubicación\n" +
                    "• Objetivo profesional: Lo que buscas\n" +
                    "• Formación académica: Cursos y estudios\n" +
                    "• Experiencia profesional: Donde has trabajado\n" +
                    "• Habilidades: Lo que sabes hacer\n\n" +
                    "Consejos importantes:\n" +
                    "• Sé honesto y veraz\n" +
                    "• Usa lenguaje profesional\n" +
                    "• Revisa antes de enviar\n" +
                    "• Adapta para cada vacante",
            "📄",
            "curriculum_creacion"
        ),
        Lição(
            "LinkedIn - Tu Red Profesional",
            "¡LinkedIn es como una feria de empleo digital - conecta profesionales y oportunidades!\n\n" +
                    "Cómo crear un perfil atractivo:\n" +
                    "• Foto profesional: Sonriente y apropiada\n" +
                    "• Encabezado claro: Tu área de actuación\n" +
                    "• Resumen interesante: Cuenta tu historia\n" +
                    "• Experiencias detalladas: Describe tus funciones\n" +
                    "• Habilidades respaldadas: Lo que otros confirman\n\n" +
                    "Cómo usar:\n" +
                    "1. Conéctate con colegas y profesionales\n" +
                    "2. Participa en grupos de tu área\n" +
                    "3. Comparte contenido relevante\n" +
                    "4. Busca oportunidades",
            "💼",
            "linkedin_perfil"
        ),
        Lição(
            "Preparándose para Entrevistas",
            "¡La entrevista es como una primera cita profesional - impresiona con confianza y preparación!\n\n" +
                    "Antes de la entrevista:\n" +
                    "• Investiga sobre la empresa\n" +
                    "• Practica respuestas comunes\n" +
                    "• Elige ropa adecuada\n" +
                    "• Llega con anticipación\n" +
                    "• Lleva documentos necesarios\n\n" +
                    "Durante la entrevista:\n" +
                    "• Mantén contacto visual\n" +
                    "• Habla claramente y escucha atentamente\n" +
                    "• Sé positivo y entusiasta\n" +
                    "• Haz preguntas inteligentes",
            "🤝",
            "entrevista_preparacion"
        ),
        Lição(
            "Comunicación en el Ambiente Laboral",
            "¡Comunicarse bien en el trabajo es como hablar el mismo idioma - evita malentendidos y construye confianza!\n\n" +
                    "Formas de comunicación profesional:\n" +
                    "• Correos electrónicos: Para asuntos formales y documentados\n" +
                    "• Reuniones: Para discusiones en grupo\n" +
                    "• Mensajes instantáneos: Para cuestiones rápidas\n" +
                    "• Llamadas telefónicas: Para urgencias y conversaciones personales\n\n" +
                    "Consejos de comunicación efectiva:\n" +
                    "• Sé claro y objetivo\n" +
                    "• Escucha más de lo que hablas\n" +
                    "• Respeta opiniones diferentes\n" +
                    "• Pide feedback regularmente",
            "🗣️",
            "comunicacion_trabajo"
        ),
        Lição(
            "Trabajo en Equipo",
            "¡Trabajar en equipo es como tocar en una orquesta - cada uno tiene su instrumento, pero la música es una sola!\n\n" +
                    "Características de buenos compañeros de equipo:\n" +
                    "• Colaborativos: Ayudan a los demás\n" +
                    "• Responsables: Cumplen sus tareas\n" +
                    "• Flexibles: Se adaptan a los cambios\n" +
                    "• Respetuosos: Valoran las diferencias\n" +
                    "• Proactivos: Anticipan necesidades\n\n" +
                    "Cómo contribuir positivamente:\n" +
                    "1. Conoce tu rol en el equipo\n" +
                    "2. Comunícate abiertamente\n" +
                    "3. Comparte conocimientos\n" +
                    "4. Celebra logros colectivos",
            "👥",
            "trabajo_equipo"
        ),
        Lição(
            "Gestión del Tiempo y Productividad",
            "¡Administrar bien el tiempo es como organizar una maleta - todo tiene su lugar y momento adecuado!\n\n" +
                    "Técnicas de productividad:\n" +
                    "• Lista de tareas: Organiza por prioridad\n" +
                    "• Plazos realistas: Estima tiempo adecuado\n" +
                    "• Elimina distracciones: Enfócate en lo importante\n" +
                    "• Haz pausas: Recarga energías\n" +
                    "• Revisa resultados: Aprende de experiencias\n\n" +
                    "Herramientas útiles:\n" +
                    "• Agenda digital o física\n" +
                    "• Aplicaciones de organización\n" +
                    "• Recordatorios y alertas\n" +
                    "• Planificación semanal",
            "⏰",
            "gestion_tiempo"
        ),
        Lição(
            "Derechos Laborales Básicos",
            "¡Conocer tus derechos es como tener un mapa en el viaje profesional - te protege de perderte!\n\n" +
                    "Derechos fundamentales:\n" +
                    "• Salario: Pago por trabajo realizado\n" +
                    "• Vacaciones: Período de descanso remunerado\n" +
                    "• Aguinaldo: Gratificación navideña\n" +
                    "• Fondo de garantía: Ahorro laboral\n" +
                    "• Horas extras: Pago por horas adicionales\n\n" +
                    "Documentos importantes:\n" +
                    "• Libreta de trabajo: Registro profesional\n" +
                    "• Contrato de trabajo: Acuerdo con empresa\n" +
                    "• Recibo de pago: Comprobante de salario\n" +
                    "• Comprobantes de contribución",
            "⚖️",
            "derechos_laborales"
        ),
        Lição(
            "Desarrollo Profesional Continuo",
            "¡Aprender siempre es como regar una planta - hace crecer y florecer profesionalmente!\n\n" +
                    "Formas de desarrollarse:\n" +
                    "• Cursos online: Aprendizaje flexible\n" +
                    "• Talleres: Habilidades prácticas\n" +
                    "• Lectura: Conocimiento teórico\n" +
                    "• Networking: Intercambio de experiencias\n" +
                    "• Mentoría: Aprendizaje con expertos\n\n" +
                    "Beneficios del desarrollo continuo:\n" +
                    "• Mejora tus habilidades\n" +
                    "• Aumenta oportunidades\n" +
                    "• Te mantiene actualizado\n" +
                    "• Crecimiento profesional",
            "📚",
            "desarrollo_profesional"
        ),
        Lição(
            "Equilibrio entre Vida Personal y Profesional",
            "¡Equilibrar trabajo y vida personal es como andar en bicicleta - necesita mantener el equilibrio para no caer!\n\n" +
                    "Estrategias de equilibrio:\n" +
                    "• Establece horarios: Separa trabajo y descanso\n" +
                    "• Aprende a decir no: Evita sobrecarga\n" +
                    "• Cuida tu salud: Alimentación y ejercicio\n" +
                    "• Reserva tiempo para ocio: Familia y pasatiempos\n" +
                    "• Desconéctate: Apaga del trabajo\n\n" +
                    "Señales de desequilibrio:\n" +
                    "• Cansancio constante\n" +
                    "• Irritabilidad\n" +
                    "• Dificultad para dormir\n" +
                    "• Falta de tiempo para ti",
            "⚖️",
            "equilibrio_vida_trabajo"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desktop)

        // 1. DETECTAR IDIOMA
        idioma = intent.getStringExtra("IDIOMA") ?: "pt"

        // 2. MUDAR TÍTULO DA TELA
        val txtTituloTela = findViewById<TextView>(R.id.txtTituloTela)
        txtTituloTela.text = if (idioma == "es" ) "Trabajo - Mundo del Trabajo" else "Trabalho - Mundo do trabalho"

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
            intent.putExtra("MODULO", "Trabalho") // DEVE SER "Trabalho"
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


