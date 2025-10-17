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

class ComprasActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private var ttsInicializado = false
    private var idioma: String = "pt"
    private var liçãoAtual = 0
    private var audioPausado = false

    // LISTA DE LIÇÕES DO MÓDULO COMPRAS - PORTUGUÊS
    private val liçõesPortugues = listOf(
        Lição(
            "Introdução às Compras Online",
            "Fazer compras online é como ter um shopping center no seu computador ou celular - conveniente, rápido e cheio de opções!\n\n" +
                    "Vantagens das compras online:\n" +
                    "• Comodidade: Compre sem sair de casa\n" +
                    "• Variedade: Milhares de produtos diferentes\n" +
                    "• Preços: Frequentemente mais baratos\n" +
                    "• Comparação: Veja preços em várias lojas\n" +
                    "• Entrega: Produtos chegam na sua casa\n\n" +
                    "Primeiros passos:\n" +
                    "1. Escolha lojas confiáveis\n" +
                    "2. Compare preços\n" +
                    "3. Verifique políticas de entrega\n" +
                    "4. Leia avaliações de produtos",
            "🛍️",
            "compras_introducao"
        ),
        Lição(
            "Encontrando Lojas Confiáveis",
            "Escolher lojas online seguras é como escolher um bom restaurante - precisa ter boa reputação e qualidade!\n\n" +
                    "Sinais de lojas confiáveis:\n" +
                    "• Site seguro (https:// e cadeado)\n" +
                    "• Informações de contato claras\n" +
                    "• Políticas de privacidade visíveis\n" +
                    "• Avaliações de clientes reais\n" +
                    "• CNPJ divulgado no site\n\n" +
                    "Lojas populares e seguras:\n" +
                    "• Amazon, Mercado Livre, Americanas\n" +
                    "• Magazine Luiza, Casas Bahia\n" +
                    "• Netshoes, Dafiti (roupas e calçados)\n" +
                    "• Submarino, Shoptime\n\n" +
                    "Dica: Comece com marcas conhecidas!",
            "🏪",
            "lojas_confiaveis"
        ),
        Lição(
            "Cadastro e Login em Lojas Online",
            "Criar uma conta em lojas online é como fazer uma ficha de cliente - rápido e necessário para comprar!\n\n" +
                    "Informações normalmente pedidas:\n" +
                    "• Nome completo\n" +
                    "• E-mail válido\n" +
                    "• CPF (para nota fiscal)\n" +
                    "• Telefone para contato\n" +
                    "• Endereço para entrega\n\n" +
                    "Dicas de segurança no cadastro:\n" +
                    "• Use senhas fortes e diferentes\n" +
                    "• Verifique seu e-mail após cadastro\n" +
                    "• Mantenha dados atualizados\n" +
                    "• Cuidado com informações pessoais demais",
            "👤",
            "cadastro_lojas"
        ),
        Lição(
            "Navegando e Buscando Produtos",
            "Encontrar produtos online é como caçar tesouros - use as ferramentas certas para achar o que precisa!\n\n" +
                    "Ferramentas de busca:\n" +
                    "• Barra de pesquisa: Digite o nome do produto\n" +
                    "• Filtros: Por preço, marca, avaliação\n" +
                    "• Categorias: Roupas, eletrônicos, casa\n" +
                    "• Ofertas: Produtos em promoção\n\n" +
                    "Como buscar eficientemente:\n" +
                    "1. Use palavras-chave específicas\n" +
                    "2. Filtre por faixa de preço\n" +
                    "3. Veja fotos e descrições\n" +
                    "4. Compare diferentes opções\n\n" +
                    "Exemplo: 'Tênis corrida feminino número 38'",
            "🔍",
            "busca_produtos"
        ),
        Lição(
            "Analisando Produtos e Avaliações",
            "Ler avaliações é como pedir opinião para amigos - ajuda a evitar compras ruins!\n\n" +
                    "O que observar nas avaliações:\n" +
                    "• Nota geral (estrelas)\n" +
                    "• Quantidade de avaliações\n" +
                    "• Comentários detalhados\n" +
                    "• Fotos reais de clientes\n" +
                    "• Respostas da loja\n\n" +
                    "Informações importantes do produto:\n" +
                    "• Descrição completa\n" +
                    "• Características técnicas\n" +
                    "• Dimensões e medidas\n" +
                    "• Materiais e composição\n" +
                    "• Garantia oferecida",
            "⭐",
            "avaliacoes_produtos"
        ),
        Lição(
            "Carrinho de Compras e Finalização",
            "O carrinho de compras é como sua sacola virtual - coloque tudo que quer e revise antes de pagar!\n\n" +
                    "Passos para finalizar compra:\n" +
                    "1. Adicione produtos ao carrinho\n" +
                    "2. Revise itens e quantidades\n" +
                    "3. Escolha opção de entrega\n" +
                    "4. Selecione forma de pagamento\n" +
                    "5. Confirme endereço de entrega\n" +
                    "6. Finalize a compra\n\n" +
                    "Itens para verificar no carrinho:\n" +
                    "• Preço total com frete\n" +
                    "• Prazos de entrega\n" +
                    "• Códigos de desconto\n" +
                    "• Itens com estoque disponível",
            "🛒",
            "carrinho_finalizacao"
        ),
        Lição(
            "Formas de Pagamento Seguras",
            "Escolher a forma de pagamento é como decidir como vai pagar no caixa - cada opção tem suas vantagens!\n\n" +
                    "Opções de pagamento comuns:\n" +
                    "• Cartão de crédito: Parcelamento\n" +
                    "• Cartão de débito: Pagamento à vista\n" +
                    "• Boleto bancário: Pague em qualquer banco\n" +
                    "• PIX: Pagamento instantâneo\n" +
                    "• PayPal: Serviço internacional\n\n" +
                    "Dicas de segurança no pagamento:\n" +
                    "• Verifique site seguro (https://)\n" +
                    "• Use cartão virtual quando possível\n" +
                    "• Não compartilhe senhas\n" +
                    "• Confirme dados antes de pagar",
            "💳",
            "pagamento_seguro"
        ),
        Lição(
            "Acompanhamento de Pedidos",
            "Acompanhar seu pedido é como seguir um pacote pelo GPS - você sabe onde está a cada momento!\n\n" +
                    "Status comuns de pedidos:\n" +
                    "• Processando pagamento\n" +
                    "• Pedido aprovado\n" +
                    "• Separando pedido\n" +
                    "• Pedido enviado\n" +
                    "• Em trânsito\n" +
                    "• Entregue\n\n" +
                    "Como acompanhar:\n" +
                    "1. Acesse 'Meus Pedidos' na loja\n" +
                    "2. Use código de rastreamento\n" +
                    "3. Receba notificações por e-mail\n" +
                    "4. Consulte site dos correios\n\n" +
                    "Prazo normal: 3 a 10 dias úteis",
            "📦",
            "acompanhamento_pedidos"
        ),
        Lição(
            "Trocas, Devoluções e Garantia",
            "Saber sobre trocas é como ter um seguro - traz tranquilidade caso algo dê errado!\n\n" +
                    "Direitos do consumidor online:\n" +
                    "• Arrependimento: 7 dias para devolver\n" +
                    "• Produto com defeito: 30 dias para troca\n" +
                    "• Garantia legal: 3 meses (pelo menos)\n" +
                    "• Descrição diferente: Direito à devolução\n\n" +
                    "Como proceder com problemas:\n" +
                    "1. Contate o SAC da loja\n" +
                    "2. Explique o problema claramente\n" +
                    "3. Tire fotos como evidência\n" +
                    "4. Siga instruções para envio\n" +
                    "5. Guarde comprovantes",
            "🔄",
            "trocas_devolucoes"
        ),
        Lição(
            "Compras Seguras e Prevenção a Golpes",
            "Comprar com segurança é como atravessar a rua - precisa olhar para os dois lados!\n\n" +
                    "Sinais de golpes online:\n" +
                    "• Preços absurdamente baixos\n" +
                    "• Sites sem informações de contato\n" +
                    "• Pagamento apenas por transferência\n" +
                    "• Pressão para compra imediata\n" +
                    "• URLs estranhas ou suspeitas\n\n" +
                    "Dicas de segurança:\n" +
                    "• Desconfie de ofertas milagrosas\n" +
                    "• Verifique reputação da loja\n" +
                    "• Use cartão com proteção\n" +
                    "• Mantenha antivírus atualizado\n" +
                    "• Não clique em links suspeitos",
            "🛡️",
            "compras_seguras"
        )
    )

    // LISTA DE LIÇÕES DO MÓDULO COMPRAS - ESPANHOL
    private val liçõesEspanol = listOf(
        Lição(
            "Introducción a las Compras Online",
            "¡Hacer compras online es como tener un centro comercial en tu computadora o celular - conveniente, rápido y lleno de opciones!\n\n" +
                    "Ventajas de las compras online:\n" +
                    "• Comodidad: Compra sin salir de casa\n" +
                    "• Variedad: Miles de productos diferentes\n" +
                    "• Precios: Frecuentemente más baratos\n" +
                    "• Comparación: Ve precios en varias tiendas\n" +
                    "• Entrega: Productos llegan a tu casa\n\n" +
                    "Primeros pasos:\n" +
                    "1. Elige tiendas confiables\n" +
                    "2. Compara precios\n" +
                    "3. Verifica políticas de entrega\n" +
                    "4. Lee evaluaciones de productos",
            "🛍️",
            "compras_introduccion"
        ),
        Lição(
            "Encontrando Tiendas Confiables",
            "¡Elegir tiendas online seguras es como elegir un buen restaurante - necesita tener buena reputación y calidad!\n\n" +
                    "Señales de tiendas confiables:\n" +
                    "• Sitio seguro (https:// y candado)\n" +
                    "• Informaciones de contacto claras\n" +
                    "• Políticas de privacidad visibles\n" +
                    "• Evaluaciones de clientes reales\n" +
                    "• CNPJ divulgado en el sitio\n\n" +
                    "Tiendas populares y seguras:\n" +
                    "• Amazon, Mercado Libre, Americanas\n" +
                    "• Magazine Luiza, Casas Bahia\n" +
                    "• Netshoes, Dafiti (ropa y calzado)\n" +
                    "• Submarino, Shoptime\n\n" +
                    "Consejo: ¡Empieza con marcas conocidas!",
            "🏪",
            "tiendas_confiables"
        ),
        Lição(
            "Registro y Login en Tiendas Online",
            "¡Crear una cuenta en tiendas online es como hacer una ficha de cliente - rápido y necesario para comprar!\n\n" +
                    "Informaciones normalmente solicitadas:\n" +
                    "• Nombre completo\n" +
                    "• E-mail válido\n" +
                    "• CPF (para nota fiscal)\n" +
                    "• Teléfono para contacto\n" +
                    "• Dirección para entrega\n\n" +
                    "Consejos de seguridad en el registro:\n" +
                    "• Usa contraseñas fuertes y diferentes\n" +
                    "• Verifica tu e-mail después del registro\n" +
                    "• Mantén datos actualizados\n" +
                    "• Cuidado con informaciones personales demais",
            "👤",
            "registro_tiendas"
        ),
        Lição(
            "Navegando y Buscando Productos",
            "¡Encontrar productos online es como cazar tesoros - usa las herramientas correctas para encontrar lo que necesitas!\n\n" +
                    "Herramientas de búsqueda:\n" +
                    "• Barra de búsqueda: Escribe el nombre del producto\n" +
                    "• Filtros: Por precio, marca, evaluación\n" +
                    "• Categorías: Ropa, electrónicos, casa\n" +
                    "• Ofertas: Productos en promoción\n\n" +
                    "Cómo buscar eficientemente:\n" +
                    "1. Usa palabras-clave específicas\n" +
                    "2. Filtra por rango de precio\n" +
                    "3. Ve fotos y descripciones\n" +
                    "4. Compara diferentes opciones\n\n" +
                    "Ejemplo: 'Zapatillas running femenino número 38'",
            "🔍",
            "busqueda_productos"
        ),
        Lição(
            "Analizando Productos y Evaluaciones",
            "¡Leer evaluaciones es como pedir opinión a amigos - ayuda a evitar compras malas!\n\n" +
                    "Qué observar en las evaluaciones:\n" +
                    "• Nota general (estrellas)\n" +
                    "• Cantidad de evaluaciones\n" +
                    "• Comentarios detallados\n" +
                    "• Fotos reales de clientes\n" +
                    "• Respuestas de la tienda\n\n" +
                    "Informaciones importantes del producto:\n" +
                    "• Descripción completa\n" +
                    "• Características técnicas\n" +
                    "• Dimensiones y medidas\n" +
                    "• Materiales y composición\n" +
                    "• Garantía ofrecida",
            "⭐",
            "evaluaciones_productos"
        ),
        Lição(
            "Carrito de Compras y Finalización",
            "¡El carrito de compras es como tu bolsa virtual - pon todo lo que quieres y revisa antes de pagar!\n\n" +
                    "Pasos para finalizar compra:\n" +
                    "1. Agrega productos al carrito\n" +
                    "2. Revisa ítems y cantidades\n" +
                    "3. Elige opción de entrega\n" +
                    "4. Selecciona forma de pago\n" +
                    "5. Confirma dirección de entrega\n" +
                    "6. Finaliza la compra\n\n" +
                    "Ítems para verificar en el carrito:\n" +
                    "• Precio total con envío\n" +
                    "• Plazos de entrega\n" +
                    "• Códigos de descuento\n" +
                    "• Ítems con stock disponible",
            "🛒",
            "carrito_finalizacion"
        ),
        Lição(
            "Formas de Pago Seguras",
            "¡Elegir la forma de pago es como decidir cómo vas a pagar en la caja - cada opción tiene sus ventajas!\n\n" +
                    "Opciones de pago comunes:\n" +
                    "• Tarjeta de crédito: Pago en cuotas\n" +
                    "• Tarjeta de débito: Pago al contado\n" +
                    "• Boleto bancario: Paga en cualquier banco\n" +
                    "• PIX: Pago instantáneo\n" +
                    "• PayPal: Servicio internacional\n\n" +
                    "Consejos de seguridad en el pago:\n" +
                    "• Verifica sitio seguro (https://)\n" +
                    "• Usa tarjeta virtual cuando sea posible\n" +
                    "• No compartas contraseñas\n" +
                    "• Confirma datos antes de pagar",
            "💳",
            "pago_seguro"
        ),
        Lição(
            "Seguimiento de Pedidos",
            "¡Seguir tu pedido es como seguir un paquete por GPS - sabes dónde está en cada momento!\n\n" +
                    "Estados comunes de pedidos:\n" +
                    "• Procesando pago\n" +
                    "• Pedido aprobado\n" +
                    "• Separando pedido\n" +
                    "• Pedido enviado\n" +
                    "• En tránsito\n" +
                    "• Entregado\n\n" +
                    "Cómo hacer seguimiento:\n" +
                    "1. Accede a 'Mis Pedidos' en la tienda\n" +
                    "2. Usa código de rastreo\n" +
                    "3. Recibe notificaciones por e-mail\n" +
                    "4. Consulta sitio de correos\n\n" +
                    "Plazo normal: 3 a 10 días hábiles",
            "📦",
            "seguimiento_pedidos"
        ),
        Lição(
            "Cambios, Devoluciones y Garantía",
            "¡Saber sobre cambios es como tener un seguro - trae tranquilidad si algo sale mal!\n\n" +
                    "Derechos del consumidor online:\n" +
                    "• Arrepentimiento: 7 días para devolver\n" +
                    "• Producto con defecto: 30 días para cambio\n" +
                    "• Garantía legal: 3 meses (mínimo)\n" +
                    "• Descripción diferente: Derecho a devolución\n\n" +
                    "Cómo proceder con problemas:\n" +
                    "1. Contacta el SAC de la tienda\n" +
                    "2. Explica el problema claramente\n" +
                    "3. Toma fotos como evidencia\n" +
                    "4. Sigue instrucciones para envío\n" +
                    "5. Guarda comprobantes",
            "🔄",
            "cambios_devoluciones"
        ),
        Lição(
            "Compras Seguras y Prevención de Estafas",
            "¡Comprar con seguridad es como cruzar la calle - necesitas mirar a ambos lados!\n\n" +
                    "Señales de estafas online:\n" +
                    "• Precios absurdamente bajos\n" +
                    "• Sitios sin informaciones de contacto\n" +
                    "• Pago solo por transferencia\n" +
                    "• Presión para compra inmediata\n" +
                    "• URLs extrañas o sospechosas\n\n" +
                    "Consejos de seguridad:\n" +
                    "• Desconfía de ofertas milagrosas\n" +
                    "• Verifica reputación de la tienda\n" +
                    "• Usa tarjeta con protección\n" +
                    "• Mantén antivirus actualizado\n" +
                    "• No hagas clic en enlaces sospechosos",
            "🛡️",
            "compras_seguras"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desktop) // Usa o mesmo layout do Desktop

        // Configurar idioma
        idioma = intent.getStringExtra("IDIOMA") ?: "pt"

        // Configurar título
        val txtTituloTela = findViewById<TextView>(R.id.txtTituloTela)
        txtTituloTela.text = if (idioma == "es") "Compras - Compras Inteligentes" else "Compras - Compras Inteligentes"

        // Inicializar componentes
        tts = TextToSpeech(this, this)
        configurarBotoes()
        configurarCliqueImagem()
        mostrarLicaoAtual()
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
                    "¡Felicitaciones! Completaste el módulo Compras! 🎉"
                } else {
                    "Parabéns! Você completou o módulo Compras! 🎉"
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

        // Botão Quiz - ABRE O QUIZ DO MÓDULO COMPRAS
        btnQuiz.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("IDIOMA", idioma)
            intent.putExtra("MODULO", "Compras") // ← IMPORTANTE: "Compras"
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
