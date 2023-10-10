package br.com.capisoft.arvores.models;

import java.util.Objects;

public class Elemento {

    public Pagina paginaResidente;

    public String palavra;

    public Elemento(Pagina paginaResidente, String palavra){
        this.paginaResidente = paginaResidente;
        this.palavra = palavra;
    }

    public boolean temElementoAEsquerda(){
        int cont = 0;
        for(Elemento e : paginaResidente.vetorElementos){
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
        for(int i = 0; i < paginaResidente.vetorElementos.length ; i++){
            if (paginaResidente.vetorElementos[i] != null && this.equals(paginaResidente.vetorElementos[i])){
                if (paginaResidente.vetorElementos[i+1] != null){
                    temDireita = true;
                    break;
                }
            }
        }
        return temDireita;
    }

    public Elemento obterIrmaoDireita(){
        for (int i = 0 ; i < paginaResidente.vetorElementos.length ; i++){
            if (paginaResidente.vetorElementos[i].equals(this)){
                if( i+1 >= paginaResidente.vetorElementos.length ){
                    return null;
                } else {
                    return paginaResidente.vetorElementos[i+1];
                }
            }
        }
        return null;
    }

    public boolean isPrimeiroDaPagina(){
        return paginaResidente.vetorElementos[0].equals(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Elemento that = (Elemento) o;
        return Objects.equals(paginaResidente, that.paginaResidente) && Objects.equals(palavra, that.palavra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paginaResidente, palavra);
    }
}
