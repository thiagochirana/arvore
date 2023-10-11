package br.com.capisoft.arvores.models;

public class ArvoreB {

    public NoB raiz;
    private int t; // Grau mínimo

    public int passosContagemBusca;

    public int comparacoes;

    public String tempoDeExecucao;

    public ArvoreB(int t) {
        this.raiz = new NoB();
        this.t = t;
        this.passosContagemBusca = 0;
        this.comparacoes = 0;
    }

    public boolean buscar(String chave) {
        return buscarNaArvoreB(this.raiz, chave);
    }

    private boolean buscarNaArvoreB(NoB no, String chave) {
        passosContagemBusca++;
        int i = 0;
        while (i < no.chaves.size() && chave.compareTo(no.chaves.get(i)) > 0) {
            i++;
        }

        if (i < no.chaves.size() && chave.equals(no.chaves.get(i))) {
            return true; // A chave foi encontrada
        } else if (no.folha) {
            return false; // A chave não está na árvore
        } else {
            // Recurso na subárvore apropriada
            return buscarNaArvoreB(no.filhos.get(i), chave);
        }
    }

    public void inserir(String chave) {
        NoB raiz = this.raiz;
        if (raiz.chaves.size() == (2 * t) - 1) {
            // A raiz está cheia, então a árvore precisa crescer.
            NoB novoNo = new NoB();
            novoNo.filhos.add(this.raiz);
            dividirNo(novoNo, 0);
            this.raiz = novoNo;
        }
        inserirNaArvoreB(raiz, chave);
    }

    private void inserirNaArvoreB(NoB no, String chave) {
        int i = no.chaves.size() - 1;
        comparacoes++;
        if (no.folha) {
            // Neste caso, simplesmente insira a chave no nó folha.
            no.chaves.add(chave);
        } else {
            // Encontre a posição correta no nó.
            while (i >= 0 && chave.compareTo(no.chaves.get(i)) < 0) {
                i--;
            }
            i++;

            // Verifique se o filho está cheio.
            if (no.filhos.get(i).chaves.size() == (2 * t) - 1) {
                dividirNo(no, i);
                if (chave.compareTo(no.chaves.get(i)) > 0) {
                    i++;
                }
            }
            inserirNaArvoreB(no.filhos.get(i), chave);
        }
    }

    private void dividirNo(NoB no, int i) {
        try{
            NoB novoNo = new NoB();
            NoB noExistente = no.filhos.get(i);
            no.filhos.add(i + 1, novoNo);
            no.chaves.add(i, noExistente.chaves.remove(t - 1));
            novoNo.folha = noExistente.folha;

            for (int j = 0; j < t - 1; j++) {
                novoNo.chaves.add(noExistente.chaves.remove(t));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void remover(String chave) {
        if (!buscar(chave)) {
            System.out.println("Chave não encontrada na árvore.");
            return;
        }
        removerNaArvoreB(this.raiz, chave);
        if (this.raiz.chaves.size() == 0 && !this.raiz.folha) {
            // Se a raiz estiver vazia após a remoção, faça a raiz apontar para o novo nó raiz.
            this.raiz = this.raiz.filhos.get(0);
        }
    }

    private void removerNaArvoreB(NoB no, String chave) {
        int i = 0;
        while (i < no.chaves.size() && chave.compareTo(no.chaves.get(i)) > 0) {
            i++;
        }

        if (i < no.chaves.size() && chave.equals(no.chaves.get(i))) {
            if (no.folha) {
                no.chaves.remove(i);
            } else {
                String chaveSubstituta = obterChaveSubstituta(no, i);
                removerNaArvoreB(no.filhos.get(i), chaveSubstituta);
                no.chaves.set(i, chaveSubstituta);
            }
        } else {
            removerNaArvoreB(no.filhos.get(i), chave);
        }

        if (no.chaves.size() < t - 1) {
            ajustarNoDeficitario(no, i);
        }
    }

    private String obterChaveSubstituta(NoB no, int indice) {
        NoB noAnterior = no.filhos.get(indice);
        while (!noAnterior.folha) {
            noAnterior = noAnterior.filhos.get(noAnterior.filhos.size() - 1);
        }
        return noAnterior.chaves.get(noAnterior.chaves.size() - 1);
    }

    private void ajustarNoDeficitario(NoB no, int indice) {
        if (indice > 0 && no.filhos.get(indice - 1).chaves.size() >= t) {
            redistribuirComVizinhoEsquerdo(no, indice);
        } else if (indice < no.chaves.size() && no.filhos.get(indice + 1).chaves.size() >= t) {
            redistribuirComVizinhoDireito(no, indice);
        } else if (indice > 0) {
            fusaoComVizinhoEsquerdo(no, indice);
        } else {
            fusaoComVizinhoDireito(no, indice);
        }
    }

    private void redistribuirComVizinhoEsquerdo(NoB no, int indice) {
        NoB filho = no.filhos.get(indice);
        NoB vizinhoEsquerdo = no.filhos.get(indice - 1);
        String chavePai = no.chaves.get(indice - 1);

        filho.chaves.add(0, chavePai);
        no.chaves.set(indice - 1, vizinhoEsquerdo.chaves.get(vizinhoEsquerdo.chaves.size() - 1));
        vizinhoEsquerdo.chaves.remove(vizinhoEsquerdo.chaves.size() - 1);

        if (!filho.folha) {
            filho.filhos.add(0, vizinhoEsquerdo.filhos.get(vizinhoEsquerdo.filhos.size() - 1));
            vizinhoEsquerdo.filhos.remove(vizinhoEsquerdo.filhos.size() - 1);
        }
    }

    private void redistribuirComVizinhoDireito(NoB no, int indice) {
        NoB filho = no.filhos.get(indice);
        NoB vizinhoDireito = no.filhos.get(indice + 1);
        String chavePai = no.chaves.get(indice);

        filho.chaves.add(chavePai);
        no.chaves.set(indice, vizinhoDireito.chaves.get(0));
        vizinhoDireito.chaves.remove(0);

        if (!filho.folha) {
            filho.filhos.add(vizinhoDireito.filhos.get(0));
            vizinhoDireito.filhos.remove(0);
        }
    }

    private void fusaoComVizinhoEsquerdo(NoB no, int indice) {
        NoB filho = no.filhos.get(indice);
        NoB vizinhoEsquerdo = no.filhos.get(indice - 1);
        String chavePai = no.chaves.remove(indice - 1);

        vizinhoEsquerdo.chaves.add(chavePai);
        vizinhoEsquerdo.chaves.addAll(filho.chaves);

        if (!filho.folha) {
            vizinhoEsquerdo.filhos.addAll(filho.filhos);
        }

        no.filhos.remove(indice);
    }

    private void fusaoComVizinhoDireito(NoB no, int indice) {
        NoB filho = no.filhos.get(indice);
        NoB vizinhoDireito = no.filhos.get(indice + 1);
        String chavePai = no.chaves.remove(indice);

        filho.chaves.add(chavePai);
        filho.chaves.addAll(vizinhoDireito.chaves);

        if (!filho.folha) {
            filho.filhos.addAll(vizinhoDireito.filhos);
        }

        no.filhos.remove(indice + 1);
    }

}
