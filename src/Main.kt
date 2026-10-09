// 1. Classes de Domínio (POO e Tipos de Dados)

class Morador(
    val nome: String,
    val telefone: String
)

class TaxaCondominio(
    val descricao: String,
    val valor: Double
)

class Unidade(
    val bloco: String,
    val numero: Int,
    val morador: Morador,
    val statusCodigo: Int, // 1: Adimplente, 2: Inadimplente, 3: Em Acordo Judicial
    val observacaoEncomenda: String? = null // Null Safety
) {

    // Função para avaliar o status da unidade usando 'when'
    fun obterStatusDescricao(): String {
        return when (statusCodigo) {
            1 -> "Adimplente"
            2 -> "Inadimplente"
            3 -> "Em Acordo Judicial"
            else -> "Status Desconhecido"
        }
    }

    // Função para verificar encomenda utilizando o operador Elvis (?:)
    fun obterObservacaoEncomenda(): String {
        return observacaoEncomenda ?: "Nenhuma encomenda pendente"
    }

    // Função para calcular o total do boleto usando 'for' e aplicando multa com 'if/else'
    fun calcularBoletoTotal(taxas: List<TaxaCondominio>, atrasado: Boolean): Double {
        var subtotal = 0.0

        // Laço 'for' para somar todas as taxas e despesas mensais
        for (taxa in taxas) {
            subtotal += taxa.valor
        }

        // Condicional 'if/else' para aplicação de multa por atraso (2%)
        val valorFinal = if (atrasado) {
            val multa = subtotal * 0.02
            subtotal + multa
        } else {
            subtotal
        }

        return valorFinal
    }
}

// 2. Execução Principal (Demonstração do Sistema)

fun main() {
    // Instanciando Morador e Unidade
    val morador1 = Morador(nome = "Carlos Andrade", telefone = "(92) 99123-4567")

    // Teste 1: Morador com encomenda pendente e pagamento em dia
    val unidade101 = Unidade(
        bloco = "A",
        numero = 101,
        morador = morador1,
        statusCodigo = 1,
        observacaoEncomenda = "Caixa media na portaria - Mercado Livre"
    )

    // Lista de taxas do mês
    val taxasMes = listOf(
        TaxaCondominio("Taxa de Condomínio Padrão", 450.00),
        TaxaCondominio("Fundo de Reserva", 45.00),
        TaxaCondominio("Manutenção do Elevador", 30.00)
    )

    println("=== SISTEMA DE GESTÃO DE CONDOMÍNIO ===")
    println("Unidade: Bloco ${unidade101.bloco} - Apto ${unidade101.numero}")
    println("Morador: ${unidade101.morador.nome} | Tel: ${unidade101.morador.telefone}")
    println("Status da Unidade: ${unidade101.obterStatusDescricao()}")
    println("Encomendas: ${unidade101.obterObservacaoEncomenda()}")
    println("----------------------------------------")

    // Cálculo do Boleto (Sem Atraso)
    val totalEmDia = unidade101.calcularBoletoTotal(taxasMes, atrasado = false)
    println("Total do Boleto (Pago no prazo): R$ ${"%.2f".format(totalEmDia)}")

    // Cálculo do Boleto (Com Atraso - Aplicação de Multa)
    val totalComAtraso = unidade101.calcularBoletoTotal(taxasMes, atrasado = true)
    println("Total do Boleto (Com atraso +2% multa): R$ ${"%.2f".format(totalComAtraso)}")

    println("\n----------------------------------------")

    // Teste 2: Morador sem encomenda (String? = null) e Inadimplente
    val morador2 = Morador(nome = "Ana Souza", telefone = "(92) 98877-6655")
    val unidade202 = Unidade(
        bloco = "B",
        numero = 202,
        morador = morador2,
        statusCodigo = 2,
        observacaoEncomenda = null // Forçando valor nulo para testar o operador Elvis
    )

    println("Unidade: Bloco ${unidade202.bloco} - Apto ${unidade202.numero}")
    println("Morador: ${unidade202.morador.nome}")
    println("Status da Unidade: ${unidade202.obterStatusDescricao()}")
    println("Encomendas: ${unidade202.obterObservacaoEncomenda()}") // Retornará a mensagem padrão
}