package br.com.capisoft.arvores.models.DTOs;

import br.com.capisoft.arvores.models.Palavra;

import java.util.List;

public record ArvoreDTO(
        boolean isAVL,
        int rotacoes,
        int comparacoes,
        String tempoDeExecucao,
        List<PalavraDTO> palavras,

        NodeDTO raiz
) {
}
