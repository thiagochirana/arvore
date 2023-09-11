package br.com.capisoft.arvores.models.DTOs;

import br.com.capisoft.arvores.models.Arvore;
import br.com.capisoft.arvores.models.Node;
import br.com.capisoft.arvores.models.Palavra;

import java.util.ArrayList;
import java.util.List;

public class GerarDTO {

    public static ArvoreDTO daArvore(Arvore arvore){
        return new ArvoreDTO(
                arvore.isAVL(),
                palavrasDaArvore(arvore),
                gerarDTO(arvore.getRoot())
        );
    }

    private static NodeDTO gerarDTO(Node node){
        NodeDTO dir = null;
        NodeDTO esq = null;
        if (node.contemNoEsquerdo()){
            esq = gerarDTO(node.getNoEsquerdo());
        }
        if (node.contemNoDireito()){
            dir = gerarDTO(node.getNoDireito());
        }
        return new NodeDTO(node.getTexto(), esq, dir);
    }

    private static List<PalavraDTO> palavrasDaArvore(Arvore arvore){
        List<PalavraDTO> lista = new ArrayList<>();
        for (Palavra p : arvore.getListaDePalavras()){
            lista.add(new PalavraDTO(
                    p.getPalavra(),
                    p.getQuantidade()
            ));
        }
        return lista;
    }
}
