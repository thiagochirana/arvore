package br.com.capisoft.arvores.models;

import java.util.Objects;

public class ElementoNodeB {

    public NodeB paiNode;

    public String palavra;

    public ElementoNodeB(NodeB paiNode, String palavra){
        this.paiNode = paiNode;
        this.palavra = palavra;
    }

    public boolean temElementoAEsquerda(){
        int cont = 0;
        for(ElementoNodeB e : paiNode.vetorPalavras){
            if (this.equals(e)){
                break;
            } else {
                cont++;
            }
        }
        if (cont > 0){
            return true;
        } else {
            return false;
        }
    }

    public boolean temElementoADireita(){
        boolean temDireita = false;
        for(int i = 0; i < paiNode.vetorPalavras.length ; i++){
            if (paiNode.vetorPalavras[i] != null && this.equals(paiNode.vetorPalavras[i])){
                if (paiNode.vetorPalavras[i+1] != null){
                    temDireita = true;
                    break;
                }
            }
        }
        return temDireita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementoNodeB that = (ElementoNodeB) o;
        return Objects.equals(paiNode, that.paiNode) && Objects.equals(palavra, that.palavra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paiNode, palavra);
    }
}
