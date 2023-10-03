package br.com.capisoft.arvores.models;

public class Pagina {

    public Elemento[] vetorElementos;

    public Pagina[] paginasFilho;

    public boolean isFolha;

    public int qtdeElementosPreenchidos;

    public Pagina(int qtdeTotalElemento){
        this.vetorElementos = new Elemento[qtdeTotalElemento];
        this.paginasFilho = new Pagina[qtdeTotalElemento+1];
        this.isFolha = true;
        this.qtdeElementosPreenchidos = 0;
    }

}
