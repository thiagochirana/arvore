package br.com.capisoft.arvores.models;

public class ArvoreB {

    public Pagina raiz;

    public int ordem;

    public int qtdeElementos;

    public ArvoreB(int ordem){
        this.raiz = new Pagina(ordem - 1);
        this.ordem = ordem;
        qtdeElementos = 0;
    }

    public void inserirElemento(String palavra){
        Pagina no = buscarElemento(raiz, palavra);
        if (no == null){
            inserir(raiz, palavra);
        }
    }

    private Elemento inserir(Pagina pagina, String palavra){
        if (pagina == null){
            return null;
        }

        for (Elemento elem : pagina.vetorElementos){
            int c = elem.palavra.compareToIgnoreCase(palavra);

            if (c < 0){

            } else if (c > 0){

            } else {

            }
        }

        if (pagina.qtdeElementosPreenchidos < pagina.vetorElementos.length-1){
            pagina.vetorElementos[pagina.qtdeElementosPreenchidos +1].palavra = palavra;
            pagina.qtdeElementosPreenchidos++;
        }
    }

    public void rebalancearPagina(Pagina pagina){
        if (pagina.paginaPai != null){
            if (pagina.contemFilhos()){
                if (pagina.necessitaDivisao()){
                    if (pagina.paginaPai.necessitaDivisao()){
                        rebalancearPagina(pagina.paginaPai);
                    } else {

                    }
                }
            }
        }
    }

    public Pagina buscarElemento(Pagina pagina, String palavra){
        if (pagina == null || palavra == null){
            return null;
        }
        int posFilho = 0;
        for(Elemento elem : pagina.vetorElementos){
            int i = elem.palavra.compareToIgnoreCase(palavra);

            if (elem.palavra.equalsIgnoreCase(palavra)){
                return pagina;
            } else if (i > 0){
                posFilho++;
            }
        }
        if (pagina.paginasFilho[posFilho] != null){
            buscarElemento(pagina.paginasFilho[posFilho], palavra);
        } else {
            return null;
        }
        return null;
    }

}
