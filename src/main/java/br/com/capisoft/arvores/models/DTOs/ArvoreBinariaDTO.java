package br.com.capisoft.arvores.models.DTOs;

public record ArvoreBinariaDTO(
        int comparacoes,
        String tempoDeExecucao,
        NodeDTO raizArvoreBinaria
) {
}
