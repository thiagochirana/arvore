package br.com.capisoft.arvores.models.DTOs;

public record ArvoreAVLDTO(
        int comparacoes,
        int rotacoes,
        String tempoDeExecucao,
        NodeDTO raizArvoreAVL
) {
}
