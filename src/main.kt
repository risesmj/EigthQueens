fun main(){
    //Instância a classe do algorítimo
    val algoritimoGenetico = AlgoritimoGenetico(
        20.0,
        50.0,
        100000,
        10)

    //Gera a população inicial
    algoritimoGenetico.gerarPopulacao()

    //Calcula o fitness da popução inicial
    algoritimoGenetico.fitness()

    //Percorre o máximo de gerações configuradas
    for(i in 0 until algoritimoGenetico.maxGeracoes){
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
            val t = algoritimoGenetico.getSolucao()
            t?.print()
            println("Fitness: ${t?.fitness}")
            break
        }
    }

}


