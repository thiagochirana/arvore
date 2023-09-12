package br.com.capisoft.arvores.services;

import br.com.capisoft.arvores.models.Arvore;
import br.com.capisoft.arvores.models.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControleArvores {

    private static Logger LOG = LoggerFactory.getLogger(ControleArvores.class);
    private Busca busca;

    private Arvore arvore;

    public ControleArvores(Arvore arvore){
        this.arvore = arvore;
        this.arvore.adicionarNaListaDePalavras(arvore.getRoot().getPalavra());
        this.busca = new Busca();
    }

    public Arvore getArvore(){
        return this.arvore;
    }

    public void adicionarNaArvore(Node node){
        if (!arvore.contemRaiz()){
            LOG.info("Arvore está vazia, vou adicionar nova arvore com o root ->"+node.toString());
            arvore.setRoot(node);
        } else {
            //Aqui realiza a busca na árvore se tal node já está presente na árvore, se sim apenas acrescenta na contagem de palavras, se não então insere na árvore
            LOG.info("Vou iniciar uma Busca Binária na "+arvore.toString()+" e verificar se contém o Node "+node.getPalavra().toUpperCase());
            Node no = busca.binariaDaArvore(arvore,node.getPalavra());
            if(no != null){
                LOG.info("Node com texto "+node.getPalavra().toUpperCase()+" foi encontrado na árvore, logo, não irei preencher, apenas adicionar a contagem de palavras");
            } else {
                LOG.info("Vou adicionar o node pois foi verificado que o mesmo não foi encontrado na árvore -> "+node.toString());
                arvore.setRoot(inserirNo(arvore.getRoot(), node.getPalavra().toLowerCase()));
            }
            arvore.adicionarNaListaDePalavras(node.getPalavra());
        }
    }

    private Node inserirNo(Node node, String palavra){
        if (node == null) {
            return new Node(palavra);
        } else{
            int res = node.getPalavra()
                    .trim()
                    .toLowerCase()
                    .compareTo(palavra.trim().toLowerCase());

            if (res > 0) {
                node.direita = inserirNo(node.esquerda, palavra);
            } else if (res < 0) {
                node.direita = inserirNo(node.direita, palavra);
            } else {
                LOG.info("O novo node "+palavra+" já está na arvore, vou adicionar a contagem de palavras");
                arvore.adicionarNaListaDePalavras(palavra);
            }
        }
        if (arvore.isAVL()){
            return rebalance(node);
        } else {
            return node;
        }
    }

    /**
     * ARVORES AVL E FUNCOES DE REBALANCEAMENTO A PARTIR DAQUI
     */

    private Node rebalance(Node node) {
        updateAltura(node);
        int balance = getBalanceamento(node);
        if (balance > 1) {
            if (altura(node.direita.direita) > altura(node.direita.esquerda)) {
                node = rotacaoEsquerda(node);
            } else {
                node.direita = rotacaoDireita(node.direita);
                node = rotacaoEsquerda(node);
            }
        } else if (balance < -1) {
            if (altura(node.esquerda.esquerda) > altura(node.esquerda.direita)) {
                node = rotacaoDireita(node);
            } else {
                node.esquerda = rotacaoEsquerda(node.esquerda);
                node = rotacaoDireita(node);
            }
        }
        return node;
    }

    //Rotacoes

    private Node rotacaoDireita(Node y) {
        Node x = y.esquerda;
        Node z = x.direita;
        x.direita = y;
        y.esquerda = z;
        updateAltura(y);
        updateAltura(x);
        return x;
    }

    private Node rotacaoEsquerda(Node y) {
        Node x = y.direita;
        Node z = x.esquerda;
        x.esquerda = y;
        y.direita = z;
        updateAltura(y);
        updateAltura(x);
        return x;
    }

    private void updateAltura(Node n) {
        n.setAltura(1 + Math.max(altura(n.esquerda), altura(n.direita)));
    }

    public int getBalanceamento(Node n) {
        return (n == null) ? 0 : altura(n.getNoDireito()) - altura(n.getNoEsquerdo());
    }

    private int altura(Node node){
        return node == null ? -1 : node.altura;
    }
}
