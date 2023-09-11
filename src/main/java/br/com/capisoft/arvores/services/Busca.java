package br.com.capisoft.arvores.services;

import br.com.capisoft.arvores.models.Arvore;
import br.com.capisoft.arvores.models.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Busca {

    private static Logger LOG = LoggerFactory.getLogger(Busca.class);

    public Busca(){}

    public Node binariaDaArvore(Arvore arvore, String textoDoNodeBuscado){
        LOG.info("----------------------------------------------");
        LOG.info("INICIANDO BUSCA BINÁRIA NA "+arvore.toString());
        Node no = buscaBinaria(arvore.getRoot(), textoDoNodeBuscado);
        if (no == null) {
            LOG.info("BUSCA BINARIA | NÃO ENCONTRADO | Node \""+textoDoNodeBuscado.toUpperCase()+"\" NÃO encontrado na árvore.");
        } else {
            LOG.info("BUSCA BINARIA | ENCONTRADO | Node \""+textoDoNodeBuscado.toUpperCase()+"\" foi encontrado na árvore.");
        }
        return no;
    }

    public Node binariaNoNode(Node node, String textoDoNodeBuscado){
        LOG.info("VOU REALIZAR UMA BUSCA BINÁRIA NA ARVORE "+node.toString());
        Node no = buscaBinaria(node, textoDoNodeBuscado);
        if (no == null) {
            LOG.info("BUSCA BINARIA | NADA ENCONTRADO | Node \""+textoDoNodeBuscado.toUpperCase()+"\" NÃO encontrado a partir do Node \""+node.getTexto().toUpperCase()+"\"\n----------------------------------------------\n");
        } else {
            LOG.info("BUSCA BINARIA | NADA ENCONTRADO | Node \""+textoDoNodeBuscado.toUpperCase()+"\" foi encontrado a partir do Node \""+node.getTexto().toUpperCase()+"\"\n----------------------------------------------\n");
        }
        return no;
    }

    public int obterAlturaDoNode(Arvore arvore, Node node){
        int a = verificarAlturaBuscaBinaria(arvore.getRoot(),node);
        LOG.info("VERIFICACAO DE ALTURA | A altura atual do "+node+" é "+a);
        return a;
    }

    public int obterNiveis(Node node){
        int v = niveis(node);
        return v;
    }

    public Node buscarNodeDesbalanceado(Node root){
        if (root == null){
            return null;
        }
        int fator = obterNiveis(root.getNoDireito()) - obterNiveis(root.getNoEsquerdo());
        if (fator < -2 || fator > 2) {
            return root;
        } else {
            buscarNodeDesbalanceado(root.getNoEsquerdo());
            buscarNodeDesbalanceado(root.getNoDireito());
        }
        return null;
    }

    private int niveis(Node node){
        int esq = 0;
        int dir = 0;

        if (node == null) {
            return 0;
        }

        esq = niveis(node.getNoEsquerdo());
        dir = niveis(node.getNoDireito());

        return Math.max(esq, dir) + 1;
    }

    private Node buscaBinaria(Node node, String texto){
        LOG.info("BUSCA BINARIA | Buscando \""+texto.toUpperCase()+"\", a partir do Node \""+node.toString()+"\"");

        if (node.getTexto().equals(texto)){
            LOG.info("BUSCA BINARIA | NODE ENCONTRADO! | "+node);
            return node;
        }
        int res = node.getTexto().compareTo(texto);
        if (res == 0){
            LOG.info("BUSCA BINARIA | NODE ENCONTRADO! | "+node);
            return node;
        }
        if (res > 0){
            if (node.contemNoEsquerdo()){
                LOG.info("BUSCA BINARIA | <<< ESQUERDA <<< | verificando na esquerda do \""+node+"\"");
                return buscaBinaria(node.getNoEsquerdo(), texto);
            }
        } else {
            if (node.contemNoDireito()){
                LOG.info("BUSCA BINARIA | >>> DIREITA >>> | verificando na direita do \""+node+"\"");
                return buscaBinaria(node.getNoDireito(), texto);
            }
        }
        return null;
    }

    private int verificarAlturaBuscaBinaria(Node raiz, Node nodeBuscado){
        int cont = 0;
        if (nodeBuscado == null){
            return cont;
        }
        String buscado = nodeBuscado.getTexto();
        if (raiz.getTexto().equals(buscado)){
            return cont;
        }
        int res = raiz.getTexto().compareTo(buscado);
        if (res == 0){
            return cont;
        }
        if (res > 0){
            if (raiz.contemNoEsquerdo()){
                cont += verificarAlturaBuscaBinaria(raiz.getNoEsquerdo(), nodeBuscado) + 1;
            }
        } else {
            if (raiz.contemNoDireito()){
                cont += verificarAlturaBuscaBinaria(raiz.getNoDireito(), nodeBuscado) + 1;
            }
        }
        return cont;
    }
}
