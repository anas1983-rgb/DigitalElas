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

class BancoActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private var ttsInicializado = false
    private var idioma: String = "pt"
    private var liçãoAtual = 0
    private var audioPausado = false

    // LISTA DE LIÇÕES DO MÓDULO BANCO - PORTUGUÊS
    private val liçõesPortugues = listOf(
        Lição(
            "Introdução ao Banco Digital",
            "O banco digital é como ter uma agência bancária no seu celular - seguro, rápido e disponível 24 horas por dia!\n\n" +
                    "Vantagens do banco digital:\n" +
                    "• Sem filas: Resolva tudo pelo app\n" +
                    "• Disponível 24h: Acesse a qualquer momento\n" +
                    "• Menos taxas: Geralmente mais econômico\n" +
                    "• Segurança: Proteção com senhas e biometria\n" +
                    "• Controle total: Veja tudo na palma da mão\n\n" +
                    "Bancos digitais populares:\n" +
                    "• Nubank, Inter, C6 Bank\n" +
                    "• Next, PicPay, Mercado Pago\n" +
                    "• Banco do Brasil, Itaú, Bradesco (também têm versão digital)",
            "🏦",
            "banco_introducao"
        ),
        Lição(
            "Criando sua Conta Digital",
            "Abrir uma conta digital é como fazer um cadastro simples - rápido, online e sem burocracia!\n\n" +
                    "Documentos necessários:\n" +
                    "• CPF\n" +
                    "• RG ou CNH\n" +
                    "• Comprovante de residência\n" +
                    "• Selfie para confirmação de identidade\n\n" +
                    "Passo a passo para criar conta:\n" +
                    "1. Baixe o app do banco escolhido\n" +
                    "2. Clique em 'Abrir conta'\n" +
                    "3. Preencha seus dados pessoais\n" +
                    "4. Tire selfies dos documentos\n" +
                    "5. Aguarde a aprovação (minutos ou horas)\n\n" +
                    "Dica: Escolha uma senha forte!",
            "📱",
            "conta_digital"
        ),
        Lição(
            "Acessando seu Banco pelo App",
            "Usar o app do banco é como abrir a porta da sua agência - precisa de chaves seguras!\n\n" +
                    "Formas de acesso:\n" +
                    "• Senha numérica (mais comum)\n" +
                    "• Biometria (digital)\n" +
                    "• Reconhecimento facial\n" +
                    "• Senha de 6 dígitos\n\n" +
                    "Áreas principais do app bancário:\n" +
                    "• Saldo: Quanto você tem na conta\n" +
                    "• Extrato: Histórico de movimentações\n" +
                    "• Transferências: Envie dinheiro\n" +
                    "• Pagamentos: Contas e boletos\n" +
                    "• Cartões: Suas bandeiras virtuais\n\n" +
                    "Dica: Sempre faça logout após usar!",
            "🔐",
            "acesso_app"
        ),
        Lição(
            "Verificando Saldo e Extrato",
            "Consultar saldo e extrato é como ver o diário do seu dinheiro - mostra toda a sua vida financeira!\n\n" +
                    "O que você vê no extrato:\n" +
                    "• Data de cada transação\n" +
                    "• Descrição do movimento\n" +
                    "• Valores (entrada e saída)\n" +
                    "• Saldo após cada operação\n\n" +
                    "Tipos de movimentações:\n" +
                    "• Crédito: Dinheiro que entrou (✔️)\n" +
                    "• Débito: Dinheiro que saiu (❌)\n" +
                    "• Transferências recebidas\n" +
                    "• Pagamentos realizados\n\n" +
                    "Importante: Verifique sempre se reconhece todas as transações!",
            "💰",
            "saldo_extrato"
        ),
        Lição(
            "Fazendo Transferências",
            "Transferir dinheiro é como mandar uma carta por correio - precisa do endereço certo para chegar!\n\n" +
                    "Formas de transferir:\n" +
                    "• TED: Transferência Eletrônica Disponível (rápida)\n" +
                    "• PIX: Transferência instantânea (mais usada)\n" +
                    "• DOC: Transferência para outro banco\n\n" +
                    "Dados necessários para transferir:\n" +
                    "• CPF ou chave PIX do destinatário\n" +
                    "• Nome completo\n" +
                    "• Valor a ser transferido\n" +
                    "• Descrição (opcional)\n\n" +
                    "Passo a passo PIX:\n" +
                    "1. Escolha 'Fazer PIX'\n" +
                    "2. Digite a chave PIX\n" +
                    "3. Confirme os dados\n" +
                    "4. Digite sua senha",
            "🔄",
            "transferencias"
        ),
        Lição(
            "Pagando Contas e Boletos",
            "Pagar contas pelo app é como ter um caixa eletrônico em casa - rápido e sem sair do sofá!\n\n" +
                    "O que pode ser pago:\n" +
                    "• Contas de luz, água, gás\n" +
                    "• Telefone e internet\n" +
                    "• Impostos e taxas\n" +
                    "• Boletos diversos\n" +
                    "• Cartão de crédito\n\n" +
                    "Como pagar um boleto:\n" +
                    "1. Escolha 'Pagar boleto'\n" +
                    "2. Digite o código ou use a câmera\n" +
                    "3. Confirme os dados\n" +
                    "4. Escolha a data do pagamento\n" +
                    "5. Digite sua senha\n\n" +
                    "Dica: Programe pagamentos para não esquecer!",
            "🧾",
            "pagamento_contas"
        ),
        Lição(
            "Cartões Virtuais e Físicos",
            "O cartão virtual é como uma chave reserva - existe, mas você não segura na mão!\n\n" +
                    "Diferenças entre cartões:\n" +
                    "• Virtual: Só existe no app, mais seguro\n" +
                    "• Físico: Plástico tradicional, para lojas\n\n" +
                    "Funções do cartão:\n" +
                    "• Débito: Gasta seu dinheiro da conta\n" +
                    "• Crédito: Gasta dinheiro do banco (paga depois)\n\n" +
                    "Como usar o cartão virtual:\n" +
                    "1. Abra o app do banco\n" +
                    "2. Ache a função 'Cartão virtual'\n" +
                    "3. Veja número, validade e CVV\n" +
                    "4. Use em compras online\n\n" +
                    "Dica: Bloqueie o cartão se perder!",
            "💳",
            "cartoes"
        ),
        Lição(
            "Investimentos Básicos",
            "Investir é como plantar uma árvore - você colhe os frutos no futuro!\n\n" +
                    "Opções simples para começar:\n" +
                    "• Poupança: Segura e conhecida\n" +
                    "• CDB: Rendimento um pouco melhor\n" +
                    "• Tesouro Direto: Emprestar para o governo\n\n" +
                    "Como funciona:\n" +
                    "• Você empresta dinheiro ao banco/governo\n" +
                    "• Eles te pagam juros pelo empréstimo\n" +
                    "• No final, você recebe seu dinheiro + lucro\n\n" +
                    "Primeiros passos:\n" +
                    "1. Comece com valores pequenos\n" +
                    "2. Escolha aplicações seguras\n" +
                    "3. Deixe o dinheiro render\n" +
                    "4. Acompanhe pelo app",
            "📈",
            "investimentos"
        ),
        Lição(
            "Segurança no Banco Digital",
            "Proteger sua conta é como trancar sua casa - precisa de boas fechaduras!\n\n" +
                    "Dicas de segurança:\n" +
                    "• Use senhas fortes e diferentes\n" +
                    "• Ative a biometria\n" +
                    "• Não compartilhe senhas\n" +
                    "• Cuidado com Wi-Fi público\n" +
                    "• Verifique app oficial\n\n" +
                    "Sinais de perigo:\n" +
                    "• Links suspeitos por SMS/email\n" +
                    "• Pessoas pedindo sua senha\n" +
                    "• Apps não oficiais\n" +
                    "• Ofertas milagrosas\n\n" +
                    "O que fazer se suspeitar de fraude:\n" +
                    "1. Bloqueie cartões\n" +
                    "2. Mude senhas\n" +
                    "3. Contate o banco\n" +
                    "4. Registre boletim de ocorrência",
            "🛡️",
            "seguranca_banco"
        ),
        Lição(
            "Resolvendo Problemas e SAC",
            "O SAC é como o porteiro do banco - ajuda a resolver problemas na entrada!\n\n" +
                    "Problemas comuns:\n" +
                    "• Esqueci minha senha\n" +
                    "• Cartão bloqueado\n" +
                    "• Transferência não chegou\n" +
                    "• Cobrança indevida\n\n" +
                    "Canais de atendimento:\n" +
                    "• Chat no app (mais rápido)\n" +
                    "• Telefone do SAC\n" +
                    "• Email oficial\n" +
                    "• Redes sociais\n\n" +
                    "Como resolver no app:\n" +
                    "1. Ache 'Ajuda' ou 'SAC'\n" +
                    "2. Descreva seu problema\n" +
                    "3. Siga as instruções\n" +
                    "4. Guarde o número do protocolo\n\n" +
                    "Dica: Tenha paciência - problemas acontecem!",
            "📞",
            "sac_problemas"
        )
    )

    // LISTA DE LIÇÕES DO MÓDULO BANCO - ESPANHOL
    private val liçõesEspanol = listOf(
        Lição(
            "Introducción a la Banca Digital",
            "¡La banca digital es como tener una sucursal bancaria en tu celular - seguro, rápido y disponible 24 horas al día!\n\n" +
                    "Ventajas de la banca digital:\n" +
                    "• Sin filas: Resuelve todo por la app\n" +
                    "• Disponible 24h: Accede en cualquier momento\n" +
                    "• Menos tasas: Generalmente más económico\n" +
                    "• Seguridad: Protección con contraseñas y biometría\n" +
                    "• Control total: Ve todo en la palma de tu mano\n\n" +
                    "Bancos digitales populares:\n" +
                    "• Nubank, Inter, C6 Bank\n" +
                    "• Next, PicPay, Mercado Pago\n" +
                    "• Banco do Brasil, Itaú, Bradesco (también tienen versión digital)",
            "🏦",
            "banco_introduccion"
        ),
        Lição(
            "Creando tu Cuenta Digital",
            "¡Abrir una cuenta digital es como hacer un registro simple - rápido, online y sin burocracia!\n\n" +
                    "Documentos necesarios:\n" +
                    "• CPF\n" +
                    "• RG o CNH\n" +
                    "• Comprobante de domicilio\n" +
                    "• Selfie para confirmación de identidad\n\n" +
                    "Paso a paso para crear cuenta:\n" +
                    "1. Descarga la app del banco elegido\n" +
                    "2. Haz clic en 'Abrir cuenta'\n" +
                    "3. Completa tus datos personales\n" +
                    "4. Toma selfies de los documentos\n" +
                    "5. Espera la aprobación (minutos u horas)\n\n" +
                    "Consejo: ¡Elige una contraseña fuerte!",
            "📱",
            "cuenta_digital"
        ),
        Lição(
            "Accediendo a tu Banco por la App",
            "¡Usar la app del banco es como abrir la puerta de tu sucursal - necesita llaves seguras!\n\n" +
                    "Formas de acceso:\n" +
                    "• Contraseña numérica (más común)\n" +
                    "• Biometría (huella digital)\n" +
                    "• Reconocimiento facial\n" +
                    "• Contraseña de 6 dígitos\n\n" +
                    "Áreas principales de la app bancaria:\n" +
                    "• Saldo: Cuanto tienes en la cuenta\n" +
                    "• Extracto: Historial de movimientos\n" +
                    "• Transferencias: Envía dinero\n" +
                    "• Pagos: Cuentas y boletos\n" +
                    "• Tarjetas: Tus banderas virtuales\n\n" +
                    "Consejo: ¡Siempre cierra sesión después de usar!",
            "🔐",
            "acceso_app"
        ),
        Lição(
            "Verificando Saldo y Extracto",
            "¡Consultar saldo y extracto es como ver el diario de tu dinero - muestra toda tu vida financiera!\n\n" +
                    "Lo que ves en el extracto:\n" +
                    "• Fecha de cada transacción\n" +
                    "• Descripción del movimiento\n" +
                    "• Valores (entrada y salida)\n" +
                    "• Saldo después de cada operación\n\n" +
                    "Tipos de movimientos:\n" +
                    "• Crédito: Dinero que entró (✔️)\n" +
                    "• Débito: Dinero que salió (❌)\n" +
                    "• Transferencias recibidas\n" +
                    "• Pagos realizados\n\n" +
                    "Importante: ¡Verifica siempre si reconoces todas las transacciones!",
            "💰",
            "saldo_extracto"
        ),
        Lição(
            "Haciendo Transferencias",
            "¡Transferir dinero es como mandar una carta por correo - necesita la dirección correcta para llegar!\n\n" +
                    "Formas de transferir:\n" +
                    "• TED: Transferencia Electrónica Disponible (rápida)\n" +
                    "• PIX: Transferencia instantánea (más usada)\n" +
                    "• DOC: Transferencia para otro banco\n\n" +
                    "Datos necesarios para transferir:\n" +
                    "• CPF o clave PIX del destinatario\n" +
                    "• Nombre completo\n" +
                    "• Valor a ser transferido\n" +
                    "• Descripción (opcional)\n\n" +
                    "Paso a paso PIX:\n" +
                    "1. Elige 'Hacer PIX'\n" +
                    "2. Digita la clave PIX\n" +
                    "3. Confirma los datos\n" +
                    "4. Digita tu contraseña",
            "🔄",
            "transferencias"
        ),
        Lição(
            "Pagando Cuentas y Boletos",
            "¡Pagar cuentas por la app es como tener un cajero automático en casa - rápido y sin salir del sofá!\n\n" +
                    "Lo que puede ser pagado:\n" +
                    "• Cuentas de luz, agua, gas\n" +
                    "• Teléfono e internet\n" +
                    "• Impuestos y tasas\n" +
                    "• Boletos diversos\n" +
                    "• Tarjeta de crédito\n\n" +
                    "Cómo pagar un boleto:\n" +
                    "1. Elige 'Pagar boleto'\n" +
                    "2. Digita el código o usa la cámara\n" +
                    "3. Confirma los datos\n" +
                    "4. Elige la fecha del pago\n" +
                    "5. Digita tu contraseña\n\n" +
                    "Consejo: ¡Programa pagamentos para no olvidar!",
            "🧾",
            "pago_cuentas"
        ),
        Lição(
            "Tarjetas Virtuales y Físicas",
            "¡La tarjeta virtual es como una llave de repuesto - existe, pero no la sostienes en la mano!\n\n" +
                    "Diferencias entre tarjetas:\n" +
                    "• Virtual: Solo existe en la app, más segura\n" +
                    "• Física: Plástico tradicional, para tiendas\n\n" +
                    "Funciones de la tarjeta:\n" +
                    "• Débito: Gasta tu dinero de la cuenta\n" +
                    "• Crédito: Gasta dinero del banco (paga después)\n\n" +
                    "Cómo usar la tarjeta virtual:\n" +
                    "1. Abre la app del banco\n" +
                    "2. Encuentra la función 'Tarjeta virtual'\n" +
                    "3. Ve número, validez y CVV\n" +
                    "4. Usa en compras online\n\n" +
                    "Consejo: ¡Bloquea la tarjeta si la pierdes!",
            "💳",
            "tarjetas"
        ),
        Lição(
            "Inversiones Básicas",
            "¡Invertir es como plantar un árbol - cosechas los frutos en el futuro!\n\n" +
                    "Opciones simples para comenzar:\n" +
                    "• Ahorro: Seguro y conocido\n" +
                    "• CDB: Rendimiento un poco mejor\n" +
                    "• Tesoro Directo: Prestar al gobierno\n\n" +
                    "Cómo funciona:\n" +
                    "• Prestas dinero al banco/gobierno\n" +
                    "• Te pagan intereses por el préstamo\n" +
                    "• Al final, recibes tu dinero + lucro\n\n" +
                    "Primeros pasos:\n" +
                    "1. Comienza con valores pequeños\n" +
                    "2. Elige aplicaciones seguras\n" +
                    "3. Deja que el dinero rinda\n" +
                    "4. Acompaña por la app",
            "📈",
            "inversiones"
        ),
        Lição(
            "Seguridad en la Banca Digital",
            "¡Proteger tu cuenta es como cerrar con llave tu casa - necesita buenas cerraduras!\n\n" +
                    "Consejos de seguridad:\n" +
                    "• Usa contraseñas fuertes y diferentes\n" +
                    "• Activa la biometría\n" +
                    "• No compartas contraseñas\n" +
                    "• Cuidado con Wi-Fi público\n" +
                    "• Verifica app oficial\n\n" +
                    "Señales de peligro:\n" +
                    "• Enlaces sospechosos por SMS/email\n" +
                    "• Personas pidiendo tu contraseña\n" +
                    "• Apps no oficiales\n" +
                    "• Ofertas milagrosas\n\n" +
                    "Qué hacer si sospechas de fraude:\n" +
                    "1. Bloquea tarjetas\n" +
                    "2. Cambia contraseñas\n" +
                    "3. Contacta al banco\n" +
                    "4. Registra boletín de ocurrencia",
            "🛡️",
            "seguridad_banco"
        ),
        Lição(
            "Resolviendo Problemas y SAC",
            "¡El SAC es como el portero del banco - ayuda a resolver problemas en la entrada!\n\n" +
                    "Problemas comunes:\n" +
                    "• Olvidé mi contraseña\n" +
                    "• Tarjeta bloqueada\n" +
                    "• Transferencia no llegó\n" +
                    "• Cobro indebido\n\n" +
                    "Canales de atención:\n" +
                    "• Chat en la app (más rápido)\n" +
                    "• Teléfono del SAC\n" +
                    "• Email oficial\n" +
                    "• Redes sociales\n\n" +
                    "Cómo resolver en la app:\n" +
                    "1. Encuentra 'Ayuda' o 'SAC'\n" +
                    "2. Describe tu problema\n" +
                    "3. Sigue las instrucciones\n" +
                    "4. Guarda el número de protocolo\n\n" +
                    "Consejo: ¡Ten paciencia - los problemas ocurren!",
            "📞",
            "sac_problemas"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desktop) // Usa o mesmo layout do Desktop

        // Configurar idioma
        idioma = intent.getStringExtra("IDIOMA") ?: "pt"

        // Configurar título
        val txtTituloTela = findViewById<TextView>(R.id.txtTituloTela)
        txtTituloTela.text = if (idioma == "es") "Banco - Banca Digital" else "Banco - Banco Digital"

        // Inicializar componentes
        tts = TextToSpeech(this, this)
        configurarBotoes()
        configurarCliqueImagem()
        mostrarLicaoAtual()
    }

    // 🔥 **COLE AQUI TODAS AS FUNÇÕES DO DesktopActivity (igual aos outros módulos):**
    // - configurarBotoes()
    // - mostrarLicaoAtual()
    // - carregarImagemLicao()
    // - falarLicaoAtual()
    // - pausarAudio()
    // - configurarCliqueImagem()
    // - onInit()
    // - onDestroy()

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
                    "¡Felicitaciones! Completaste el módulo Banco! 🎉"
                } else {
                    "Parabéns! Você completou o módulo Banco! 🎉"
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

        // Botão Quiz - ABRE O QUIZ DO MÓDULO BANCO
        btnQuiz.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("IDIOMA", idioma)
            intent.putExtra("MODULO", "Banco") // ← IMPORTANTE: "Banco"
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

