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

class DesktopActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private var ttsInicializado = false
    private var idioma: String = "pt"
    private var liçãoAtual = 0
    private var audioPausado = false



    // Lista de lições com conteúdo expandido mantendo as analogias originais
    private val liçõesPortugues = listOf(
        Lição(
            "Conhecendo o Computador",
            "CPU é o cérebro, monitor são os olhos, teclado e mouse são as mãos do computador. É como uma cozinha organizada: cada parte tem sua função!\n\n" +
                    "• CPU (Processador): O cérebro que processa todas as informações\n" +
                    "• Monitor: Os olhos que mostram tudo o que acontece\n" +
                    "• Teclado: As mãos para digitar e dar comandos\n" +
                    "• Mouse: Os dedos para apontar e selecionar\n" +
                    "• Gabinete: O corpo que protege todos os órgãos\n" +
                    "• Impressora: A boca que fala no papel\n\n" +
                    "Dica: Assim como numa cozinha, cada utensílio tem seu propósito específico!",
            "🖥️",
            "computador_partes"
        ),
        Lição(
            "Meu Primeiro Windows",
            "Área de trabalho é sua sala, menu Iniciar é a porta para todos os programas. Janelas são como portas de diferentes cômodos que você pode abrir e fechar.\n\n" +
                    "• Área de Trabalho: Sua sala principal, personalizável\n" +
                    "• Menu Iniciar: A porta de entrada para todo o sistema\n" +
                    "• Ícones: Objetos decorativos da sua sala digital\n" +
                    "• Barra de Tarefas: A prateleira onde deixa programas acessíveis\n" +
                    "• Janelas: Portas que levam a diferentes ambientes\n\n" +
                    "Como navegar:\n" +
                    "1. Clique na 'porta' (Menu Iniciar) para explorar\n" +
                    "2. Arrume sua 'sala' com ícones úteis\n" +
                    "3. Abra 'portas' (janelas) conforme necessário",
            "🪟",
            "area_trabalho"
        ),
        Lição(
            "Dominando o Mouse",
            "Clique esquerdo para selecionar, clique direito para opções, arraste para mover. O mouse é como sua mão digital - ele aponta e pega coisas na tela!\n\n" +
                    "Ações da mão digital:\n" +
                    "• Clique Esquerdo: Apontar o dedo para selecionar\n" +
                    "• Clique Duplo: Bater na porta para entrar rápido\n" +
                    "• Clique Direito: Mostrar o leque de opções\n" +
                    "• Arrastar: Pegar e levar objetos para outro lugar\n" +
                    "• Roda Central: Folhear páginas como um livro\n\n" +
                    "Exercícios práticos:\n" +
                    "1. Aponte (clique) nos ícones da sala\n" +
                    "2. Bata duas vezes (clique duplo) para abrir portas\n" +
                    "3. Mostre opções (clique direito) nos objetos\n" +
                    "4. Mova (arraste) os objetos pela sala",
            "🐭",
            "mouse_acoes"
        ),
        Lição(
            "Teclado - Minhas Primeiras Letras",
            "Tecla Enter confirma, Espaço dá espaço, Backspace apaga, Shift faz maiúsculas. É como uma máquina de escrever moderna!\n\n" +
                    "Teclas essenciais:\n" +
                    "• Enter ↵: O botão 'vai' que confirma tudo\n" +
                    "• Espaço: O respiro entre as palavras\n" +
                    "• Backspace ←: A borracha digital\n" +
                    "• Delete: O apagador do lado direito\n" +
                    "• Shift ⇧: O elevador das letras\n" +
                    "• Caps Lock: O travador de maiúsculas\n" +
                    "• Tab ↹: O pulador de campos\n\n" +
                    "Zonas do teclado:\n" +
                    "1. Letras e números (área principal)\n" +
                    "2. Teclado numérico (calculadora)\n" +
                    "3. Teclas de função (atalhos especiais)\n" +
                    "4. Setas de navegação (GPS da tela)",
            "⌨️",
            "teclado_teclas"
        ),
        Lição(
            "Pastas - Organizando Minhas Coisas",
            "Pastas são como gavetas: criamos para guardar documentos organizados. Pode ter pastas dentro de pastas, como gavetas dentro de armários.\n\n" +
                    "Sistema de organização:\n" +
                    "• Pastas: Gavetas digitais para documentos\n" +
                    "• Subpastas: Gavetas dentro de gavetas\n" +
                    "• Arquivos: Objetos guardados nas gavetas\n" +
                    "• Nomes: Etiquetas para identificar conteúdo\n\n" +
                    "Como organizar:\n" +
                    "1. Crie gavetas (pastas) por categoria\n" +
                    "2. Dê nomes claros às gavetas\n" +
                    "3. Guarde documentos nas gavetas certas\n" +
                    "4. Use gavetas dentro de gavetas para detalhes\n\n" +
                    "Exemplo de organização:\n" +
                    "• Gaveta 'Receitas' → Subgaveta 'Doces'\n" +
                    "• Gaveta 'Trabalho' → Subgaveta 'Relatórios'",
            "📁",
            "pastas_organizacao"
        ),
        Lição(
            "Internet no Computador",
            "Navegador é seu carro para internet, barra de endereços é o GPS dos sites. Digite o endereço e pressione Enter para viajar!\n\n" +
                    "Componentes da viagem digital:\n" +
                    "• Navegador: Seu carro para rodar na internet\n" +
                    "• Barra de Endereços: GPS que leva aos destinos\n" +
                    "• Motor de Busca: Mapa para encontrar lugares\n" +
                    "• Favoritos: Lugares preferidos guardados\n" +
                    "• Histórico: Roteiro das viagens anteriores\n\n" +
                    "Como viajar com segurança:\n" +
                    "1. Digite o endereço exato no GPS\n" +
                    "2. Verifique se o destino é confiável\n" +
                    "3. Não entre em ruas desconhecidas (links suspeitos)\n" +
                    "4. Guarde seus documentos (senhas) em segurança\n\n" +
                    "Destinos úteis:\n" +
                    "• Google.com - Seu mapa universal\n" +
                    "• YouTube.com - Cinema particular\n" +
                    "• Wikipedia.org - Biblioteca mundial",
            "🌐",
            "navegador_internet"
        ),
        Lição(
            "Email no Computador",
            "Email é carta digital: @ é o endereço, anexo é o envelope com fotos. Escreva, coloque o endereço e envie - chega em segundos!\n\n" +
                    "Partes da carta digital:\n" +
                    "• Para: Endereço do destinatário\n" +
                    "• Assunto: Título da carta\n" +
                    "• Corpo: Conteúdo da mensagem\n" +
                    "• Anexo: Envelope com fotos/documentos\n" +
                    "• @: Símbolo que localiza o destinatário\n\n" +
                    "Como escrever uma boa carta digital:\n" +
                    "1. Saudação inicial (Querido, Olá)\n" +
                    "2. Mensagem clara e objetiva\n" +
                    "3. Despedida educada (Atenciosamente)\n" +
                    "4. Assinatura com seu nome\n\n" +
                    "Dicas importantes:\n" +
                    "• Confirme sempre o endereço do destinatário\n" +
                    "• Use assunto que resuma o conteúdo\n" +
                    "• Revise antes de enviar\n" +
                    "• Cuidado com cartas de desconhecidos",
            "📧",
            "email_tela"
        ),
        Lição(
            "Word - Meus Primeiros Documentos",
            "Word é papel digital: digite textos, salve e imprima como uma máquina de escrever moderna. Pode mudar cores, tamanhos e até colocar imagens!\n\n" +
                    "Funcionalidades do papel digital:\n" +
                    "• Digitação: Escrever como numa máquina de escrever\n" +
                    "• Formatação: Mudar aparência do texto\n" +
                    "• Imagens: Colocar fotos no documento\n" +
                    "• Tabelas: Organizar informações em linhas e colunas\n" +
                    "• Salvar: Guardar o documento no computador\n\n" +
                    "Primeiros passos:\n" +
                    "1. Abra o Word (encontre na lista de programas)\n" +
                    "2. Comece a digitar seu texto\n" +
                    "3. Use Ctrl+S para salvar frequentemente\n" +
                    "4. Ctrl+P para imprimir quando pronto\n\n" +
                    "Formatação básica:\n" +
                    "• Negrito: Destacar texto importante\n" +
                    "• Itálico: Dar ênfase suave\n" +
                    "• Sublinhado: Chamar atenção\n" +
                    "• Tamanho: Ajustar para melhor leitura",
            "📄",
            "word_documento"
        ),
        Lição(
            "Impressora - Trazendo para o Papel",
            "Impressora é máquina de fotocópia pessoal: envia da tela para o papel. Escolha o documento e clique em imprimir - magicamente aparece no papel!\n\n" +
                    "Tipos de impressora:\n" +
                    "• Jato de Tinta: Usa tinta líquida - boa para fotos\n" +
                    "• Laser: Usa pó de toner - rápida para texto\n" +
                    "• Multifuncional: Imprime, copia e escaneia\n\n" +
                    "Como imprimir:\n" +
                    "1. Abra o documento que quer imprimir\n" +
                    "2. Clique em Arquivo → Imprimir\n" +
                    "3. Escolha a impressora correta\n" +
                    "4. Selecione o número de cópias\n" +
                    "5. Clique no botão Imprimir\n\n" +
                    "Dicas de economia:\n" +
                    "• Use modo rascunho para textos simples\n" +
                    "• Imprima frente e verso quando possível\n" +
                    "• Revise na tela antes de imprimir\n" +
                    "• Reutilize papéis para rascunho",
            "🖨️",
            "impressora_funcionamento"
        ),
        Lição(
            "Segurança no Computador",
            "Senha forte é como chave forte: misture letras, números e símbolos. Cuidado com links estranhos! Sempre desconfie de ofertas milagrosas.\n\n" +
                    "Proteções essenciais:\n" +
                    "• Antivírus: Vacina contra doenças digitais\n" +
                    "• Firewall: Muro de proteção contra invasores\n" +
                    "• Atualizações: Reforços de segurança\n" +
                    "• Backups: Cópias de segurança dos arquivos\n\n" +
                    "Como criar senhas fortes:\n" +
                    "• Use no mínimo 8 caracteres\n" +
                    "• Misture letras, números e símbolos\n" +
                    "• Não use informações pessoais\n" +
                    "• Use senhas diferentes para cada site\n\n" +
                    "Cuidados online:\n" +
                    "• Desconfie de emails suspeitos\n" +
                    "• Não clique em links desconhecidos\n" +
                    "• Verifique sites antes de comprar\n" +
                    "• Use redes Wi-Fi seguras",
            "🔒",
            "seguranca_senha"
        )
    )

    private val liçõesEspanol = listOf(
        Lição(
            "Conociendo la Computadora",
            "CPU es el cerebro, monitor son los ojos, teclado y mouse son las manos de la computadora. ¡Es como una cocina organizada: cada parte tiene su función!\n\n" +
                    "• CPU (Procesador): El cerebro que procesa toda la información\n" +
                    "• Monitor: Los ojos que muestran todo lo que sucede\n" +
                    "• Teclado: Las manos para escribir y dar comandos\n" +
                    "• Mouse: Los dedos para apuntar y seleccionar\n" +
                    "• Gabinete: El cuerpo que protege todos los órganos\n" +
                    "• Impresora: La boca que habla en el papel\n\n" +
                    "Consejo: ¡Como en una cocina, cada utensilio tiene su propósito específico!",
            "🖥️",
            "computador_partes"
        ),
        Lição(
            "Mi Primer Windows",
            "El escritorio es tu sala, el menú Inicio es la puerta a todos los programas. Las ventanas son como puertas de diferentes habitaciones que puedes abrir y cerrar.\n\n" +
                    "• Escritorio: Tu sala principal, personalizable\n" +
                    "• Menú Inicio: La puerta de entrada a todo el sistema\n" +
                    "• Iconos: Objetos decorativos de tu sala digital\n" +
                    "• Barra de Tareas: El estante donde dejas programas accesibles\n" +
                    "• Ventanas: Puertas que llevan a diferentes ambientes\n\n" +
                    "Cómo navegar:\n" +
                    "1. Haz clic en la 'puerta' (Menú Inicio) para explorar\n" +
                    "2. Organiza tu 'sala' con iconos útiles\n" +
                    "3. Abre 'puertas' (ventanas) según sea necesario",
            "🪟",
            "area_trabalho"
        ),
        Lição(
            "Dominando el Mouse",
            "Clic izquierdo para seleccionar, clic derecho para opciones, arrastrar para mover. ¡El mouse es como tu mano digital - apunta y toma cosas en la pantalla!\n\n" +
                    "Acciones de la mano digital:\n" +
                    "• Clic Izquierdo: Apuntar el dedo para seleccionar\n" +
                    "• Doble Clic: Golpear la puerta para entrar rápido\n" +
                    "• Clic Derecho: Mostrar el abanico de opciones\n" +
                    "• Arrastrar: Agarrar y llevar objetos a otro lugar\n" +
                    "• Rueda Central: Hojear páginas como un libro\n\n" +
                    "Ejercicios prácticos:\n" +
                    "1. Apunta (clic) en los iconos de la sala\n" +
                    "2. Golpea dos veces (doble clic) para abrir puertas\n" +
                    "3. Muestra opciones (clic derecho) en los objetos\n" +
                    "4. Mueve (arrastra) los objetos por la sala",
            "🐭",
            "mouse_acoes"
        ),
        Lição(
            "Teclado - Mis Primeras Letras",
            "Tecla Enter confirma, Espacio da espacio, Backspace borra, Shift hace mayúsculas. ¡Es como una máquina de escribir moderna!\n\n" +
                    "Teclas esenciales:\n" +
                    "• Enter ↵: El botón 'ir' que confirma todo\n" +
                    "• Espacio: El respiro entre las palabras\n" +
                    "• Backspace ←: La goma de borrar digital\n" +
                    "• Delete: El borrador del lado derecho\n" +
                    "• Shift ⇧: El elevador de las letras\n" +
                    "• Caps Lock: El trabador de mayúsculas\n" +
                    "• Tab ↹: El saltador de campos\n\n" +
                    "Zonas del teclado:\n" +
                    "1. Letras y números (área principal)\n" +
                    "2. Teclado numérico (calculadora)\n" +
                    "3. Teclas de función (atajos especiales)\n" +
                    "4. Flechas de navegación (GPS de la pantalla)",
            "⌨️",
            "teclado_teclas"
        ),
        Lição(
            "Carpetas - Organizando Mis Cosas",
            "Las carpetas son como cajones: las creamos para guardar documentos organizados. Puede tener carpetas dentro de carpetas, como cajones dentro de armarios.\n\n" +
                    "Sistema de organización:\n" +
                    "• Carpetas: Cajones digitales para documentos\n" +
                    "• Subcarpetas: Cajones dentro de cajones\n" +
                    "• Archivos: Objetos guardados en los cajones\n" +
                    "• Nombres: Etiquetas para identificar contenido\n\n" +
                    "Cómo organizar:\n" +
                    "1. Crea cajones (carpetas) por categoría\n" +
                    "2. Dale nombres claros a los cajones\n" +
                    "3. Guarda documentos en los cajones correctos\n" +
                    "4. Usa cajones dentro de cajones para detalles\n\n" +
                    "Ejemplo de organización:\n" +
                    "• Cajón 'Recetas' → Subcajón 'Postres'\n" +
                    "• Cajón 'Trabajo' → Subcajón 'Informes'",
            "📁",
            "pastas_organizacao"
        ),
        Lição(
            "Internet en la Computadora",
            "El navegador es tu auto para internet, la barra de direcciones es el GPS de los sitios. ¡Escribe la dirección y presiona Enter para viajar!\n\n" +
                    "Componentes del viaje digital:\n" +
                    "• Navegador: Tu auto para recorrer internet\n" +
                    "• Barra de Direcciones: GPS que lleva a los destinos\n" +
                    "• Motor de Búsqueda: Mapa para encontrar lugares\n" +
                    "• Favoritos: Lugares preferidos guardados\n" +
                    "• Historial: Itinerario de viajes anteriores\n\n" +
                    "Cómo viajar con seguridad:\n" +
                    "1. Escribe la dirección exacta en el GPS\n" +
                    "2. Verifica que el destino sea confiable\n" +
                    "3. No entres en calles desconocidas (enlaces sospechosos)\n" +
                    "4. Guarda tus documentos (contraseñas) con seguridad\n\n" +
                    "Destinos útiles:\n" +
                    "• Google.com - Tu mapa universal\n" +
                    "• YouTube.com - Cine particular\n" +
                    "• Wikipedia.org - Biblioteca mundial",
            "🌐",
            "navegador_internet"
        ),
        Lição(
            "Email en la Computadora",
            "Email es carta digital: @ es la dirección, archivo adjunto es el sobre con fotos. Escribe, coloca la dirección y envía - ¡llega en segundos!\n\n" +
                    "Partes de la carta digital:\n" +
                    "• Para: Dirección del destinatario\n" +
                    "• Asunto: Título de la carta\n" +
                    "• Cuerpo: Contenido del mensaje\n" +
                    "• Archivo Adjunto: Sobre con fotos/documentos\n" +
                    "• @: Símbolo que localiza al destinatario\n\n" +
                    "Cómo escribir una buena carta digital:\n" +
                    "1. Saludo inicial (Querido, Hola)\n" +
                    "2. Mensaje clara y objetiva\n" +
                    "3. Despedida educada (Atentamente)\n" +
                    "4. Firma con tu nombre\n\n" +
                    "Consejos importantes:\n" +
                    "• Confirma siempre la dirección del destinatario\n" +
                    "• Usa asunto que resuma el contenido\n" +
                    "• Revisa antes de enviar\n" +
                    "• Cuidado con cartas de desconocidos",
            "📧",
            "email_tela"
        ),
        Lição(
            "Word - Mis Primeros Documentos",
            "Word es papel digital: escribe textos, guarda e imprime como una máquina de escribir moderna. ¡Puedes cambiar colores, tamaños y hasta poner imágenes!\n\n" +
                    "Funcionalidades del papel digital:\n" +
                    "• Escritura: Escribir como en máquina de escribir\n" +
                    "• Formato: Cambiar apariencia del texto\n" +
                    "• Imágenes: Poner fotos en el documento\n" +
                    "• Tablas: Organizar información en filas y columnas\n" +
                    "• Guardar: Guardar el documento en la computadora\n\n" +
                    "Primeros pasos:\n" +
                    "1. Abre Word (encuéntralo en la lista de programas)\n" +
                    "2. Empieza a escribir tu texto\n" +
                    "3. Usa Ctrl+S para guardar frecuentemente\n" +
                    "4. Ctrl+P para imprimir cuando esté listo\n\n" +
                    "Formato básico:\n" +
                    "• Negrita: Resaltar texto importante\n" +
                    "• Cursiva: Dar énfasis suave\n" +
                    "• Subrayado: Llamar la atención\n" +
                    "• Tamaño: Ajustar para mejor lectura",
            "📄",
            "word_documento"
        ),
        Lição(
            "Impresora - Llevando al Papel",
            "La impresora es máquina de fotocopia personal: envía de la pantalla al papel. ¡Elige el documento y haz clic en imprimir - mágicamente aparece en papel!\n\n" +
                    "Tipos de impresora:\n" +
                    "• Inyección de Tinta: Usa tinta líquida - buena para fotos\n" +
                    "• Láser: Usa polvo de tóner - rápida para texto\n" +
                    "• Multifuncional: Imprime, copia y escanea\n\n" +
                    "Cómo imprimir:\n" +
                    "1. Abre el documento que quieres imprimir\n" +
                    "2. Haz clic en Archivo → Imprimir\n" +
                    "3. Elige la impresora correcta\n" +
                    "4. Selecciona el número de copias\n" +
                    "5. Haz clic en el botón Imprimir\n\n" +
                    "Consejos de economía:\n" +
                    "• Usa modo borrador para textos simples\n" +
                    "• Imprime a doble cara cuando sea posible\n" +
                    "• Revisa en pantalla antes de imprimir\n" +
                    "• Reutiliza papeles para borrador",
            "🖨️",
            "impressora_funcionamento"
        ),
        Lição(
            "Seguridad en la Computadora",
            "Contraseña fuerte es como llave fuerte: mezcla letras, números y símbolos. ¡Cuidado con enlaces extraños! Siempre desconfía de ofertas milagrosas.\n\n" +
                    "Protecciones esenciales:\n" +
                    "• Antivirus: Vacuna contra enfermedades digitales\n" +
                    "• Firewall: Muro de protección contra invasores\n" +
                    "• Actualizaciones: Refuerzos de seguridad\n" +
                    "• Copias de Seguridad: Respaldo de los archivos\n\n" +
                    "Cómo crear contraseñas fuertes:\n" +
                    "• Usa mínimo 8 caracteres\n" +
                    "• Mezcla letras, números y símbolos\n" +
                    "• No uses información personal\n" +
                    "• Usa contraseñas diferentes para cada sitio\n\n" +
                    "Cuidados online:\n" +
                    "• Desconfía de emails sospechosos\n" +
                    "• No hagas clic en enlaces desconocidos\n" +
                    "• Verifica sitios antes de comprar\n" +
                    "• Usa redes Wi-Fi seguras",
            "🔒",
            "seguranca_senha"
        )
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desktop)

        // Detectar idioma
        idioma = intent.getStringExtra("IDIOMA") ?: "pt"

        // Mudar título da tela
        val txtTituloTela = findViewById<TextView>(R.id.txtTituloTela)
        if (txtTituloTela != null) {
            val titulo = if (idioma == "es") {
                "Desktop - La Computadora"
            } else {
                "Desktop - Meu Computador"
            }
            txtTituloTela.text = titulo
        }

        // Inicializar componentes
        tts = TextToSpeech(this, this)
        configurarBotoes()
        configurarCliqueImagem()
        mostrarLicaoAtual()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            ttsInicializado = true
            val locale = if (idioma == "es") Locale("es", "ES") else Locale("pt", "BR")
            val result = tts.setLanguage(locale)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                val mensagem = if (idioma == "es") "Idioma no soportado" else "Idioma não suportado"
                Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
            } else {
                val mensagem = if (idioma == "es") "¡Sistema de voz listo!" else "Sistema de voz pronto!"
                Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
            }
        } else {
            ttsInicializado = false
            val mensagem = if (idioma == "es") "Error en el sistema de voz" else "Erro no sistema de voz"
            Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
        }
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
                    "¡Felicitaciones! Completaste el módulo Desktop! 🎉"
                } else {
                    "Parabéns! Você completou o módulo Desktop! 🎉"
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

        // No DesktopActivity - função do botão Quiz
        btnQuiz.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("IDIOMA", idioma)
            intent.putExtra("MODULO", "Desktop") // DEVE SER "Desktop"
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





    override fun onDestroy() {
        if (ttsInicializado) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}

data class Lição(
    val titulo: String,
    val conteudo: String,
    val icone: String,
    val imagem: String
)

