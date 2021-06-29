fun main(vararg args: String){

    println("Informe a parâmetrização do algorítimo nesta ordem: ")
    print("Taxa Crossover / Taxa mutação / Máximo de gerações / Máximo da população: ")

    //Resgata os valores parâmetrizado
    var (taxaCrossover,taxaMutacao,maxGeracaoes,maxPopulacao) = readInput()

    //Valida o valor mínimo da população
    if(maxPopulacao.toInt() < 2){
        println("A população deve haver no mínimo dois indivíudos.")
        return
    }

    println("")

    //Instância a classe do algorítimo
    val algoritimoGenetico = AlgoritimoGenetico(
        taxaCrossover,
        taxaMutacao,
        maxGeracaoes.toInt(),
        maxPopulacao.toInt())

    var solucao: Tabuleiro? = null

    //Gera a população inicial
    algoritimoGenetico.gerarPopulacao()

    //Calcula o fitness da popução inicial
    algoritimoGenetico.fitness()

    //Percorre o máximo de gerações configuradas
    for(i in 0 until algoritimoGenetico.maxGeracoes){
        //Break line
        println("")

        println("------------------------ Geração ${i+1} ------------------------")

        //Array que contém a nova população que será gerada
        val mNovaPopulacao = mutableListOf<Tabuleiro>()

        //Gera a nova população de acordo com o tamanho de população configurada
        while(mNovaPopulacao.size < algoritimoGenetico.maxPopulacao) {

            //Seleciona o pai e mãe
            val pai = algoritimoGenetico.selecionar()
            val mae = algoritimoGenetico.selecionar()

            //Gera os filhos
            val mFilhos: MutableList<Tabuleiro> = algoritimoGenetico.crossover(pai,mae)

            //Muta os filhos
            val filho1: Tabuleiro = algoritimoGenetico.mutar(mFilhos[0])
            val filho2: Tabuleiro  = algoritimoGenetico.mutar(mFilhos[1])

            //Imprime os filhos
            filho1.print()
            filho2.print()

            //Adiciona na população
            mNovaPopulacao.add(filho1)
            mNovaPopulacao.add(filho2)
        }

        //Substitui a população
        algoritimoGenetico.populacao = mNovaPopulacao

        //Calcula o fitness da população
        algoritimoGenetico.fitness()

        //Verifica se encontrou a solução
        if(algoritimoGenetico.isEncontrouSolucao()) {
            solucao = algoritimoGenetico.getSolucao()

            println("")
            println("**********************************************************************")
            println("Solução encontrada na geração: ${i+1}")
            solucao?.print()
            println("Fitness: ${solucao?.fitness}")
            println("**********************************************************************")
            break
        }
    }

    //Verifica se não localizou a solução esperada
    if(solucao == null){
        if(algoritimoGenetico.populacao.isNotEmpty()) {
            algoritimoGenetico.populacao.sortBy { t -> t.fitness }

            //Resgata o primeiro registro e imprime seu tabuleiro
            solucao = algoritimoGenetico.populacao[0]
            println("")
            println("**********************************************************************")
            println("Não localizado a solução de fitness zero, segue abaixo a mais próxima: ")
            solucao?.print()
            println("Fitness: ${solucao?.fitness}")
            println("**********************************************************************")
        }else{
            println("Nenhuma solução encontrada =(")
        }
    }

}

//Interpreta os parâmetors de entrada
fun readInput() = readLine()!!.split(' ').map { it.toDouble() }


