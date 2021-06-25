class Tabuleiro(
    var positionLines: MutableList<Int> = mutableListOf(),
    var fitness: Int = 0){

    //Converte o objeto tabuleiro para matriz
    fun getMatriz(): MutableList<MutableList<Int>> {
        //Instância o tabuleiro em matriz
        val tabuleiro: MutableList<MutableList<Int>> = mutableListOf()

        //Cria uma matriz sem rainha
        for(i in 1..8){
            tabuleiro.add(mutableListOf(0,0,0,0,0,0,0,0))
        }

        //Adiciona as rainhas em suas devidas posições conforme o cromossomo da população
        for(coluna in 0 until positionLines?.size!!){
            val linha = positionLines[coluna] - 1
            tabuleiro[linha][coluna] = 1
        }

        return tabuleiro
    }

    //Imprime o tabuleiro no console por objeto tabuleiro
    fun print(){
        println("")
        //Converre para matriz
        val t  = getMatriz()

        //Desenha o tabuleiro
        for(i in 0 until t.size){
            for(j in 0 until t[i].size){
                print(t[i][j].toString() + "|")
            }
            println("")
        }

        println("")
    }
}
