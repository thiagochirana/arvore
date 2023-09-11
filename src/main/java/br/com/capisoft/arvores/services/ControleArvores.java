package br.com.capisoft.arvores.services;

import br.com.capisoft.arvores.models.Arvore;
import br.com.capisoft.arvores.models.DirecaoNode;
import br.com.capisoft.arvores.models.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControleArvores {

    private static Logger LOG = LoggerFactory.getLogger(ControleArvores.class);
    private Busca busca;

    private Arvore arvore;

    public ControleArvores(Arvore arvore){
        this.arvore = arvore;
        this.arvore.adicionarNaListaDePalavras(arvore.getRoot().getTexto());
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
            LOG.info("Vou iniciar uma Busca Binária na "+arvore.toString()+" e verificar se contém o Node "+node.getTexto().toUpperCase());
            Node no = busca.binariaDaArvore(arvore,node.getTexto());
            if(no != null){
                LOG.info("Node com texto "+node.getTexto().toUpperCase()+" foi encontrado na árvore, logo, não irei preencher, apenas adicionar a contagem de palavras");
                arvore.adicionarNaListaDePalavras(node.getTexto());
            } else {
                LOG.info("Vou adicionar o node pois foi verificado que o mesmo não foi encontrado na árvore -> "+node.toString());
                preencherArvore(arvore.getRoot(), node);
            }
        }
    }

    //Adicionando na Arvore
    private Node preencherArvore(Node raiz, Node novoNode){
        String raizPalavra = raiz.getTexto().toUpperCase();
        String nodePalavra = novoNode.getTexto().toUpperCase();

        if (raiz == null){
            raiz = novoNode;
            LOG.info("Raiz "+raiz+" vazia, vou adicionar o node "+novoNode.getTexto().toUpperCase());
            Node n1 = adicionarNode(raiz, novoNode, DirecaoNode.NODE_ATUAL);
            if (arvore.isAVL()) verificarBalanceamentoArvore();
            return n1;
        }
        int res = raiz.getTexto()
                .trim()
                .toLowerCase()
                .compareTo(novoNode.getTexto().trim().toLowerCase());
        if (res == 0){
            LOG.info("O novo node "+nodePalavra+" já está na arvore, vou adicionar a contagem de palavras");
            arvore.adicionarNaListaDePalavras(nodePalavra);
        }
        if (res > 0){
            LOG.info("Node central "+raizPalavra+" é maior que o node "+ nodePalavra+", vou inserir node "+nodePalavra+" na Esquerda do "+raizPalavra);
            if (raiz.contemNoEsquerdo()){
                novoNode.adicionarNivel();
                return preencherArvore(raiz.getNoEsquerdo(),novoNode);
            } else {
                Node n1 = adicionarNode(raiz,novoNode,DirecaoNode.ESQUERDA);
                if (arvore.isAVL()) verificarBalanceamentoArvore();
                return n1;
            }
        } else {
            LOG.info("Node central "+raizPalavra+" é menor que o node "+ nodePalavra+", vou inserir node "+nodePalavra+" na Direita do "+raizPalavra);
            if (raiz.contemNoDireito()) {
                novoNode.adicionarNivel();
                return preencherArvore(raiz.getNoDireito(),novoNode);
            } else {
                Node n1 = adicionarNode(raiz,novoNode,DirecaoNode.DIREITA);
                if (arvore.isAVL()) verificarBalanceamentoArvore();
                return n1;
            }
        }
    }

    private Node adicionarNode(Node raiz, Node novoNode, DirecaoNode direcao){
        if(direcao == DirecaoNode.NODE_ATUAL){
            LOG.info("O node "+novoNode.getTexto().toUpperCase()+" foi adicionado na árvore");
            raiz = novoNode;
        }
        if (direcao == DirecaoNode.ESQUERDA){
            LOG.info("O node "+novoNode.getTexto().toUpperCase()+" adicionado ESQUERDA do node "+raiz.getTexto().toUpperCase());
            raiz.adicionarNaEsquerda(novoNode);
        } else {
            LOG.info("O node "+novoNode.getTexto().toUpperCase()+" adicionado DIREITA do node "+raiz.getTexto().toUpperCase());
            raiz.adicionarNaDireita(novoNode);
        }
        novoNode.adicionarNivel();
        arvore.adicionarNaListaDePalavras(novoNode.getTexto());
        return novoNode;
    }

    public void verificarBalanceamentoArvore(){
        LOG.info("Irei verificar se árvore está desbalanceada, e se sim, rebalancear.");
        rebalance(arvore.getRoot());
    }

    /**
     * ARVORES AVL E FUNCOES DE REBALANCEAMENTO A PARTIR DAQUI
     */

    //Rotacoes
    private Node rotacaoSimplesDireita(Node node){
        LOG.info("ROTACAO DIREITA | iniciada no node "+node.toString());
        Node n2 = node.getNoEsquerdo();

        if (n2.contemNoEsquerdo()){
            n2.setNodeDireito(node);
            n2.setNodeEsquerdo(null);
        } else {
            Node nAux = n2;
            n2 = n2.getNoDireito();
            n2.setNodeEsquerdo(nAux);
            n2.setNodeDireito(node);
        }
        LOG.info("ROTACAO DIREITA | Agora o "+n2+" está no centro e terá na esquerda o "+n2.getNoEsquerdo()+" e na direita "+n2.getNoDireito());
        return n2;
    }

    private Node rotacaoSimplesEsquerda(Node node){
        LOG.info("ROTACAO ESQUERDA | iniciada no node "+node.toString());
        Node n2 = node.getNoDireito();

        if (n2.contemNoDireito()){
            n2.setNodeEsquerdo(node);
            node.setNodeDireito(null);
        } else if (n2.contemNoEsquerdo()) {
            Node nAux = n2;
            n2 = n2.getNoEsquerdo();
            n2.setNodeDireito(nAux);
            n2.setNodeEsquerdo(node);
        }
        LOG.info("ROTACAO ESQUERDA | Agora o "+n2+" está no centro e terá na esquerda o "+n2.getNoEsquerdo()+" e na direita "+n2.getNoDireito());
        return n2;
    }

    private void rebalance(Node node){
        Node noDesbalanceado = busca.buscarNodeDesbalanceado(node);
        int balanc = obterBalanceamento(noDesbalanceado);
        Node noBalanceado;
        if (balanc < -2){
            noBalanceado = rotacaoSimplesDireita(noDesbalanceado);
        } else {
            noBalanceado = rotacaoSimplesEsquerda(noDesbalanceado);
        }
        noDesbalanceado = noBalanceado;
    }

    private int obterBalanceamento(Node node){
        return (node == null) ? 0: altura(node.getNoDireito()) - altura(node.getNoEsquerdo());
    }

    private int altura(Node node){
        return (node == null) ? 0 : busca.obterAlturaDoNode(arvore,node);
    }
}
