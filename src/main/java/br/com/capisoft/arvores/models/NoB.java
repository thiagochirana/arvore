package br.com.capisoft.arvores.models;

import java.util.ArrayList;

public class NoB {
    ArrayList<String> chaves;
    ArrayList<NoB> filhos;
    boolean folha;

    NoB() {
        this.chaves = new ArrayList<>();
        this.filhos = new ArrayList<>();
        this.folha = true;
    }
}
