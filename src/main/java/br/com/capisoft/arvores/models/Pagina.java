package br.com.capisoft.arvores.models;

public class Pagina {

    public Elemento[] vetorElementos;

    public Pagina[] paginasFilho;

    public Pagina paginaPai;

    public boolean isFolha;

    public int qtdeElementosPreenchidos;

    public Pagina(int qtdeTotalElemento){
        this.vetorElementos = new Elemento[qtdeTotalElemento];
        this.paginasFilho = new Pagina[qtdeTotalElemento+1];
        this.isFolha = true;
        this.qtdeElementosPreenchidos = 0;
    }

    public Pagina(int qtdeTotalElemento, boolean isFolha){
        this(qtdeTotalElemento);
        this.isFolha = isFolha;
    }

    public Pagina(int qtdeTotalElemento, boolean isFolha, Pagina paginaPai){
        this(qtdeTotalElemento,isFolha);
        this.paginaPai = paginaPai;
    }

    public boolean necessitaDivisao(){
        int cont = 0;
        for (Elemento el : vetorElementos){
            cont++;
        }
        return cont > vetorElementos.length / 2;
    }

    public boolean contemFilhos(){
        int cont = 0;
        for (Pagina p : paginasFilho){
            cont++;
        }
        return cont > 0;
    }

}
