package br.com.capisoft.arvores.models.DTOs;

import br.com.capisoft.arvores.models.Arvore;
import br.com.capisoft.arvores.models.Binario;
import br.com.capisoft.arvores.models.Node;
import br.com.capisoft.arvores.models.Palavra;
import br.com.capisoft.arvores.services.Tempo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GerarDTO {

    public static ArvoreDTO daArvore(Arvore arvore){
        return new ArvoreDTO(
                arvore.isAVL(),
                arvore.rotacoes,
                arvore.comparacoes,
                arvore.tempoDeExecucao,
                palavrasDaArvore(arvore),
                gerarDTO(arvore.getRoot())
        );
    }

    public static InformacoesDTO dasInformacoesTotais(Binario vetorBin, Arvore binaria, Arvore avl){
        return new InformacoesDTO(
                vetorBin.tempoLeituraArquivo,
                frequenciaDePalavras(vetorBin.getFrequenciaDePalavras()),
                doVetor(vetorBin),
                daArvoreBinaria(binaria),
                daArvoreAVL(avl)
        );
    }

    public static ArvoreBinariaDTO daArvoreBinaria(Arvore binaria){
        return new ArvoreBinariaDTO(
                binaria.comparacoes,
                gerarDTO(binaria.getRoot())
        );
    }

    public static ArvoreAVLDTO daArvoreAVL(Arvore avl){
        return new ArvoreAVLDTO(
                avl.comparacoes,
                avl.rotacoes,
                gerarDTO(avl.getRoot())
        );
    }

    public static VetorBinarioDTO doVetor(Binario vetorBinario){
        return new VetorBinarioDTO(
                Tempo.formatarTempoEmString(vetorBinario.tempoExecucaoBuscaBinaria),
                Tempo.formatarTempoEmString(vetorBinario.tempoOrdenacaoVetor),
                vetorBinario.contadorComparacoesBinaria
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
        return new NodeDTO(node.getPalavra(), esq, dir);
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

    private static List<PalavraDTO> frequenciaDePalavras(Map<String, Integer> mapsPalavras){
        List<PalavraDTO> list = new ArrayList<>();
        for(Map.Entry<String, Integer> item : mapsPalavras.entrySet()   ){
            list.add(new PalavraDTO(item.getKey(), item.getValue().intValue()));
        }
        return list;
    }
}
