package br.com.capisoft.arvores.models.DTOs;

public record BuscaResultadoDTO(
        NodeSimplesDTO node,
        String tempoBusca,

        int qtdePassos
) {
}
