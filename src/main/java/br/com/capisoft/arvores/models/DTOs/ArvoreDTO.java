package br.com.capisoft.arvores.models.DTOs;

import br.com.capisoft.arvores.models.Palavra;

import java.util.List;

public record ArvoreDTO(
        boolean isAVL,
        List<PalavraDTO> palavras,

        NodeDTO raiz
) {
}
