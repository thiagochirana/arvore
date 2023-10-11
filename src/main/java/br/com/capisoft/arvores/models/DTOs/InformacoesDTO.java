package br.com.capisoft.arvores.models.DTOs;

import java.util.List;

public record InformacoesDTO(
        String tempoLeituraArquivo,
        List<PalavraDTO> frequenciaPalavras,

        VetorBinarioDTO vetorBinario,

        ArvoreBinariaDTO arvoreBinaria,

        ArvoreAVLDTO arvoreAVL,

        ArvoreBDTO arvoreB,

        ArvoreBPlusDTO arvoreBPlus

) {
}
