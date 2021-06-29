import java.util.*
import kotlin.random.Random

class AlgoritimoGenetico(
    val taxaCrossover: Double = 0.0,
    val taxaMutacao: Double = 0.0,
    val maxGeracoes: Int = 0,
    val maxPopulacao: Int = 0){

    //Armazena a população
    var populacao: MutableList<Tabuleiro> = mutableListOf()

    //Gera a população inicial
    fun gerarPopulacao(){

        println("Gerando popuação inicial")

        //Gera a população máxima
        for(i in 0 until maxPopulacao){
            val colunasTabuleiro = mutableListOf<Int>()

            //Cria um loop para gerar as posições sem números repetidos de linha
            while(colunasTabuleiro.size < 8){
                var linha = Random.nextInt(1,9)
                //Adiciona caso não exista
                if(colunasTabuleiro.filterIndexed { _,i -> i == linha }.isNullOrEmpty()) colunasTabuleiro.add(linha)
            }

            //Adiciona na população
            populacao.add(Tabuleiro(colunasTabuleiro))
        }
    }

    //Realiza o cálculo de fitness
    fun fitness(){
        println("Calculando fitness")

        if(! populacao.isNullOrEmpty()) {

            //Percorre a população
            for(p in populacao) {
                //Armazena o número de conflitos da população atual
                var conflito = 0

                //Instância o tabuleiro em matriz
                val tabuleiro = p.getMatriz()

                //Percorre cada rainha e verifica o número de colisões individualmente, e soma no final
                for(coluna in 0 until p.positionLines?.size!!){
                    var colunaAtual = 0
                    var linhaAtual = 0

                    //--------------------------Checa a diagonal acima anterior--------------------------
                    colunaAtual = coluna
                    linhaAtual = p.positionLines[coluna] - 1

                    //Sobe uma linha
                    linhaAtual--
                    //Volta uma coluna
                    colunaAtual--
                    while(  ( linhaAtual >= 0 && linhaAtual < tabuleiro.size)
                            && (colunaAtual >= 0 && colunaAtual < tabuleiro[linhaAtual].size)
                    ){
                        if(tabuleiro[linhaAtual][colunaAtual] == 1){
                            conflito++
                        }

                        //Sobe uma linha
                        linhaAtual--
                        //Volta uma coluna
                        colunaAtual--
                    }
                    //----------------------------------------------------------------------------------

                    //--------------------------Checa a diagonal acima posterior--------------------------
                    colunaAtual = coluna
                    linhaAtual = p.positionLines[coluna] - 1

                    //Sobe uma linha
                    linhaAtual--
                    //Avança uma coluna
                    colunaAtual++
                    while(  ( linhaAtual >= 0 && linhaAtual < tabuleiro.size)
                        && (colunaAtual >= 0 && colunaAtual < tabuleiro[linhaAtual].size)
                    ){
                        if(tabuleiro[linhaAtual][colunaAtual] == 1){
                            conflito++
                        }

                        //Sobe uma linha
                        linhaAtual--
                        //Avança uma coluna
                        colunaAtual++
                    }
                    //----------------------------------------------------------------------------------

                    //--------------------------Checa a diagonal abaixo anterior--------------------------
                    colunaAtual = coluna
                    linhaAtual = p.positionLines[coluna] - 1

                    //Sobe uma linha
                    linhaAtual++
                    //Volta uma coluna
                    colunaAtual--
                    while(  ( linhaAtual >= 0 && linhaAtual < tabuleiro.size)
                        && (colunaAtual >= 0 && colunaAtual < tabuleiro[linhaAtual].size)
                    ){
                        if(tabuleiro[linhaAtual][colunaAtual] == 1){
                            conflito++
                        }

                        //Sobe uma linha
                        linhaAtual++
                        //Volta uma coluna
                        colunaAtual--
                    }
                    //----------------------------------------------------------------------------------

                    //--------------------------Checa a diagonal abaixo posterior--------------------------
                    colunaAtual = coluna
                    linhaAtual = p.positionLines[coluna] - 1

                    //Sobe uma linha
                    linhaAtual++
                    //Avança uma coluna
                    colunaAtual++
                    while(  ( linhaAtual >= 0 && linhaAtual < tabuleiro.size)
                        && (colunaAtual >= 0 && colunaAtual < tabuleiro[linhaAtual].size)
                    ){
                        if(tabuleiro[linhaAtual][colunaAtual] == 1){
                            conflito++
                        }

                        //Sobe uma linha
                        linhaAtual++
                        //Avança uma coluna
                        colunaAtual++
                    }
                    //----------------------------------------------------------------------------------
                }

                //Atualiza seu fitness
                p.fitness = conflito
            }
        }

        //ordena por fitness
        populacao.sortBy { t -> t.fitness }
    }

    //Realiza o processo de seleção por torneio
    fun selecionar(): Tabuleiro{
        println("Realizando torneio de seleção")

        //Resgata as posições random do torneio
        val pos1 = Random.nextInt(0, populacao.size)
        var pos2 = Random.nextInt(0, populacao.size)

        //Não aceita comparar o mesmo
        while(pos1 == pos2) pos2 = Random.nextInt(0, populacao.size)

        //Resgata a posição sorteada
        val t1 = populacao[pos1]
        val t2 = populacao[pos2]

        //Compara o fitness, resgatando o tabuleiro que possuí menos conflitos
        return if(t1.fitness < t2.fitness) t1 else t2
    }

    //Realiza o processo de cruzamento
    fun crossover(pai: Tabuleiro, mae: Tabuleiro): MutableList<Tabuleiro>{
        println("Verificando probabilidade de cruzamento")

        val filhos: MutableList<Tabuleiro>

        //Verifica a probabilidade de crossover
        if(Random.nextDouble(1.0,100.01) <= this.taxaCrossover) {
            println("Cruzamento realizada.")
            //Resgata a posição do ponto de corte
            val pontoCorte = Random.nextInt(2,6)

            //Efetua os cortes e junções e atribui a lista de novos filhos
            filhos = mutableListOf(unirCorte(pai,mae,pontoCorte),unirCorte(mae,pai,pontoCorte))
        }else{
            filhos  = mutableListOf(pai,mae)
        }

        return filhos
    }

    //Realiza o processo de mutação
    fun mutar(t: Tabuleiro): Tabuleiro{
        println("Verificando probabilidade de mutação")

        val mutacao: Tabuleiro

        if(Random.nextDouble(1.0,100.01) <= taxaMutacao) {
            println("Mutação realizada.")

            //Resgata as posições que serão trocadas
            val pos1 = Random.nextInt(0,7)
            var pos2 = Random.nextInt(0,7)

            //Enquanto as posições forem iguais, gera uma nova posição
            while(pos1 == pos2)  pos2 = Random.nextInt(0,7)

            //Efetua a troca
            Collections.swap(t.positionLines,pos1,pos2)

            //Atribui ao retorno da mutação
            mutacao = t
        }else{
            mutacao = t
        }

        return mutacao
    }

    //Uni os cortes de crossover e retorna o filho criado
    fun unirCorte(t1: Tabuleiro, t2: Tabuleiro, pontoCorte: Int): Tabuleiro{
        //Resgata os cortes
        val mFilho1Corte1 = t1.positionLines?.filterIndexed { index, _ ->  index <= pontoCorte}
                as MutableList<Int>
        val mFilho1Corte2 = t2.positionLines?.filterIndexed { index, _ ->  index > pontoCorte }
                as MutableList<Int>

        //instancia o tabuleiro do novo filho
        val filho = Tabuleiro()

        //Atribuí o valor do corte 1
        filho.positionLines?.addAll(mFilho1Corte1)

        //Atribuí o valor do corte 2, com valores não existentes
        for(e in mFilho1Corte2){
            val filter = filho.positionLines?.filterIndexed { _, i -> i == e  }
            //Se não existir o valor no filho atual, adiciona a posição do tabuleiro
            if(filter.isNullOrEmpty()) filho.positionLines?.add(e)
        }

        //Caso não haja 8 colunas no tabuleiro, deve sortear até encontrar uma posição válida
        while(filho.positionLines?.size != 8) {
            //Gera um numero random para a posição da linha
            Random.nextInt(1,9).let {
                val filter = filho.positionLines?.filterIndexed { _, i -> i == it  }
                if(filter.isNullOrEmpty()) filho.positionLines?.add(it)
            }
        }

        return filho
    }

    //Verifica se encontrou a rainha
    fun isEncontrouSolucao() = ! populacao.filterIndexed { _,i -> i.fitness == 0}.isNullOrEmpty()

    //Resgata a solução econtrada
    fun getSolucao(): Tabuleiro?{
        return populacao.filterIndexed { _,t -> t.fitness == 0}[0]
    }

}