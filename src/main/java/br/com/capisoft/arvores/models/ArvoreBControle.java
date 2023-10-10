package br.com.capisoft.arvores.models;

import br.com.capisoft.arvores.exceptions.ElementoException;

public class ArvoreBControle {

    private ArvoreB arvoreBControle;

    public ArvoreBControle(ArvoreB arvoreB){
        this.arvoreBControle = arvoreB;
    }

    public void inserir(Elemento elemento){

    }

    public Elemento buscarElemento(String palavra) throws ElementoException {
        if(palavra == null){
            throw new ElementoException("Elemento "+palavra+" não encontrado na Arvore B");
        } else {
            busca(arvoreBControle.raiz,palavra);
        }
    }

    private Elemento busca(Pagina pagina, String palavra){
        if (pagina == null){

        }
    }

    public ArvoreB getArvoreBControle() {
        return arvoreBControle;
    }
}
