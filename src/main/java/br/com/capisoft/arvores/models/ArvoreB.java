package br.com.capisoft.arvores.models;

public class ArvoreB {

    public NodeB raiz;

    public int ordem;

    public int qtdeElementos;

    public ArvoreB(int ordem){
        this.raiz = new NodeB(ordem - 1);
        this.ordem = ordem;
        qtdeElementos = 0;
    }

    public void inserirElemento(String palavra){
        NodeB no = buscarElemento(raiz, palavra);
        if (no == null){
            inserir(raiz, palavra);
        }
    }

    private ElementoNodeB inserir(NodeB node, String palavra){
        if (node == null){
            return null;
        }

        for (ElementoNodeB elem : node.vetorPalavras){
            int c = elem.palavra.compareToIgnoreCase(palavra);

            if (c < 0){
                if (!elem.temElementoAEsquerda()){

                }
            } else if (c > 0){

            } else {

            }
        }

        if (node.qtdeElementosPreenchidos < node.vetorPalavras.length-1){
            node.vetorPalavras[node.qtdeElementosPreenchidos +1].palavra = palavra;
            node.qtdeElementosPreenchidos++;
        }
    }

    public NodeB buscarElemento(NodeB node, String palavra){
        if (node == null || palavra == null){
            return null;
        }
        int posFilho = 0;
        for(ElementoNodeB elem : node.vetorPalavras){
            int i = elem.palavra.compareToIgnoreCase(palavra);

            if (elem.palavra.equalsIgnoreCase(palavra)){
                return node;
            } else if (i > 0){
                posFilho++;
            }
        }
        if (node.nodes[posFilho] != null){
            buscarElemento(node.nodes[posFilho], palavra);
        } else {
            return null;
        }
        return null;
    }

}
