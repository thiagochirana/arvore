package br.com.capisoft.arvores.models;

public class ArvoreB {

    public Pagina raiz;

    public boolean isBMais;

    public int ordem;

    public int qtdeElementos;

    public ArvoreB(int ordem){
        this.raiz = new Pagina(ordem - 1);
        this.ordem = ordem;
        this.isBMais = false;
        qtdeElementos = 0;
    }

    public void inserirElemento(String palavra){

    }

    private Elemento inserir(Pagina pagina, String palavra){
        for(Elemento el : pagina.vetorElementos){
            if (el == null){

            }
        }
        //TODO consertar essa bullshit aqui
        return new Elemento(pagina,palavra);
    }

    private void inserirOrganizar(Pagina pagina, Elemento elemento, int posicao){
        if (posicao > pagina.vetorElementos.length){
            return;
        }
        if (pagina.vetorElementos[posicao] != null){
            for (int i = posicao ; i < pagina.vetorElementos.length ; i++){
                Elemento atual = pagina.vetorElementos[i];
                Elemento nextAtual = pagina.vetorElementos[i].obterIrmaoDireita();

                pagina.vetorElementos[i] = elemento;
                pagina.vetorElementos[i+1] = atual;
                if (i+2 >= pagina.vetorElementos.length){
                    pagina.vetorElementos[i+2] = nextAtual;
                }
            }
        }

    }

    private void removerElemento(Elemento elemento){
        if (elemento.paginaResidente == null){
            return;
        }

        for (int i = 0 ; i < elemento.paginaResidente.vetorElementos.length ; i++){
            if (elemento.paginaResidente.vetorElementos[i].equals(elemento)){
                elemento.paginaResidente.vetorElementos[i] = null;
                break;
            }
        }
        for (int i = 0 ; i < elemento.paginaResidente.vetorElementos.length ; i++){
            if (elemento.paginaResidente.vetorElementos[i] == null){
                Elemento next = elemento.paginaResidente.vetorElementos[i].obterIrmaoDireita();
                Elemento atual = elemento.paginaResidente.vetorElementos[i];
                if (!(elemento.paginaResidente.vetorElementos.length >= i+1)){
                    elemento.paginaResidente.vetorElementos[i+1] = atual;
                }
                elemento.paginaResidente.vetorElementos[i] = next;
            }
        }
    }

}
