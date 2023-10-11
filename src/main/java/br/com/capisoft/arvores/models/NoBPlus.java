package br.com.capisoft.arvores.models;

public class NoBPlus {
    String[] chaves;
    String[] valores;
    NoBPlus pai;
    NoBPlus[] filhos;
    boolean folha;
    int numChaves;

    NoBPlus() {
        this.chaves = new String[4]; // Ajuste o tamanho conforme necessário
        this.valores = new String[4]; // Ajuste o tamanho conforme necessário
        this.pai = null;
        this.filhos = new NoBPlus[5]; // Ajuste o tamanho conforme necessário
        this.folha = true;
        this.numChaves = 0;
    }
}
