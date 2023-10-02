package br.com.capisoft.arvores.models;

public class NodeB {

    public ElementoNodeB[] vetorPalavras;

    public NodeB[] nodes;

    public boolean isFolha;

    public int qtdeElementosPreenchidos;

    public NodeB(int qtdeTotalElemento){
        this.vetorPalavras = new ElementoNodeB[qtdeTotalElemento];
        this.nodes = new NodeB[qtdeTotalElemento+1];
        this.isFolha = true;
        this.qtdeElementosPreenchidos = 0;
    }

}
