package com.ana.digitalelas

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale


data class PerguntaQuiz(
    val pergunta: String,
    val opcoes: List<String>,
    val respostaCorreta: Int,
    val explicacao: String
)

class QuizActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private var ttsInicializado = false
    private var idioma: String = "pt"
    private var modulo: String = "Celular"
    private lateinit var txtAudioEmTempoReal: TextView

    // Componentes da UI
    private lateinit var txtPerguntaQuiz: TextView
    private lateinit var radioGroupOpcoes: RadioGroup
    private lateinit var btnProximaPergunta: Button
    private lateinit var btnOuvirQuiz: ImageButton
    private lateinit var progressBarQuiz: ProgressBar
    private lateinit var txtProgressoQuiz: TextView
    private lateinit var txtExplicacao: TextView
    private lateinit var btnAvancarQuiz: Button

    // Dados do quiz
    private var perguntaAtual = 0
    private var pontuacao = 0
    private lateinit var perguntas: List<PerguntaQuiz>


    // QUIZZES UNIFICADOS E ORGANIZADOS
    private val quizCelularPortugues = listOf(
        PerguntaQuiz(
            "Qual parte do celular é considerada como 'janela para o mundo digital'?",
            listOf(
                "1- Botão Power - liga e desliga o aparelho",
                "2-Câmera - tira fotos e vídeos",
                "3-Alto-falante - emite sons e músicas",
                "4-Tela Touch - permite interagir com toques"
            ),
            3,//as opções "vão de 0 a 3"(0,1,2,3)//
            "A tela touch é realmente a janela para o mundo digital, onde interagimos com tudo."
        ),
        PerguntaQuiz(
            "Qual é a sequência CORRETA para salvar um novo contato?",
            listOf(
                "1-Ligar → Discar número → Salvar automaticamente",
                "2-Contatos → Adicionar → Digitar nome/número → Salvar",
                "3-Mensagens → Nova mensagem → Digitar número",
                "4-Câmera → Tirar foto → Salvar como contato"
            ),
            1,
            "A sequência correta é: abrir Contatos, clicar em Adicionar, preencher os dados e Salvar."

    ),
    PerguntaQuiz(
    "Qual destes é um aplicativo de mensagens instantâneas?",
    listOf(
        "1-SMS - sistema tradicional de texto",
        "2-Email - mensagem formal por email",
        "3-Telefone - apenas para ligações",
        "4-WhatsApp - mensagens pela internet"
    ),
    3,
    "Correto: WhatsApp é o principal aplicativo de mensagens instantâneas pela internet."
    ),
    PerguntaQuiz(
    "Para tirar uma selfie (foto de si mesmo), qual câmera devemos usar?",
    listOf(
        "1-Câmera traseira - fica atrás do celular",
        "2-Câmera frontal - fica na frente do celular",
    "3-Câmera traseira com espelho",
    "4-Câmera de vídeo - apenas para gravar"
    ),
    1,
    "Correto: A câmera frontal, localizada na parte da frente do celular, é usada para selfies."
    ),
    PerguntaQuiz(
    "O que é necessário para acessar a internet pelo navegador?",
    listOf(
    "1-Conexão com Wi-Fi ou dados móveis",
    "2-Apenas ter o aplicativo instalado",
    "3-Ter muitos contatos salvos",
    "4-Câmera com boa qualidade"
    ),
    0,
    "Correto: Para navegar na internet é essencial ter conexão Wi-Fi ou plano de dados ativo."
    ),
    PerguntaQuiz(
    "Onde podemos baixar novos aplicativos para o celular?",
    listOf(
        "1-Configurações do sistema",
        "2-Galeria de fotos",
        "3-Aplicativo de mensagens",
        "4-Play Store (Android) ou App Store (iPhone)"
    ),
    3,
    "Correto: A Play Store (Android) e App Store (iPhone) são as lojas oficiais de aplicativos."
    ),
    PerguntaQuiz(
    "Qual configuração ajuda a economizar bateria do celular?",
    listOf(
        "1-Aumentar o volume máximo",
        "2-Deixar o Wi-Fi sempre ligado",
        "3-Reduzir o brilho da tela",
        "4-Usar papel de parede animado"
    ),
    2,
    "Correto: Reduzir o brilho da tela é uma das formas mais eficazes de economizar bateria."
    ),
    PerguntaQuiz(
    "Qual destes é um método seguro de bloquear o celular?",
    listOf(
        "1-Data de nascimento - fácil de lembrar",
        "2-sequência simples /1234",
        " 3-Sem senha - mais prático",
        "4-Senha numérica (PIN) - apenas você sabe"
    ),
    3,
    "Correto: Uma senha numérica (PIN) que só você conhece é um método seguro de proteção."
    ),
    PerguntaQuiz(
    "O que significa quando a bateria está no vermelho?",
    listOf(
    "1-Bateria fraca - precisa carregar logo",
    "2-Bateria carregando - conectada na tomada",
    "3-Bateria cheia - 100% de carga",
    "4-Bateria com problema - precisa trocar"
    ),
    0,
    "Correto: Bateria no vermelho indica nível baixo e necessidade de carregamento urgente."
    ),
    PerguntaQuiz(
    "Qual recurso de acessibilidade LÊ em voz alta o que está na tela?",
    listOf(

    "1-Fonte maior - aumenta letras",
        "2-Leitor de tela - narra textos e botões",
    "3-Alto contraste - cores mais fortes",
    "4-Legendas - texto em vídeos"
    ),
    1,
    "Correto: O leitor de tela é o recurso que fala o conteúdo da tela, ideal para pessoas com deficiência visual."
    )
    )


    private val quizCelularEspanol = listOf(
        PerguntaQuiz(
            "¿Qué parte del celular se considera como 'ventana al mundo digital'?",
            listOf(

                "1-Botón Power - enciende y apaga el aparato",
                "2-Cámara - toma fotos y videos",
                "3-Pantalla Táctil - permite interactuar con toques",
                "4-Altavoz - emite sonidos y música"
            ),
            2,
            "La pantalla táctil es realmente la ventana al mundo digital, donde interactuamos con todo."

    ),
    PerguntaQuiz(
    "¿Cuál es la secuencia CORRECTA para guardar un nuevo contacto?",
    listOf(

    "1-Llamar → Marcar número → Guardar automáticamente",
    "2-Mensajes → Nuevo mensaje → Escribir número",
    "3-Cámara → Tomar foto → Guardar como contacto",
        "4-Contactos → Agregar → Escribir nombre/número → Guardar"
    ),
    3,
    "¡Correcto: La secuencia correcta es: abrir Contactos, hacer clic en Agregar, completar datos y Guardar."
    ),
    PerguntaQuiz(
    "¿Cuál de estos es una aplicación de mensajería instantánea?",
    listOf(

    "1-SMS - sistema tradicional de texto",
        "2-WhatsApp - mensajes por internet",
    "3-Email - mensaje formal por correo",
    "4-Teléfono - solo para llamadas"
    ),
    1,
    "¡Correcto: WhatsApp es la principal aplicación de mensajería instantánea por internet."
    ),
    PerguntaQuiz(
    "Para tomar una selfie (foto de uno mismo), ¿qué cámara debemos usar?",
    listOf(

    "1-Cámara trasera - está atrás del celular",
    "2-Cámara trasera con espejo",
        "3-Cámara frontal - está en el frente del celular",
    "4-Cámara de video - solo para grabar"
    ),
    2,
    "¡Correcto: La cámara frontal, ubicada en la parte delantera del celular, se usa para selfies."
    ),
    PerguntaQuiz(
    "¿Qué se necesita para acceder a internet por el navegador?",
    listOf(
    "1-Conexión Wi-Fi o datos móviles",
    "2-Solo tener la aplicación instalada",
    "3-Tener muchos contactos guardados",
    "4-Cámara con buena calidad"
    ),
    0,
    "¡Correcto: Para navegar por internet es esencial tener conexión Wi-Fi o plan de datos activo."
    ),
    PerguntaQuiz(
    "¿Dónde podemos descargar nuevas aplicaciones para el celular?",
    listOf(
    "1-Play Store (Android) o App Store (iPhone)",
    "2-Configuración del sistema",
    "3-Galería de fotos",
    "4-Aplicación de mensajes"
    ),
    0,
    "¡Correcto: Play Store (Android) y App Store (iPhone) son las tiendas oficiales de aplicaciones."
    ),
    PerguntaQuiz(
    "¿Qué configuración ayuda a ahorrar batería del celular?",
    listOf(

    "1-Aumentar el volumen al máximo",
    "2-Dejar el Wi-Fi siempre activado",
    "3-Usar fondo de pantalla animado",
        "4-Reducir el brillo de la pantalla"
    ),
    3,
    "¡Correcto! Reducir el brillo de la pantalla es una de las formas más efectivas de ahorrar batería."
    ),
    PerguntaQuiz(
    "¿Cuál de estos es un método seguro para bloquear el celular?",
    listOf(

    "1-Fecha de nacimiento - fácil de recordar",
    "2-secuencia simple/1234",
        "3-Contraseña numérica (PIN) - solo tú sabes",
    "4-Sin contraseña - más práctico"
    ),
    2,
    "¡Correcto: Una contraseña numérica (PIN) que solo tú conoces es un método seguro de protección."
    ),
    PerguntaQuiz(
    "¿Qué significa cuando la batería está en rojo?",
    listOf(
    "1-Batería baja - necesita cargar pronto",
    "2-Batería cargando - conectada al enchufe",
    "3-Batería llena - 100% de carga",
    "4-Batería con problema - necesita cambiar"
    ),
    0,
    "¡Correcto: Batería en rojo indica nivel bajo y necesidad de carga urgente."
    ),
    PerguntaQuiz(
    "¿Qué recurso de accesibilidad LEE en voz alta lo que está en la pantalla?",
    listOf(

    "1-Fuente más grande - aumenta letras",
        "2-Lector de pantalla - narra textos y botones",
    "3-Alto contraste - colores más fuertes",
    "4-Subtítulos - texto en videos"
    ),
    1,
    "¡Correcto: El leitor de pantalla es el recurso que habla el contenido de la pantalla, ideal para personas con discapacidad visual."
    )
    )

    private val quizDesktopPortugues = listOf(
        PerguntaQuiz(
            "Qual parte do computador é considerada o 'cérebro'?",
            listOf( "1-Monitor", "2-Teclado","3-CPU", "4-Mouse"),
            2,
            "A CPU (Unidade Central de Processamento) é o cérebro do computador, responsável por processar todas as informações e cálculos."
        ),
        PerguntaQuiz(
            "O que o Menu Iniciar representa na analogia da sala?",
            listOf("1-O chão","2-A porta de entrada", "3-A janela",  "4-O teto"),
            1,
            "O Menu Iniciar é como a porta de entrada que dá acesso a todos os programas e funções do computador."
        ),
        PerguntaQuiz(
            "Qual ação do mouse é usada para selecionar itens?",
            listOf( "1-Clique direito", "2-Clique duplo", "3-Arrastar","4-Clique esquerdo"),
            3,
            "O clique esquerdo é usado para selecionar itens, enquanto o clique direito abre menus de opções."
        ),
        PerguntaQuiz(
            "Qual tecla usamos para confirmar ações?",
            listOf("1-Enter", "2-Espaço", "3-Backspace", "4-Shift"),
            0,
            "A tecla Enter é usada para confirmar ações, enquanto Espaço dá espaços e Backspace apaga."
        ),
        PerguntaQuiz(
            "Como são comparadas as pastas no computador?",
            listOf("1-Gavetas", "2-Pratos", "3-Livros", "4-Portas"),
            0,
            "Pastas são como gavetas digitais onde guardamos documentos organizados por categoria."
        ),
        PerguntaQuiz(
            "O que é o navegador na analogia da internet?",
            listOf( "1-O GPS", "2-O carro","3-O mapa", "4-A estrada"),
            1,
            "O navegador é como um carro que nos leva para diferentes lugares na internet."
        ),
        PerguntaQuiz(
            "No email, o que representa o símbolo @?",
            listOf( "1-O assunto", "2-O anexo", "3-A assinatura","4-O endereço"),
            3,
            "O símbolo @ (arroba) é usado para separar o nome do usuário do domínio no endereço de email."
        ),
        PerguntaQuiz(
            "Qual programa usamos para criar documentos de texto?",
            listOf( "1-Excel", "2-PowerPoint","3-Word", "4-Paint"),
            2,
            "O Microsoft Word é o programa mais usado para criar e editar documentos de texto."
        ),
        PerguntaQuiz(
            "O que a impressora faz?",
            listOf("1-Envia para o papel", "2-Envia texto para tela", "3-Cópia de segurança", "4-Grava"),
            0,
            "A impressora transforma documentos digitais em cópias físicas no papel."
        ),
        PerguntaQuiz(
            "O que é uma senha forte?",
            listOf("1-Mistura letras, números e símbolos", "2-Só letras", "3-Data de nascimento", "4-Nome simples"),
            0,
            "Uma senha forte combina letras maiúsculas, minúsculas, números e símbolos para maior segurança."
        )
    )

    private val quizDesktopEspanol = listOf(
        PerguntaQuiz(
            "¿Qué parte de la computadora es considerada el 'cerebro'?",
            listOf( "1-Monitor", "2-Teclado", "3-Mouse","4-CPU"),
            3,
            "La CPU (Unidad Central de Procesamiento) es el cerebro de la computadora, responsable de procesar toda la información y cálculos."
        ),
        PerguntaQuiz(
            "¿Qué representa el Menú Inicio en la analogía de la sala?",
            listOf("1-La puerta de entrada", "2-La ventana", "3-El piso", "4-El techo"),
            0,
            "El Menú Inicio es como la puerta de entrada que da acceso a todos los programas y funciones de la computadora."
        ),
        PerguntaQuiz(
            "¿Qué acción del mouse se usa para seleccionar elementos?",
            listOf("1-Clic izquierdo", "2-Clic derecho", "3-Doble clic", "4-Arrastrar"),
            0,
            "El clic izquierdo se usa para seleccionar elementos, mientras el clic derecho abre menús de opciones."
        ),
        PerguntaQuiz(
            "¿Qué tecla usamos para confirmar acciones?",
            listOf( "1-Espacio","2-Enter", "3-Backspace", "4-Shift"),
            1,
            "La tecla Enter se usa para confirmar acciones, mientras Espacio da espacios y Backspace borra."
        ),
        PerguntaQuiz(
            "¿Cómo se comparan las carpetas en la computadora?",
            listOf( "1-Platos", "2-Libros","3-Cajones", "4-Puertas"),
            2,
            "Las carpetas son como cajones digitales donde guardamos documentos organizados por categoría."
        ),
        PerguntaQuiz(
            "¿Qué es el navegador en la analogía de internet?",
            listOf( "1-El GPS", "2-El mapa","3-El auto", "4-La carretera"),
            2,
            "El navegador es como un auto que nos lleva a diferentes lugares en internet."
        ),
        PerguntaQuiz(
            "En el email, ¿qué representa el símbolo @?",
            listOf( "1-El asunto", "2-El archivo adjunto", "3-La firma","4-La dirección"),
            3,
            "El símbolo @ (arroba) se usa para separar el nombre del usuario del dominio en la dirección de email."
        ),
        PerguntaQuiz(
            "¿Qué programa usamos para crear documentos de texto?",
            listOf( "1-Excel", "2-PowerPoint", "3-Paint","4-Word"),
            3,
            "Microsoft Word es el programa más usado para crear y editar documentos de texto."
        ),
        PerguntaQuiz(
            "¿Qué hace la impresora?",
            listOf( "1-Envía texto a la pantalla","2-Envía al papel", "3-Copia de seguridad", "4-Graba"),
            1,
            "La impresora transforma documentos digitales en copias físicas en el papel."
        ),
        PerguntaQuiz(
            "¿Qué es una contraseña fuerte?",
            listOf("1-Mezcla letras, números y símbolos", "2-Solo letras", "3-Fecha de nacimiento", "4-Nombre simple"),
            0,
            "Una contraseña fuerte combina letras mayúsculas, minúsculas, números y símbolos para mayor seguridad."

        ),
    )
        // QUIZ DO MÓDULO INTERNET - PORTUGUÊS
        private val quizInternetPortugues = listOf(
            PerguntaQuiz(
                "O que é um navegador de internet?",
                listOf(

                    "1-Um tipo de computador mais rápido",
                    "2-Programa para acessar sites e páginas web",
                    "3-Um aplicativo de mensagens",
                    "4-Uma rede social"
                ),
                1,
                "O navegador é o programa que usamos para acessar e visualizar sites na internet, como Chrome, Firefox ou Edge."
            ),
    PerguntaQuiz(
    "O que significa 'https://' no início de um site?",
    listOf(

    "1-Que o site é totalmente gratuito",
    "2-Que o site carrega mais rápido",
        "3-Que a conexão com o site é segura e criptografada",
    "4-Que o site tem vídeos"
    ),
    2,
    "'https://' indica que a conexão com o site é criptografada e segura para enviar dados sensíveis."
    ),
    PerguntaQuiz(
    "Como pesquisar algo no Google?",
    listOf(

    "1-Ligar para o número do Google",
    "2-Enviar um email para o Google",
    "3-Abrir o Facebook e pesquisar lá",
        "4-Digitar palavras-chave na barra de pesquisa e pressionar Enter"
    ),
    3,
    "No Google, digite palavras relacionadas ao que busca na barra de pesquisa e pressione Enter para ver os resultados."
    ),
    PerguntaQuiz(
    "O que é Wi-Fi?",
    listOf(
    "1-Tecnologia de conexão com internet sem usar cabos",
    "2-Um tipo de site de compras",
    "3-Um navegador de internet diferente",
    "4-Um aplicativo de redes sociais"
    ),
    0,
    "Wi-Fi é uma tecnologia que permite conexão com a internet sem usar cabos, através de ondas de rádio."
    ),
    PerguntaQuiz(
    "Qual destes é um sinal de site seguro?",
    listOf(
    "1-Cadeado verde na barra de endereços do navegador",
    "2-Muitos anúncios coloridos na página",
    "3-Pop-ups constantes pedindo clique",
    "4-Site sem nome nem informações"
    ),
    0,
    "O cadeado verde na barra de endereços indica que o site usa conexão segura (HTTPS) e é confiável."
    )
    )

    // QUIZ DO MÓDULO INTERNET - ESPANHOL
    private val quizInternetEspanol = listOf(
        PerguntaQuiz(
            "¿Qué es un navegador de internet?",
            listOf(
                "1-Programa para acceder a sitios y páginas web",
                "2-Un tipo de computadora más rápida",
                "3-Una aplicación de mensajes",
                "4-Una red social"
            ),
            0,
            "El navegador es el programa que usamos para acceder y ver sitios web en internet, como Chrome, Firefox o Edge."
        ),
        PerguntaQuiz(
            "¿Qué significa 'https://' al inicio de un sitio web?",
            listOf(

                "1-Que el sitio es totalmente gratuito",
                "2-Que la conexión con el sitio es segura y cifrada",
                "3-Que el sitio carga más rápido",
                "4-Que el sitio tiene videos"
            ),
            1,
            "'https://' indica que la conexión con el sitio web es cifrada y segura para enviar datos sensibles."
        ),
        PerguntaQuiz(
            "¿Cómo buscar algo en Google?",
            listOf(

                "1-Llamar al número de Google",
                "2-Enviar un correo a Google",
                "3-Abrir Facebook y buscar allí",
                "4-Escribir palabras clave en la barra de búsqueda y presionar Enter"
            ),
            3,
            "En Google, escribe palabras relacionadas con lo que buscas en la barra de búsqueda y presiona Enter para ver los resultados."
        ),
        PerguntaQuiz(
            "¿Qué es Wi-Fi?",
            listOf(

                "1-Un tipo de sitio de compras",
                "2-Un navegador de internet diferente",
                "3-Una aplicación de redes sociales",
                "4-Tecnología de conexión a internet sin usar cables"
            ),
            3,
            "Wi-Fi es una tecnología que permite conexión a internet sin usar cables, a través de ondas de radio."
        ),
        PerguntaQuiz(
            "¿Cuál de estos es una señal de sitio web seguro?",
            listOf(

                "1-Muchos anuncios coloridos en la página",
                "2-Candado verde en la barra de direcciones del navegador",
                "3-Pop-ups constantes pidiendo clics",
                "4-Sitio sin nombre ni información"
            ),
            1,
            "El candado verde en la barra de direcciones indica que el sitio web usa conexión segura (HTTPS) y es confiable."
        )
    )

    // QUIZ DO MÓDULO EMAIL - COMPLETO
    private val quizEmailPortugues = listOf(
        PerguntaQuiz(
            "O que é um endereço de email?",
            listOf( "1-Um tipo de rede social", "2-Um aplicativo de mensagens", "3-Um navegador de internet","4-Sua identidade digital para receber mensagens"),
            3,
            "O endereço de email é único para cada pessoa e usado para enviar e receber mensagens eletrônicas."
        ),
        PerguntaQuiz(
            "Qual destes é um provedor de email gratuito?",
            listOf("1-Gmail", "2-Windows", "3-Photoshop", "4-Excel"),
            0,
            "Gmail é o serviço de email gratuito do Google, muito popular e fácil de usar."
        ),
        PerguntaQuiz(
            "O que devemos fazer com emails de remetentes desconhecidos?",
            listOf( "1-Responder imediatamente","2-Ter cuidado e não abrir anexos", "3-Encaminhar para amigos", "4-Abrir todos os anexos"),
            1,
            "Emails de desconhecidos podem conter vírus ou tentativas de golpe - é melhor ter cautela."
        ),
        PerguntaQuiz(
            "Para que serve o campo 'Assunto' em um email?",
            listOf( "1-Decorar o email", "2-Esconder o conteúdo","3-Resumir o conteúdo do email", "4-Aumentar o tamanho do email"),
            2,
            "O assunto ajuda o destinatário a entender rapidamente sobre o que se trata o email."
        ),
        PerguntaQuiz(
            "O que é phishing?",
            listOf("1-Tentativa de golpe por email", "2-Um tipo de anexo", "3-Mensagem automática", "4-Sistema de organização"),
            0,
            "Phishing são emails falsos que tentam enganar você para roubar senhas e dados pessoais."
        ),
        // NOVAS PERGUNTAS DO QUIZ
        PerguntaQuiz(
            "Como anexar um arquivo em um email?",
            listOf( "1-Digitar o nome do arquivo","2-Clicar no ícone de clipe (📎)", "3-Copiar e colar o texto", "4-Tirar uma foto da tela"),
            1,
            "O botão de clipe (📎) é usado para anexar arquivos do computador ou celular ao email."
        ),
        PerguntaQuiz(
            "Para que servem as pastas no email?",
            listOf( "1-Aumentar o espaço de armazenamento", "2-Tornar o email mais bonito", "3-Organizar emails por assunto","4-Acelerar o envio de mensagens"),
            2,
            "Pastas ajudam a classificar e organizar emails para encontrá-los mais facilmente depois."
        ),
        PerguntaQuiz(
            "Qual é a estrutura ideal de um email profissional?",
            listOf( "1-Apenas a mensagem sem saudação", "2-Muitos emojis e cores","3-Saudação, assunto claro, mensagem objetiva, despedida", "4-Texto longo sem parágrafos"),
            2,
            "Um email profissional deve ter saudação, assunto claro, mensagem objetiva e despedida educada."
        ),
        PerguntaQuiz(
            "Quando usar 'Responder a todos'?",
            listOf("1-Nunca, é perigoso","2-Quando a resposta interessa a todos da conversa", "3-Sempre, para todos os emails","4-Apenas para emails pessoais"),
            1,
            "'Responder a todos' envia sua resposta para todas as pessoas que receberam o email original."
        ),
        PerguntaQuiz(
            "O que fazer se esquecer a senha do email?",
            listOf("1-Usar a opção 'Recuperar senha'", "2-Criar uma nova conta", "3-Ligar para assistência técnica", "4-Parar de usar email"),
            0,
            "A opção 'Recuperar senha' ou 'Esqueci minha senha' ajuda a redefinir sua senha com segurança."
        )
    )

    private val quizEmailEspanol= listOf(
        PerguntaQuiz(
            "¿Qué es una dirección de email?",
            listOf( "1-Un tipo de red social", "2-Una aplicación de mensajes", "3-Un navegador de internet","4-Tu identidad digital para recibir mensajes"),
            3,
            "La dirección de email es única para cada persona y se usa para enviar y recibir mensajes electrónicos."
        ),
        PerguntaQuiz(
            "¿Cuál de estos es un proveedor de email gratuito?",
            listOf( "1-Windows", "2-Photoshop","3-Gmail", "4-Excel"),
            2,
            "Gmail es el servicio de email gratuito de Google, muy popular y fácil de usar."
        ),
        PerguntaQuiz(
            "¿Qué debemos hacer con emails de remitentes desconocidos?",
            listOf( "1-Responder inmediatamente","2-Tener cuidado y no abrir archivos adjuntos","3-Reenviar a amigos", "4-Abrir todos los archivos adjuntos"),
            1,
            "Los emails de desconocidos pueden contener virus o intentos de estafa - es mejor tener precaución."
        ),
        PerguntaQuiz(
            "¿Para qué sirve el campo 'Asunto' en un email?",
            listOf( "1-Ocultar el contenido", "2-Aumentar el tamaño del email","3-Resumir el contenido del email", "4-Decorar el email"),
            2,
            "El asunto ayuda al destinatario a entender rápidamente de qué trata el email."
        ),
        PerguntaQuiz(
            "¿Qué es el phishing?",
            listOf("1-Intento de estafa por email", "2-Un tipo de archivo adjunto", "3-Mensaje automático", "4-Sistema de organización"),
            0,
            "Phishing son emails falsos que intentan engañarte para robar contraseñas y datos personales."
        ),
        // NUEVAS PREGUNTAS DEL QUIZ
        PerguntaQuiz(
            "¿Cómo adjuntar un archivo en un email?",
            listOf( "1-Escribir el nombre del archivo", "2-Copiar y pegar el texto","3-Hacer clic en el ícono de clip (📎)", "4-Tomar una foto de la pantalla"),
            2,
            "El botón de clip (📎) se usa para adjuntar archivos de la computadora o celular al email."
        ),
        PerguntaQuiz(
            "¿Para qué sirven las carpetas en el email?",
            listOf( "1-Aumentar el espacio de almacenamiento", "2-Hacer el email más bonito","3-Organizar emails por asunto", "4-Acelerar el envío de mensajes"),
            2,
            "Las carpetas ayudan a clasificar y organizar emails para encontrarlos más fácilmente después."
        ),
        PerguntaQuiz(
            "¿Cuál es la estructura ideal de un email profesional?",
            listOf("1-Saludo, asunto claro, mensaje objetiva, despedida", "2-Solo el mensaje sin saludo", "3-Muchos emojis y colores", "4-Texto largo sin párrafos"),
            0,
            "Un email profesional debe tener saludo, asunto claro, mensaje objetiva y despedida educada."
        ),
        PerguntaQuiz(
            "¿Cuándo usar 'Responder a todos'?",
            listOf( "1-Siempre, para todos los emails","2-Cuando la respuesta interesa a todos en la conversación", "3-Nunca, es peligroso", "4-Solo para emails personales"),
            1,
            "'Responder a todos' envía tu respuesta a todas las personas que recibieron el email original."
        ),
        PerguntaQuiz(
            "¿Qué hacer si olvidas la contraseña del email?",
            listOf("1-Usar la opción 'Recuperar contraseña'", "2-Crear una nueva cuenta", "3-Convocatoria de asistencia técnica", "4-Dejar de usar email"),
            0,
            "La opción 'Recuperar contraseña' o 'Olvidé mi contraseña' ayuda a redefinir tu contraseña con seguridad."
        )
    )
    // QUIZ DO MÓDULO TRABALHO - PORTUGUÊS
    private val quizTrabalhoPortugues: List<PerguntaQuiz> = listOf(
        PerguntaQuiz(
            "Qual é o principal objetivo de um currículo?",
            listOf(

                "1-Contar toda sua história de vida pessoal",
                "2-Listar apenas seus cursos acadêmicos",
                "3-Apresentar suas qualificações de forma clara e profissional",
                "4-Mostrar apenas suas experiências internacionais"
            ),
            2,
            "O currículo deve destacar suas habilidades, experiências e formações de maneira organizada para impressionar recrutadores."
        ),
        PerguntaQuiz(
            "Qual destas é uma dica importante para uma entrevista de emprego?",
            listOf(

                "1-Chegar atrasado para não parecer ansioso",
                "2-Falar apenas sobre salário e benefícios",
                "3-Vestir roupas casuais como shorts e chinelos",
                "4-Pesquisar sobre a empresa antes da entrevista"
            ),
            3,
            "Conhecer a empresa demonstra interesse genuíno e preparação para a vaga."
        ),
        PerguntaQuiz(
            "O que é o LinkedIn?",
            listOf(
                "1-Uma rede social profissional para conexões de trabalho",
                "2-Um aplicativo de mensagens pessoais",
                "3-Um site de compras online",
                "4-Um jogo de estratégia empresarial"
            ),
            0,
            "LinkedIn é a maior rede profissional do mundo, usada para networking, busca de empregos e desenvolvimento de carreira."
        ),
        PerguntaQuiz(
            "Qual destas habilidades é mais importante para o trabalho em equipe?",
            listOf(
                "1-Comunicação clara e respeito às opiniões diferentes",
                "2-Trabalhar sempre sozinho para ser mais eficiente",
                "3-Criticar constantemente os colegas",
                "4-Esconder informações da equipe"
            ),
            0,
            "A comunicação eficaz e o respeito são fundamentais para um bom ambiente de trabalho em equipe."
        ),
        PerguntaQuiz(
            "O que significa gestão do tempo no ambiente profissional?",
            listOf(

                "1-Trabalhar até tarde todos os dias",
                "2-Organizar tarefas por prioridade e cumprir prazos",
                "3-Fazer todas as tarefas ao mesmo tempo",
                "4-Deixar tudo para a última hora"
            ),
            1,
            "Uma boa gestão do tempo aumenta a produtividade e reduz o estresse no trabalho."
        ),
        PerguntaQuiz(
            "Qual destes é um direito trabalhista básico?",
            listOf(

                "1-Trabalhar sem intervalo para descanso",
                "2-Receber salário apenas a cada dois meses",
                "3-Férias remuneradas após um ano de trabalho",
                "4-Trabalhar 60 horas por semana sem horas extras"
            ),
            2,
            "As férias remuneradas são um direito garantido por lei após 12 meses de trabalho."
        ),
        PerguntaQuiz(
            "Por que é importante o desenvolvimento profissional contínuo?",
            listOf(

                "1-Para impressionar os colegas de trabalho",
                "2-Para poder trabalhar menos horas",
                "3-Para se manter atualizado e crescer na carreira",
                "4-Para evitar promoções no trabalho"
            ),
            2,
            "O aprendizado constante permite acompanhar as mudanças do mercado e abrir novas oportunidades."
        ),
        PerguntaQuiz(
            "O que é equilíbrio entre vida pessoal e profissional?",
            listOf(
                "1-Distribuir o tempo adequadamente entre trabalho e vida pessoal",
                "2-Trabalhar 16 horas por dia todos os dias",
                "3-Nunca separar trabalho da vida familiar",
                "4-Deixar o trabalho para cuidar apenas da vida pessoal"
            ),
            0,
            "O equilíbrio é essencial para evitar burnout e manter a saúde mental e física."
        ),
        PerguntaQuiz(
            "Qual destes comportamentos ajuda na comunicação profissional?",
            listOf(

                "1-Interromper os outros enquanto falam",
                "2-Falar apenas sobre assuntos pessoais",
                "3-Ignorar emails e mensagens importantes",
                "4-Ouvir atentamente antes de responder"
            ),
            3,
            "A escuta ativa demonstra respeito e ajuda a entender melhor as necessidades e pontos de vista dos outros."
        ),
        PerguntaQuiz(
            "O que deve conter uma carta de apresentação?",
            listOf(

                "1-Lista de todas suas contas em redes sociais",
                "2-História completa da sua infância e família",
                "3-Críticas aos seus empregadores anteriores",
                "4-Explicação de como suas habilidades se alinham com a vaga",
            ),
            3,
            "A carta de apresentação complementa o currículo mostrando seu interesse específico pela vaga e empresa."
        )
    )

    // QUIZ DO MÓDULO TRABALHO - ESPANHOL
    private val quizTrabalhoEspanol = listOf(
        PerguntaQuiz(
            "¿Cuál es el principal objetivo de un currículum?",
            listOf(

                "1-Contar toda tu historia de vida personal",
                "2-Listar solo tus cursos académicos",
                "3-Mostrar solo tus experiencias internacionales",
                "4-Presentar tus calificaciones de forma clara y profesional",
            ),
            3,
            "El currículum debe destacar tus habilidades, experiencias y formaciones de manera organizada para impresionar a los reclutadores."
        ),
        PerguntaQuiz(
            "¿Cuál de estos es un consejo importante para una entrevista de trabajo?",
            listOf(
                "1-Investigar sobre la empresa antes de la entrevista",
                "2-Llegar tarde para no parecer ansioso",
                "3-Hablar solo sobre salario y beneficios",
                "4-Vestir ropa casual como shorts y chanclas"
            ),
            0,
            "Conocer la empresa demuestra interés genuino y preparación para el puesto."
        ),
        PerguntaQuiz(
            "¿Qué es LinkedIn?",
            listOf(

                "1-Una aplicación de mensajes personales",
                "2-Un sitio de compras online",
                "3-Una red social profesional para conexiones laborales",
                "4-Un juego de estrategia empresarial"
            ),
            2,
            "LinkedIn es la mayor red profesional del mundo, utilizada para networking, búsqueda de empleo y desarrollo profesional."
        ),
        PerguntaQuiz(
            "¿Cuál de estas habilidades es más importante para el trabajo en equipo?",
            listOf(

                "1-Trabajar siempre solo para ser más eficiente",
                "2-Criticar constantemente a los compañeros",
                "3-Comunicación clara y respeto a las opiniones diferentes",
                "4-Ocultar información del equipo"
            ),
            2,
            "La comunicación efectiva y el respeto son fundamentales para un buen ambiente de trabajo en equipo."
        ),
        PerguntaQuiz(
            "¿Qué significa gestión del tiempo en el ambiente profesional?",
            listOf(

                "1-Trabajar hasta tarde todos los días",
                "2-Hacer todas las tareas al mismo tiempo",
                "3-Dejar todo para el último momento",
                "4-Organizar tareas por prioridad y cumplir plazos"
            ),
            3,
            "Una buena gestión del tiempo aumenta la productividad y reduce el estrés en el trabajo."
        ),
        PerguntaQuiz(
            "¿Cuál de estos es un derecho laboral básico?",
            listOf(
                "1-Vacaciones remuneradas después de un año de trabajo",
                "2-Trabajar sin intervalo para descanso",
                "3-Recibir salario solo cada dos meses",
                "4-Trabajar 60 horas por semana sin horas extras"
            ),
            0,
            "Las vacaciones remuneradas son un derecho garantizado por ley después de 12 meses de trabajo."
        ),
        PerguntaQuiz(
            "¿Por qué es importante el desarrollo profesional continuo?",
            listOf(
                "1-Para mantenerse actualizado y crecer en la carrera",
                "2-Para impresionar a los compañeros de trabajo",
                "3-Para poder trabajar menos horas",
                "4-Para evitar promociones en el trabajo"
            ),
            0,
            "El aprendizaje constante permite acompanar los cambios del mercado y abrir nuevas oportunidades."
        ),
        PerguntaQuiz(
            "¿Qué es el equilibrio entre vida personal y profesional?",
            listOf(

                "1-Trabajar 16 horas al día todos los días",
                "2-Distribuir el tiempo adecuadamente entre trabajo y vida personal",
                "3-Nunca separar trabajo de la vida familiar",
                "4-Dejar el trabajo para cuidar solo de la vida personal"
            ),
            1,
            "El equilibrio es esencial para evitar el burnout y mantener la salud mental y física."
        ),
        PerguntaQuiz(
            "¿Cuál de estos comportamientos ayuda en la comunicación profesional?",
            listOf(

                "1-Interrumpir a los demás mientras hablan",
                "2-Escuchar atentamente antes de responder",
                "3-Hablar solo sobre temas personales",
                "4-Ignorar correos y mensajes importantes"
            ),
            1,
            "La escucha activa demuestra respeto y ayuda a comprender mejor las necesidades y puntos de vista de los demás."
        ),
        PerguntaQuiz(
            "¿Qué debe contener una carta de presentación?",
            listOf(

                "1-Lista de todas tus cuentas en redes sociales",
                "2-Explicación de cómo tus habilidades se alinean con el puesto",
                "3-Historia completa de tu infancia y familia",
                "4-Críticas a tus empleadores anteriores"
            ),
            1,
            "La carta de presentación complementa el currículum mostrando tu interés específico por el puesto y la empresa."
        )
    )
    // QUIZ DO MÓDULO COMPRAS - PORTUGUÊS
    private val quizComprasPortugues = listOf(
        PerguntaQuiz(
            "Qual é uma das principais vantagens das compras online?",
            listOf(

                "1-Precisar ir pessoalmente à loja",
                "2-Ter menos opções de produtos",
                "3-Pagar sempre mais caro pelos produtos",
                "4-Comodidade de comprar sem sair de casa"
            ),
            3,
            "A comodidade é uma das maiores vantagens, permitindo comprar a qualquer hora e de qualquer lugar."
        ),
        PerguntaQuiz(
            "Qual destes é um sinal de loja online confiável?",
            listOf(

                "1-Site sem informações de contato",
                "2-Preços absurdamente baixos",
                "3-Site com https:// e cadeado na barra de endereço",
                "4-URL suspeita e complicada"
            ),
            2,
            "O https:// e o cadeado indicam que o site é seguro e criptografado para proteger seus dados."
        ),
        PerguntaQuiz(
            "Quais informações são normalmente necessárias para cadastro em lojas online?",
            listOf(
                "1-Nome completo, e-mail, CPF e endereço",
                "2-Apenas nome e e-mail",
                "3-Número de cartão de crédito e senha",
                "4-Renda mensal e estado civil"
            ),
            0,
            "Dados básicos como nome, e-mail, CPF e endereço são necessários para emissão de nota fiscal e entrega."
        ),
        PerguntaQuiz(
            "Como buscar produtos de forma eficiente em lojas online?",
            listOf(

                "1-Navegar sem usar a barra de pesquisa",
                "2-Usar palavras-chave específicas e filtros",
                "3-Comprar o primeiro produto que aparecer",
                "4-Não ler as descrições dos produtos"
            ),
            1,
            "Palavras-chave específicas e filtros ajudam a encontrar exatamente o que você precisa mais rapidamente."
        ),
        PerguntaQuiz(
            "Por que é importante ler avaliações de produtos?",
            listOf(

                "1-Porque todas as avaliações são falsas",
                "2-Para saber a experiência real de outros compradores",
                "3-Para descobrir o preço mais baixo",
                "4-Porque a loja paga para avaliar"
            ),
            1,
            "Avaliações de clientes reais mostram a qualidade real do produto e possíveis problemas."
        ),
        PerguntaQuiz(
            "O que deve ser verificado no carrinho antes de finalizar a compra?",
            listOf(
                "1-Preço total, quantidades e opção de entrega",
                "2-Apenas a cor do produto",
                "3-Quantos likes o produto tem",
                "4-A data de fabricação do produto"
            ),
            0,
            "É essencial verificar preço final, quantidades e opções de entrega para evitar surpresas."
        ),
        PerguntaQuiz(
            "Qual forma de pagamento oferece a opção de parcelamento?",
            listOf(
                "1-Cartão de crédito",
                "2-PIX",
                "3-Boleto bancário",
                "4-Cartão de débito"
            ),
            0,
            "O cartão de crédito permite parcelar compras em várias vezes, facilitando o orçamento."
        ),
        PerguntaQuiz(
            "Como acompanhar um pedido após a compra?",
            listOf(

                "1-Ligar para o entregador pessoalmente",
                "2-Esperar em casa sem informações",
                "3-Pedir para um vizinho ficar observando",
                "4-Usar o código de rastreamento fornecido"
            ),
            3,
            "O código de rastreamento permite acompanhar cada etapa da entrega pelo site dos correios."
        ),
        PerguntaQuiz(
            "Qual é o prazo para arrependimento de compra online?",
            listOf(

                "1-30 dias úteis",
                "2-7 dias corridos após recebimento do produto",
                "3-Apenas 24 horas",
                "4-Não existe prazo para arrependimento"
            ),
            1,
            "Por lei, o consumidor tem 7 dias para se arrepender de compras feitas pela internet."
        ),
        PerguntaQuiz(
            "O que fazer ao suspeitar de um golpe online?",
            listOf(

                "1-Comprar mesmo assim para testar",
                "2-Fornecer todos os dados pedidos",
                "3-Compartilhar a oferta com amigos",
                "4-Não fornecer dados e denunciar ao site"
            ),
            3,
            "Ao suspeitar de golpe, não forneça dados e denuncie para proteger outras pessoas."
        )
    )

    // QUIZ DO MÓDULO COMPRAS - ESPANHOL
    private val quizComprasEspanol = listOf(
        PerguntaQuiz(
            "¿Cuál es una de las principales ventajas de las compras online?",
            listOf(
                "1-Comodidad de comprar sin salir de casa",
                "2-Necesitar ir personalmente a la tienda",
                "3-Tener menos opciones de productos",
                "4-Pagar siempre más caro por los productos"
            ),
            0,
            "La comodidad es una de las mayores ventajas, permitiendo comprar a cualquier hora y desde cualquier lugar."
        ),
        PerguntaQuiz(
            "¿Cuál de estos es una señal de tienda online confiable?",
            listOf(
                "1-Sitio con https:// y candado en la barra de dirección",
                "2-Sitio sin informaciones de contacto",
                "3-Precios absurdamente bajos",
                "4-URL sospechosa y complicada"
            ),
            0,
            "El https:// y el candado indican que el sitio es seguro y cifrado para proteger tus datos."
        ),
        PerguntaQuiz(
            "¿Qué informaciones son normalmente necesarias para registro en tiendas online?",
            listOf(
                "1-Nombre completo, e-mail, CPF y dirección",
                "2-Solo nombre y e-mail",
                "3-Número de tarjeta de crédito y contraseña",
                "4-Ingresos mensuales y estado civil"
            ),
            0,
            "Datos básicos como nombre, e-mail, CPF y dirección son necesarios para emisión de nota fiscal y entrega."
        ),
        PerguntaQuiz(
            "¿Cómo buscar productos de forma eficiente en tiendas online?",
            listOf(

                "1-Navegar sin usar la barra de búsqueda",
                "2-Usar palabras-clave específicas y filtros",
                "3-Comprar el primer producto que aparezca",
                "4-No leer las descripciones de los productos"
            ),
            1,
            "Palabras-clave específicas y filtros ayudan a encontrar exactamente lo que necesitas más rápidamente."
        ),
        PerguntaQuiz(
            "¿Por qué es importante leer evaluaciones de productos?",
            listOf(

                "1-Porque todas las evaluaciones son falsas",
                "2-Para descubrir el precio más bajo",
                "3-Porque la tienda paga para evaluar",
                "4-Para saber la experiencia real de otros compradores"
            ),
            3,
            "Evaluaciones de clientes reales muestran la calidad real del producto y posibles problemas."
        ),
        PerguntaQuiz(
            "¿Qué debe verificarse en el carrito antes de finalizar la compra?",
            listOf(

                "1-Solo el color del producto",
                "2-Cuántos likes tiene el producto",
                "3-La fecha de fabricación del producto",
                "4-Precio total, cantidades y opción de entrega"
            ),
            3,
            "Es esencial verificar precio final, cantidades y opciones de entrega para evitar sorpresas."
        ),
        PerguntaQuiz(
            "¿Qué forma de pago ofrece la opción de pago en cuotas?",
            listOf(

                "1-PIX",
                "2-Tarjeta de crédito",
                "3-Boleto bancario",
                "4-Tarjeta de débito"
            ),
            1,
            "La tarjeta de crédito permite pagar compras en varias cuotas, facilitando el presupuesto."
        ),
        PerguntaQuiz(
            "¿Cómo hacer seguimiento de un pedido después de la compra?",
            listOf(
                "1-Usar el código de rastreo proporcionado",
                "2-Llamar al entregador personalmente",
                "3-Esperar en casa sin informaciones",
                "4-Pedir a un vecino que observe"
            ),
            0,
            "El código de rastreo permite acompanar cada etapa de la entrega por el sitio de correos."
        ),
        PerguntaQuiz(
            "¿Cuál es el plazo para arrepentimiento de compra online?",
            listOf(

                "1-30 días hábiles",
                "2-Solo 24 horas",
                "3-7 días corridos después de recibir el producto",
                "4-No existe plazo para arrepentimiento"
            ),
            2,
            "Por ley, el consumidor tiene 7 días para arrepentirse de compras hechas por internet."
        ),
        PerguntaQuiz(
            "¿Qué hacer al sospechar de una estafa online?",
            listOf(

                "1-Comprar igual para probar",
                "2-Proporcionar todos los datos solicitados",
                "3-No proporcionar datos y denunciar al sitio",
                "4-Compartir la oferta con amigos"
            ),
            2,
            "Al sospechar de estafa, no proporciones datos y denuncia para proteger otras personas."
        )
    )
    // QUIZ DO MÓDULO BANCO - PORTUGUÊS
    private val quizBancoPortugues = listOf(
        PerguntaQuiz(
            "Qual é uma das principais vantagens do banco digital?",
            listOf(
                "1-Acesso 24 horas por dia sem precisar ir à agência",
                "2-Precisa ir pessoalmente para resolver tudo",
                "3-Taxas mais altas que bancos tradicionais",
                "4-Funciona apenas em horário comercial"
            ),
            0,
            "Os bancos digitais permitem acesso a qualquer hora pelo celular, sem filas e geralmente com menos taxas."
        ),
        PerguntaQuiz(
            "Quais documentos são normalmente necessários para abrir uma conta digital?",
            listOf(
                "1-CPF, RG, comprovante de residência e selfie",
                "2-Apenas CPF e e-mail",
                "3-Certidão de nascimento e título de eleitor",
                "4-Carteira de trabalho e comprovante de renda"
            ),
            0,
            "CPF, RG (ou CNH), comprovante de residência e selfie para confirmação de identidade são os documentos básicos."
        ),
        PerguntaQuiz(
            "Qual é a forma mais segura de acessar o app do banco?",
            listOf(
                "1-Anotar a senha em um papel colado no celular",
                "2-Usar biometria (digital) ou reconhecimento facial",
                "3-Usar a mesma senha de outros aplicativos",
                "4-Compartilhar a senha com familiares"
            ),
            1,
            "A biometria e reconhecimento facial são mais seguros porque usam características únicas do seu corpo."
        ),
        PerguntaQuiz(
            "O que significa quando uma transação aparece como 'crédito' no extrato?",
            listOf(

                "1-Dinheiro que saiu da sua conta",
                "2-Uma compra feita com cartão",
                "3-Dinheiro que entrou na sua conta",
                "4-Uma taxa cobrada pelo banco"
            ),
            2,
            "Crédito significa entrada de dinheiro, como salário, transferências recebidas ou depósitos."
        ),
        PerguntaQuiz(
            "Qual é a principal diferença entre PIX e TED?",
            listOf(
                "1-PIX é instantâneo e gratuito, TED pode demorar e ter taxa",
                "2-PIX só funciona entre mesmo banco, TED entre bancos diferentes",
                "3-PIX precisa de agendamento, TED é imediato",
                "4-PIX tem limite maior que TED"
            ),
            0,
            "PIX é transferência instantânea e geralmente gratuita, enquanto TED pode levar horas e ter custo."
        ),
        PerguntaQuiz(
            "Como pagar um boleto pelo app do banco?",
            listOf(
                "1-Digitalizar o código de barras ou digitar o número",
                "2-Levar o boleto até uma agência bancária",
                "3-Enviar foto do boleto por e-mail",
                "4-Ligar para o banco e dar os dados por telefone"
            ),
            0,
            "A maioria dos apps permite escanear o código de barras do boleto com a câmera do celular."
        ),
        PerguntaQuiz(
            "Qual é a vantagem do cartão virtual em relação ao físico?",
            listOf(

                "1-Pode ser usado em todas as lojas físicas",
                "2-Tem limite de crédito maior",
                "3-Não precisa de senha para usar",
                "4-Mais seguro para compras online, não pode ser perdido ou roubado"
            ),
            3,
            "O cartão virtual existe apenas no app, portanto não pode ser extraviado ou clonado fisicamente."
        ),
        PerguntaQuiz(
            "O que é a poupança?",
            listOf(

                "1-Um tipo de empréstimo com juros altos",
                "2-Uma aplicação financeira segura e de fácil acesso",
                "3-Um cartão de crédito especial",
                "4-Um seguro para o celular"
            ),
            1,
            "A poupança é um investimento seguro onde seu dinheiro rende juros e você pode resgatar a qualquer momento."
        ),
        PerguntaQuiz(
            "O que fazer se receber um SMS suspeito pedindo dados do banco?",
            listOf(
                "1-Ignorar e nunca fornecer informações",
                "2-Responder com seus dados para verificar",
                "3-Encaminhar para amigos para alertá-los",
                "4-Clicar no link para ver se é verdade"
            ),
            0,
            "Bancos nunca pedem dados sensíveis por SMS, e-mail ou telefone. Desconfie sempre!"
        ),
        PerguntaQuiz(
            "Qual é o primeiro passo ao ter problemas com o app do banco?",
            listOf(

                "1-Ir imediatamente a uma agência física",
                "2-Desinstalar e reinstalar o app",
                "3-Procurar o SAC ou ajuda dentro do próprio aplicativo",
                "4-Fechar a conta e abrir em outro banco"
            ),
            2,
            "A maioria dos problemas pode ser resolvida pelo SAC dentro do app, que é mais rápido e eficiente."
        )
    )

    // QUIZ DO MÓDULO BANCO - ESPANHOL
    private val quizBancoEspanol = listOf(
        PerguntaQuiz(
            "¿Cuál es una de las principales ventajas del banco digital?",
            listOf(

                "1-Necesita ir personalmente para resolver todo",
                "2-Tasas más altas que bancos tradicionales",
                "3-Acceso 24 horas al día sin necesidad de ir a la sucursal",
                "4-Funciona solo en horario comercial"
            ),
            2,
            "Los bancos digitales permiten acceso a cualquier hora por el celular, sin filas y generalmente con menos tasas."
        ),
        PerguntaQuiz(
            "¿Qué documentos son normalmente necesarios para abrir una cuenta digital?",
            listOf(

                "1-Solo CPF y e-mail",
                "2-Certificado de nacimiento y título de elector",
                "3-Libreta de trabajo y comprobante de ingresos",
                "4-CPF, RG, comprobante de domicilio y selfie"
            ),
            3,
            "CPF, RG (o CNH), comprobante de domicilio y selfie para confirmación de identidad son los documentos básicos."
        ),
        PerguntaQuiz(
            "¿Cuál es la forma más segura de acceder a la app del banco?",
            listOf(

                "1-Anotar la contraseña en un papel pegado al celular",
                "2-Usar biometría (huella) o reconocimiento facial",
                "3-Usar la misma contraseña de otras aplicaciones",
                "4-Compartir la contraseña con familiares"
            ),
            1,
            "La biometría y reconocimiento facial son más seguros porque usan características únicas de tu cuerpo."
        ),
        PerguntaQuiz(
            "¿Qué significa cuando una transacción aparece como 'crédito' en el extracto?",
            listOf(

                "1-Dinero que salió de tu cuenta",
                "2-Dinero que entró en tu cuenta",
                "3-Una compra hecha con tarjeta",
                "4-Una tasa cobrada por el banco"
            ),
            1,
            "Crédito significa entrada de dinero, como salario, transferencias recibidas o depósitos."
        ),
        PerguntaQuiz(
            "¿Cuál es la principal diferencia entre PIX y TED?",
            listOf(
                "1-PIX es instantáneo y gratuito, TED puede demorar y tener tasa",
                "2-PIX solo funciona entre mismo banco, TED entre bancos diferentes",
                "3-PIX necesita agendamiento, TED es inmediato",
                "4-PIX tiene límite mayor que TED"
            ),
            0,
            "PIX es transferencia instantánea y generalmente gratuita, mientras TED puede llevar horas y tener costo."
        ),
        PerguntaQuiz(
            "¿Cómo pagar un boleto por la app del banco?",
            listOf(
                "1-Escanear el código de barras o digitar el número",
                "2-Llevar el boleto hasta una sucursal bancaria",
                "3-Enviar foto del boleto por e-mail",
                "4-Llamar al banco y dar los datos por teléfono"
            ),
            0,
            "La mayoría de las apps permite escanear el código de barras del boleto con la cámara del celular."
        ),
        PerguntaQuiz(
            "¿Cuál es la ventaja de la tarjeta virtual en relación a la física?",
            listOf(

                "1-Puede ser usada en todas las tiendas físicas",
                "2-Tiene límite de crédito mayor",
                "3-No necesita contraseña para usar",
                "4-Más segura para compras online, no puede ser perdida o robada"
            ),
            3,
            "La tarjeta virtual existe solo en la app, por lo tanto no puede ser extraviada o clonada físicamente."
        ),
        PerguntaQuiz(
            "¿Qué es el ahorro?",
            listOf(

                "1-Un tipo de préstamo con intereses altos",
                "2-Una tarjeta de crédito especial",
                "3-Un seguro para el celular",
                "4-Una aplicación financiera segura y de fácil acceso"
            ),
            3,
            "El ahorro es una inversión segura donde tu dinero rinde intereses y puedes rescatar en cualquier momento."
        ),
        PerguntaQuiz(
            "¿Qué hacer si recibes un SMS sospechoso pidiendo datos del banco?",
            listOf(

                "1-Responder con tus datos para verificar",
                "2-Reenviar a amigos para alertarlos",
                "3-Hacer clic en el enlace para ver si es verdad",
                "4-Ignorar y nunca proporcionar informaciones"
            ),
            3,
            "Los bancos nunca piden datos sensibles por SMS, e-mail o teléfono. ¡Desconfía siempre!"
        ),
        PerguntaQuiz(
            "¿Cuál es el primer paso al tener problemas con la app del banco?",
            listOf(
                "1-Buscar el SAC o ayuda dentro de la propia aplicación",
                "2-Ir inmediatamente a una sucursal física",
                "3-Desinstalar y reinstalar la app",
                "4-Cerrar la cuenta y abrir en otro banco"
            ),
            0,
            "La mayoría de los problemas puede ser resuelta por el SAC dentro de la app, que es más rápido y eficiente."
        )
    )
    // QUIZ DO MÓDULO SEGURANÇA - PORTUGUÊS
    private val quizSegurancaPortugues = listOf(
        PerguntaQuiz(
            "Qual é a principal característica de uma senha forte?",
            listOf(

                "1-Usar apenas letras minúsculas",
                "2-Combinação de letras, números e símbolos",
                "3-Usar sequências simples como 123456",
                "4-Usar apenas o nome completo"
            ),
            1,
            "Senhas fortes misturam maiúsculas, minúsculas, números e símbolos para maior segurança."
        ),
        PerguntaQuiz(
            "O que é autenticação em duas etapas?",
            listOf(

                "1-Usar duas senhas diferentes",
                "2-Um método que exige senha mais um código temporário",
                "3-Fazer login duas vezes no mesmo dia",
                "4-Ter duas contas no mesmo serviço"
            ),
            1,
            "A autenticação em duas etapas adiciona uma camada extra de segurança exigindo algo que você sabe (senha) e algo que você tem (código no celular)."
        ),
        PerguntaQuiz(
            "Como identificar um e-mail de phishing?",
            listOf(

                "1-Vem de um amigo conhecido",
                "2-Tem o logotipo oficial da empresa",
                "3-Oferece produtos com desconto normal",
                "4-Erros de português e urgência artificial"
            ),
            3,
            "Phishing geralmente tem erros gramaticais, cria senso de urgência e pede dados pessoais."
        ),
        PerguntaQuiz(
            "Qual é a vantagem de usar um gerenciador de senhas?",
            listOf(
                "1-Cria e armazena senhas fortes automaticamente",
                "2-Compartilha senhas com amigos",
                "3-Lembra apenas uma senha para tudo",
                "4-Funciona sem internet"
            ),
            0,
            "Gerenciadores geram senhas únicas e fortes para cada site e as armazenam criptografadas."
        ),
        PerguntaQuiz(
            "Por que é perigoso usar Wi-Fi público?",
            listOf(

                "1-A internet é mais lenta",
                "2-O celular descarrega mais rápido",
                "3-Hackers podem interceptar seus dados",
                "4-Não se pode fazer chamadas"
            ),
            2,
            "Em redes Wi-Fi públicas, criminosos podem capturar senhas e informações pessoais."
        ),
        PerguntaQuiz(
            "Qual é o propósito principal de fazer backup?",
            listOf(

                "1-Liberar espaço no celular",
                "2-Proteger dados contra perda ou danos",
                "3-Compartilhar arquivos com outras pessoas",
                "4-Acelerar o dispositivo"
            ),
            1,
            "Backup garante que você não perca fotos, documentos e outros dados importantes."
        ),
        PerguntaQuiz(
            "O que NÃO se deve compartilhar nas redes sociais?",
            listOf(

                "1-Fotos de paisagens e viagens",
                "2-Receitas culinárias favoritas",
                "3-Opiniões sobre filmes e séries",
                "4-Endereço completo e localização em tempo real"
            ),
            3,
            "Informações pessoais como endereço e localização em tempo real podem ser usadas por criminosos."
        ),
        PerguntaQuiz(
            "Qual destes é um sinal de site seguro?",
            listOf(

                "1-Muitos anúncios pop-up",
                "2-HTTPS:// e cadeado na barra de endereço",
                "3-URL começando com HTTP://",
                "4-Solicitação de instalação de software"
            ),
            1,
            "HTTPS e o cadeado indicam que a conexão é criptografada e segura."
        ),
        PerguntaQuiz(
            "O que fazer ao receber um link suspeito por mensagem?",
            listOf(

                "1-Clicar para ver onde leva",
                "2-Encaminhar para amigos",
                "3-Responder pedindo mais informações",
                "4-Não clicar e verificar a fonte"
            ),
            3,
            "Links suspeitos podem levar a sites maliciosos que roubam dados ou instalam vírus."
        ),
        PerguntaQuiz(
            "Por que é importante manter o sistema operacional atualizado?",
            listOf(

                "1-Só para ter novos recursos visuais",
                "2-Corrige falhas de segurança descobertas",
                "3-Para o dispositivo ficar mais bonito",
                "4-Apenas para melhorar a velocidade"
            ),
            1,
            "Atualizações frequentemente incluem correções para vulnerabilidades de segurança."
        )
    )

    // QUIZ DO MÓDULO SEGURANÇA - ESPANHOL
    private val quizSegurancaEspanol = listOf(
        PerguntaQuiz(
            "¿Cuál es la principal característica de una contraseña fuerte?",
            listOf(

                "1-Usar solo letras minúsculas",
                "2-Usar secuencias simples como 123456",
                "3-Usar solo el nombre completo",
                "4-Combinación de letras, números y símbolos"
            ),
            3,
            "Las contraseñas fuertes mezclan mayúsculas, minúsculas, números y símbolos para mayor seguridad."
        ),
        PerguntaQuiz(
            "¿Qué es la autenticación en dos pasos?",
            listOf(
                "1-Un método que exige contraseña más un código temporal",
                "2-Usar dos contraseñas diferentes",
                "3-Hacer login dos veces en el mismo día",
                "4-Tener dos cuentas en el mismo servicio"
            ),
            0,
            "La autenticación en dos pasos añade una capa extra de seguridad exigiendo algo que sabes (contraseña) y algo que tienes (código en el celular)."
        ),
        PerguntaQuiz(
            "¿Cómo identificar un correo de phishing?",
            listOf(

                "1-Viene de un amigo conocido",
                "2-Tiene el logo oficial de la empresa",
                "3-Errores de español y urgencia artificial",
                "4-Ofrece productos con descuento normal"
            ),
            2,
            "Phishing generalmente tiene errores gramaticales, crea sentido de urgencia y pide datos personales."
        ),
        PerguntaQuiz(
            "¿Cuál es la ventaja de usar un gestor de contraseñas?",
            listOf(

                "1-Comparte contraseñas con amigos",
                "2-Recuerda solo una contraseña para todo",
                "3-Funciona sin internet",
                "4-Crea y almacena contraseñas fuertes automáticamente"
            ),
            3,
            "Los gestores generan contraseñas únicas y fuertes para cada sitio y las almacenan cifradas."
        ),
        PerguntaQuiz(
            "¿Por qué es peligroso usar Wi-Fi público?",
            listOf(

                "1-El internet es más lento",
                "2-Hackers pueden interceptar tus datos",
                "3-El celular se descarga más rápido",
                "4-No se pueden hacer llamadas"
            ),
            1,
            "En redes Wi-Fi públicas, criminales pueden capturar contraseñas e informaciones personales."
        ),
        PerguntaQuiz(
            "¿Cuál es el propósito principal de hacer backup?",
            listOf(
                "1-Proteger datos contra pérdida o daños",
                "2-Liberar espacio en el celular",
                "3-Compartir archivos con otras personas",
                "4-Acelerar el dispositivo"
            ),
            0,
            "El backup garantiza que no pierdas fotos, documentos y otros datos importantes."
        ),
        PerguntaQuiz(
            "¿Qué NO se debe compartir en las redes sociales?",
            listOf(
                "1-Dirección completa y ubicación en tiempo real",
                "2-Fotos de paisajes y viajes",
                "3-Recetas culinarias favoritas",
                "4-Opiniones sobre películas y series"
            ),
            0,
            "Informaciones personales como dirección y ubicación en tiempo real pueden ser usadas por criminales."
        ),
        PerguntaQuiz(
            "¿Cuál de estos es una señal de sitio seguro?",
            listOf(
                "1-Muchos anuncios pop-up",
                "2-HTTPS:// y candado en la barra de dirección",
                "3-URL comenzando con HTTP://",
                "4-Solicitud de instalación de software"
            ),
            1,
            "HTTPS y el candado indican que la conexión es cifrada y segura."
        ),
        PerguntaQuiz(
            "¿Qué hacer al recibir un enlace sospechoso por mensaje?",
            listOf(

                "1-Hacer clic para ver a dónde lleva",
                "2-No hacer clic y verificar la fuente",
                "3-Reenviar a amigos",
                "4-Responder pidiendo más informaciones"
            ),
            1,
            "Los enlaces sospechosos pueden llevar a sitios maliciosos que roban datos o instalan virus."
        ),
        PerguntaQuiz(
            "¿Por qué es importante mantener el sistema operativo actualizado?",
            listOf(

                "1-Solo para tener nuevos recursos visuales",
                "2-Para que el dispositivo quede más bonito",
                "3-Corrige fallas de seguridad descubiertas",
                "4-Solo para mejorar la velocidad"
            ),
            2,
            "Las actualizaciones frecuentemente incluyen correcciones para vulnerabilidades de seguridad."
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        inicializarComponentes()

        // CORREÇÃO: Pegar parâmetros corretamente
        idioma = intent.getStringExtra("IDIOMA") ?: "pt"
        modulo = intent.getStringExtra("MODULO") ?: "Celular"

        tts = TextToSpeech(this, this)
        carregarPerguntas()
        configurarBotoes()
        mostrarPerguntaAtual()
    }

    private fun inicializarComponentes() {
        txtPerguntaQuiz = findViewById(R.id.txtPerguntaQuiz)
        radioGroupOpcoes = findViewById(R.id.radioGroupOpcoes)
        btnProximaPergunta = findViewById(R.id.btnProximaPergunta)
        btnAvancarQuiz = findViewById(R.id.btnAvancarQuiz)
        btnOuvirQuiz = findViewById(R.id.btnOuvirQuiz)
        progressBarQuiz = findViewById(R.id.progressBarQuiz)
        txtProgressoQuiz = findViewById(R.id.txtProgressoQuiz)
        txtExplicacao = findViewById(R.id.txtExplicacao)
        txtAudioEmTempoReal = findViewById(R.id.txtAudioEmTempoReal)
    }

    private fun carregarPerguntas() {
        try {
            println("🔄 DEBUG: Carregando perguntas - Módulo: $modulo, Idioma: $idioma")


            perguntas = when {
                modulo == "Desktop" && idioma == "es" -> quizDesktopEspanol
                modulo == "Desktop" && idioma == "pt" -> quizDesktopPortugues
                modulo == "Celular" && idioma == "es" -> quizCelularEspanol
                modulo == "Celular" && idioma == "pt" -> quizCelularPortugues
                modulo == "Internet" && idioma == "es" -> quizInternetEspanol
                modulo == "Internet" && idioma == "pt" -> quizInternetPortugues
                modulo == "Email" && idioma == "es" -> quizEmailEspanol
                modulo == "Email" && idioma == "pt" -> quizEmailPortugues
                modulo == "Trabalho" && idioma == "es" -> quizTrabalhoEspanol
                modulo == "Trabalho" && idioma == "pt" -> quizTrabalhoPortugues
                modulo == "Compras" && idioma == "es" -> quizComprasEspanol
                modulo == "Compras" && idioma == "pt" -> quizComprasPortugues
                modulo == "Banco"  && idioma == "es" -> quizBancoEspanol
                modulo == "Banco"  && idioma == "pt" -> quizBancoPortugues
                modulo == "Segurança"  && idioma == "es" -> quizSegurancaEspanol
                modulo == "Segurança"  && idioma == "pt" -> quizSegurancaPortugues

                else -> {
                    println("⚠️  Módulo não encontrado: $modulo - Usando fallback")
                    quizCelularPortugues
                }
            }

            println("✅ DEBUG: ${perguntas.size} perguntas carregadas para módulo: $modulo")
            progressBarQuiz.max = perguntas.size

        } catch (e: Exception) {
            println("❌ ERRO em carregarPerguntas: ${e.message}")
            e.printStackTrace()
            // Fallback para não crashar
            perguntas = quizCelularPortugues
            progressBarQuiz.max = perguntas.size
        }
    }

    private fun configurarBotoes() {
        // Botão Ouvir
        btnOuvirQuiz.setOnClickListener {
            if (tts.isSpeaking) {
                pausarAudioQuiz()
                btnOuvirQuiz.setImageResource(android.R.drawable.ic_media_play)
            } else {
                falarPerguntaAtual()
                btnOuvirQuiz.setImageResource(android.R.drawable.ic_media_pause)
            }
        }

        // Botão "Ver Resposta"
        btnProximaPergunta.setOnClickListener {
            verificarResposta()
        }

        // Botão "Próxima Pergunta"
        btnAvancarQuiz.setOnClickListener {
            avancarParaProximaPergunta()
        }

        // Botão Voltar
        findViewById<ImageButton>(R.id.btnVoltarQuiz).setOnClickListener {
            pausarAudioQuiz()
            finish()
        }
    }

    private fun mostrarPerguntaAtual() {
        if (perguntaAtual < perguntas.size) {
            val pergunta = perguntas[perguntaAtual]
            pausarAudioQuiz()

            txtPerguntaQuiz.text = pergunta.pergunta
            radioGroupOpcoes.removeAllViews()
            radioGroupOpcoes.clearCheck()

            pergunta.opcoes.forEachIndexed { index, opcao ->
                val radioButton = RadioButton(this).apply {
                    text = opcao
                    id = View.generateViewId()
                    textSize = 14f
                    setPadding(20, 15, 20, 15)
                    setTextColor(Color.WHITE)
                    setBackgroundColor(Color.parseColor("#8B4789"))
                    // Quebra de texto para opções longas
                    isSingleLine = false
                    maxLines = 3
                }
                radioGroupOpcoes.addView(radioButton)
                // Espaçamento entre opções
                val layoutParams = radioButton.layoutParams as LinearLayout.LayoutParams
                layoutParams.setMargins(0, 0, 0, 8)  // ⬅ MARGEM INFERIOR
                radioButton.layoutParams = layoutParams
            }

            // Resetar estado da tela
            txtExplicacao.visibility = View.GONE
            txtExplicacao.text = ""
            atualizarProgresso()

            // Resetar botões
            btnOuvirQuiz.setImageResource(android.R.drawable.ic_media_play)
            btnOuvirQuiz.isEnabled = true
            btnProximaPergunta.isEnabled = true
            btnProximaPergunta.visibility = View.VISIBLE
            btnProximaPergunta.text = if (idioma == "es") "Ver Respuesta" else "Veja Resposta"
            btnAvancarQuiz.visibility = View.GONE

            limparTextoAudio()
        } else {
            finalizarQuiz()
        }
    }

    private fun verificarResposta() {
        val idSelecionado = radioGroupOpcoes.checkedRadioButtonId

        if (idSelecionado == -1) {
            val mensagem = if (idioma == "es") {
                "Por favor selecciona una respuesta antes de continuar"
            } else {
                "Por favor selecione uma resposta antes de continuar"
            }
            Toast.makeText(this, "❌ $mensagem", Toast.LENGTH_LONG).show()
            return
        }

        pausarAudioQuiz()
        btnOuvirQuiz.setImageResource(android.R.drawable.ic_media_play)

        val radioSelecionado = findViewById<RadioButton>(idSelecionado)
        val respostaIndex = radioGroupOpcoes.indexOfChild(radioSelecionado)
        val pergunta = perguntas[perguntaAtual]

        btnProximaPergunta.isEnabled = false
        btnOuvirQuiz.isEnabled = false

        destacarRespostas(pergunta.respostaCorreta, respostaIndex)

        val respostaCorreta = (respostaIndex == pergunta.respostaCorreta)

        if (respostaCorreta) {
            pontuacao++
            val mensagemToast = if (idioma == "es") "¡Correcto! 🎉" else "Correto! 🎉"
            Toast.makeText(this, mensagemToast, Toast.LENGTH_SHORT).show()

            // ✅ CORRETO - Mostra explicação completa
            txtExplicacao.text = pergunta.explicacao
            txtExplicacao.setTextColor(Color.parseColor("#4CAF50"))
            txtExplicacao.visibility = View.VISIBLE

            // 🔊 ÁUDIO para resposta correta
            val textoAudio = if (idioma == "es") {
                "¡Correcto! ${pergunta.explicacao}"
            } else {
                "Correto! ${pergunta.explicacao}"
            }
            falarTexto(textoAudio)

        } else {
            val mensagemToast = if (idioma == "es") "Incorrecto ❌" else "Incorreto ❌"
            Toast.makeText(this, mensagemToast, Toast.LENGTH_SHORT).show()

            // ✅ ERRADO - Mostra a resposta correta + explicação
            val respostaCerta = pergunta.opcoes[pergunta.respostaCorreta]
            val respostaCertaSimples = respostaCerta.split(" - ")[0] // Remove descrição longa
            val respostaSelecionadaSimples = radioSelecionado.text.split(" - ")[0]

            val mensagemErro = if (idioma == "es") {
                "❌ Tu respuesta: $respostaSelecionadaSimples\n" +
                        "✅ Correcto: $respostaCertaSimples\n\n" +
                        "${pergunta.explicacao}"
            } else {
                "❌ Sua resposta: $respostaSelecionadaSimples\n" +
                        "✅ Correto: $respostaCertaSimples\n\n" +
                        "${pergunta.explicacao}"
            }

            txtExplicacao.text = mensagemErro
            txtExplicacao.setTextColor(Color.parseColor("#F44336"))
            txtExplicacao.visibility = View.VISIBLE

            // 🔊 ÁUDIO para resposta errada
            val textoAudio = if (idioma == "es") {
                "Incorrecto. La respuesta correcta es: $respostaCertaSimples. ${pergunta.explicacao}"
            } else {
                "Incorreto. A resposta correta é: $respostaCertaSimples. ${pergunta.explicacao}"
            }
            falarTexto(textoAudio)
        }

        // Trocar botões
        btnProximaPergunta.visibility = View.GONE
        btnAvancarQuiz.visibility = View.VISIBLE

        btnAvancarQuiz.text = if (perguntaAtual == perguntas.size - 1) {
            if (idioma == "es") "Ver Resultado" else "Ver Resultado"
        } else {
            if (idioma == "es") "Próxima Pregunta" else "Próxima Pergunta"
        }
    }
    private fun mostrarTextoAudio(texto: String) {
        runOnUiThread {
            txtAudioEmTempoReal.text = texto
            txtAudioEmTempoReal.visibility = View.VISIBLE
        }
    }

    private fun limparTextoAudio() {
        runOnUiThread {
            txtAudioEmTempoReal.text = ""
            txtAudioEmTempoReal.visibility = View.GONE
        }
    }

    private fun mostrarTextoAudioProgressivo(texto: String) {
        runOnUiThread {
            txtAudioEmTempoReal.text = texto
            txtAudioEmTempoReal.visibility = View.VISIBLE
        }
    }

    private fun destacarRespostas(indiceCorreto: Int, indiceSelecionado: Int) {
        val corCorreta = Color.parseColor("#4CAF50")
        val corErrada = Color.parseColor("#F44336")
        val corNormal = Color.parseColor("#8B4789")

        for (i in 0 until radioGroupOpcoes.childCount) {
            val radioButton = radioGroupOpcoes.getChildAt(i) as RadioButton

            when {
                i == indiceCorreto -> {
                    radioButton.setBackgroundColor(corCorreta)
                    radioButton.setTextColor(Color.WHITE)
                }
                i == indiceSelecionado && i != indiceCorreto -> {
                    radioButton.setBackgroundColor(corErrada)
                    radioButton.setTextColor(Color.WHITE)
                }
                else -> {
                    radioButton.setBackgroundColor(corNormal)
                    radioButton.setTextColor(Color.WHITE)
                }
            }
            radioButton.isEnabled = false
        }
    }
    private fun avancarParaProximaPergunta() {
        try {
            pausarAudioQuiz()
            btnOuvirQuiz.setImageResource(android.R.drawable.ic_media_play)
            perguntaAtual++

            if (perguntaAtual < perguntas.size) {
                mostrarPerguntaAtual()
            } else {
                finalizarQuiz()
            }
        } catch (e: Exception) {
            println("❌ Erro em avancarParaProximaPergunta: ${e.message}")
            Toast.makeText(this, "Erro ao avançar pergunta", Toast.LENGTH_SHORT).show()
        }
    }
    private fun atualizarProgresso() {
        val progresso = perguntaAtual + 1
        progressBarQuiz.progress = progresso

        val textoProgresso = if (idioma == "es") {
            "Pregunta $progresso de ${perguntas.size}"
        } else {
            "Pergunta $progresso de ${perguntas.size}"
        }
        txtProgressoQuiz.text = textoProgresso
    }

    private fun finalizarQuiz() {
        pausarAudioQuiz()
        mostrarResultadoLocal()
    }

    private fun mostrarResultadoLocal() {
        val porcentagem = (pontuacao * 100) / perguntas.size

        val mensagemTitulo = if (idioma == "es") "¡Quiz Completado!" else "Quiz Concluído!"
        val mensagemPontuacao = "$pontuacao/${perguntas.size} ($porcentagem%)"

        val mensagemResultado = when {
            porcentagem >= 90 -> if (idioma == "es") "¡Excelente! 🎉" else "Excelente! 🎉"
            porcentagem >= 70 -> if (idioma == "es") "¡Muy bien! 👍" else "Muito bom! 👍"
            porcentagem >= 50 -> if (idioma == "es") "¡Bien! 😊" else "Bom! 😊"
            else -> if (idioma == "es") "¡Sigue practicando! 📚" else "Continue praticando! 📚"
        }

        android.app.AlertDialog.Builder(this)
            .setTitle(mensagemTitulo)
            .setMessage("$mensagemPontuacao\n\n$mensagemResultado")
            .setPositiveButton(if (idioma == "es") "Volver" else "Voltar") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setCancelable(false)
            .show()
    }

    // FUNÇÕES DE ÁUDIO
    private fun pausarAudioQuiz() {
        if (ttsInicializado && tts.isSpeaking) {
            tts.stop()
            // ✅ LIMPAR texto quando áudio parar
            limparTextoAudio()
        }
    }

    private fun falarPerguntaAtual() {
        if (!ttsInicializado) {
            val mensagem = if (idioma == "es") "Sistema de voz aún no está listo" else "Sistema de voz ainda não está pronto"
            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
            return
        }

        val pergunta = perguntas[perguntaAtual]
        val locale = if (idioma == "es") Locale("es", "ES") else Locale("pt", "BR")
        tts.language = locale

        val textoParaFalar = StringBuilder()
        textoParaFalar.append("${pergunta.pergunta}. ")

        pergunta.opcoes.forEachIndexed { index, opcao ->
            val opcaoSimples = opcao.split(" - ")[0]
            textoParaFalar.append("Opção ${index + 1}: $opcaoSimples. ")
        }

        tts.speak(textoParaFalar.toString(), TextToSpeech.QUEUE_FLUSH, null, "pergunta_${perguntaAtual}")
    }
    private fun falarTexto(texto: String) {
        if (!ttsInicializado) {
            return
        }

        pausarAudioQuiz()
        val locale = if (idioma == "es") Locale("es", "ES") else Locale("pt", "BR")
        tts.language = locale

        // ✅ MOSTRAR texto do feedback na tela
        mostrarTextoAudio("🔊 Feedback: $texto")

        tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null, "feedback_resposta")
    }
    
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            ttsInicializado = true
        } else {
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
