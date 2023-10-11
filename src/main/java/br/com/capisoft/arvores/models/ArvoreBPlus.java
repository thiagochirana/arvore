package br.com.capisoft.arvores.models;

public class ArvoreBPlus {

    public NoBPlus raiz;
    private int t; // Grau mínimo

    public int passosContagemBusca;

    public String tempoDeExecucao;

    public int comparacoes;

    public ArvoreBPlus(int t) {
        this.raiz = new NoBPlus();
        this.t = t;
        this.passosContagemBusca = 0;
        this.comparacoes = 0;
    }

    public void inserir(String valor) {
        inserirNaArvoreBPlus(this.raiz, valor);
    }

    private void inserirNaArvoreBPlus(NoBPlus no, String valor) {
        comparacoes++;
        if (no.folha) {
            // Caso 1: Se o nó é uma folha, insira o valor.
            inserirNaFolha(no, valor);
        } else {
            // Encontre a posição correta no nó.
            int i = 0;
            while (i < no.numChaves && valor.compareTo(no.valores[i]) > 0) {
                i++;
            }

            // Insira na subárvore apropriada.
            inserirNaArvoreBPlus(no.filhos[i], valor);
        }
    }

    private void inserirNaFolha(NoBPlus folha, String valor) {
        try{
            int i = folha.numChaves - 1;
            while (i >= 0 && valor.compareTo(folha.valores[i]) < 0) {
                folha.valores[i + 1] = folha.valores[i];
                i--;
            }
            folha.valores[i + 1] = valor;
            folha.numChaves++;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public String buscar(String palavra) {
        return buscarNaArvoreBPlus(this.raiz, palavra);
    }

    private String buscarNaArvoreBPlus(NoBPlus no, String palavra) {
        passosContagemBusca++;
        int i = 0;
        while (i < no.numChaves && palavra.compareTo(no.valores[i]) > 0) {
            i++;
        }

        if (i < no.numChaves && palavra.equals(no.valores[i])) {
            // A palavra foi encontrada na folha.
            return no.valores[i];
        } else if (!no.folha) {
            // A palavra não está na folha, continue na subárvore apropriada.
            return buscarNaArvoreBPlus(no.filhos[i], palavra);
        }

        return null; // A palavra não está na árvore.
    }

    public void remover(String chave) {
        removerNaArvoreBPlus(this.raiz, chave);
        if (this.raiz.numChaves == 0) {
            // Se a raiz ficar sem chaves, atualize a raiz.
            this.raiz = this.raiz.filhos[0];
        }
    }

    private void removerNaArvoreBPlus(NoBPlus no, String chave) {
        try{
            int i = 0;
            while (i < no.numChaves && chave.compareTo(no.chaves[i]) > 0) {
                i++;
            }

            if (no.folha) {
                // Caso 1: Se o nó é uma folha, remova a chave e valor correspondente.
                if (i < no.numChaves && chave.equals(no.chaves[i])) {
                    removerDaFolha(no, i);
                }
            } else {
                // Continue na subárvore apropriada.
                boolean chaveEncontrada = (i < no.numChaves && chave.equals(no.chaves[i]));
                removerNaArvoreBPlus(no.filhos[i], chave);

                if (chaveEncontrada) {
                    // Atualize a chave com a mais à esquerda do nó filho.
                    no.chaves[i] = obterChaveMaisAEsquerda(no.filhos[i]);
                }
            }

            // Verifique se o nó atual ficou com menos chaves do que o mínimo permitido.
            if (no.numChaves < t - 1) {
                ajustarNoDeficitario(no, i);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void removerDaFolha(NoBPlus folha, int indice) {
        for (int i = indice + 1; i < folha.numChaves; i++) {
            folha.chaves[i - 1] = folha.chaves[i];
            folha.valores[i - 1] = folha.valores[i];
        }
        folha.numChaves--;
    }

    private String obterChaveMaisAEsquerda(NoBPlus no) {
        while (!no.folha) {
            no = no.filhos[0];
        }
        return no.chaves[0];
    }

    private void ajustarNoDeficitario(NoBPlus no, int indice) {
        if (indice > 0 && no.filhos[indice - 1].numChaves >= t) {
            redistribuirComVizinhoEsquerdo(no, indice);
        } else if (indice < no.numChaves && no.filhos[indice + 1].numChaves >= t) {
            redistribuirComVizinhoDireito(no, indice);
        } else if (indice > 0) {
            fusaoComVizinhoEsquerdo(no, indice);
        } else {
            fusaoComVizinhoDireito(no, indice);
        }
    }

    private void redistribuirComVizinhoEsquerdo(NoBPlus no, int indice) {
        NoBPlus filho = no.filhos[indice];
        NoBPlus vizinhoEsquerdo = no.filhos[indice - 1];

        for (int i = filho.numChaves - 1; i >= 0; i--) {
            filho.chaves[i + 1] = filho.chaves[i];
            filho.valores[i + 1] = filho.valores[i];
        }
        filho.chaves[0] = no.chaves[indice - 1];
        filho.valores[0] = vizinhoEsquerdo.valores[vizinhoEsquerdo.numChaves - 1];

        no.chaves[indice - 1] = vizinhoEsquerdo.chaves[vizinhoEsquerdo.numChaves - 1];

        vizinhoEsquerdo.numChaves--;
        filho.numChaves++;
    }

    private void redistribuirComVizinhoDireito(NoBPlus no, int indice) {
        NoBPlus filho = no.filhos[indice];
        NoBPlus vizinhoDireito = no.filhos[indice + 1];

        filho.chaves[filho.numChaves] = no.chaves[indice];
        filho.valores[filho.numChaves] = vizinhoDireito.valores[0];

        no.chaves[indice] = vizinhoDireito.chaves[0];

        for (int i = 0; i < vizinhoDireito.numChaves - 1; i++) {
            vizinhoDireito.chaves[i] = vizinhoDireito.chaves[i + 1];
            vizinhoDireito.valores[i] = vizinhoDireito.valores[i + 1];
        }

        filho.numChaves++;
        vizinhoDireito.numChaves--;
    }

    private void fusaoComVizinhoEsquerdo(NoBPlus no, int indice) {
        NoBPlus filho = no.filhos[indice];
        NoBPlus vizinhoEsquerdo = no.filhos[indice - 1];

        filho.chaves[filho.numChaves] = no.chaves[indice - 1];
        filho.valores[filho.numChaves] = vizinhoEsquerdo.valores[vizinhoEsquerdo.numChaves - 1];

        for (int i = 0; i < vizinhoEsquerdo.numChaves; i++) {
            filho.chaves[filho.numChaves + 1 + i] = vizinhoEsquerdo.chaves[i];
            filho.valores[filho.numChaves + 1 + i] = vizinhoEsquerdo.valores[i];
        }

        no.filhos[indice - 1] = no.filhos[indice];
        no.filhos[indice] = null;

        for (int i = indice; i < no.numChaves - 1; i++) {
            no.chaves[i - 1] = no.chaves[i];
            no.filhos[i] = no.filhos[i + 1];
        }

        no.numChaves--;
        filho.numChaves += vizinhoEsquerdo.numChaves + 1;
    }

    private void fusaoComVizinhoDireito(NoBPlus no, int indice) {
        NoBPlus filho = no.filhos[indice];
        NoBPlus vizinhoDireito = no.filhos[indice + 1];

        filho.chaves[filho.numChaves] = no.chaves[indice];
        filho.valores[filho.numChaves] = vizinhoDireito.valores[0];

        for (int i = 0; i < vizinhoDireito.numChaves; i++) {
            filho.chaves[filho.numChaves + 1 + i] = vizinhoDireito.chaves[i];
            filho.valores[filho.numChaves + 1 + i] = vizinhoDireito.valores[i];
        }

        no.filhos[indice + 1] = no.filhos[indice + 2];
        no.filhos[indice + 2] = null;

        for (int i = indice; i < no.numChaves - 1; i++) {
            no.chaves[i] = no.chaves[i + 1];
            no.filhos[i + 1] = no.filhos[i + 2];
        }

        no.numChaves--;
        filho.numChaves += vizinhoDireito.numChaves + 1;
    }

}
