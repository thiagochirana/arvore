package br.com.capisoft.arvores.models.DTOs;

public record ArvoreAVLDTO(
        int comparacoes,
        int rotacoes,
        NodeDTO raizArvoreAVL
) {
}
