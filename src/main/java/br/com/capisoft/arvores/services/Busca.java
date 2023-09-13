package br.com.capisoft.arvores.services;

import br.com.capisoft.arvores.models.Arvore;
import br.com.capisoft.arvores.models.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Busca {

    private static Logger LOG = LoggerFactory.getLogger(Busca.class);

    private int pass = 0;

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

    public int getPassosDados(){
        int aux = this.pass;
        this.pass = 0;
        return aux;
    }

    private Node buscaBinaria(Node node, String texto){
        pass++;
        LOG.info("BUSCA BINARIA | Buscando \""+texto.toUpperCase()+"\", a partir do Node \""+node.toString()+"\"");

        if (node.getPalavra().equals(texto.toLowerCase())){
            LOG.info("BUSCA BINARIA | NODE ENCONTRADO! | "+node);
            return node;
        }
        int res = node.getPalavra().compareTo(texto);
        if (res == 0){
            LOG.info("BUSCA BINARIA | NODE ENCONTRADO! | "+node);
            return node;
        }
        if (res > 0){
            if (node.contemNoEsquerdo()){
                LOG.info("BUSCA BINARIA | <<< ESQUERDA <<< | verificando na esquerda do \""+node+"\"");
                return buscaBinaria(node.getNoEsquerdo(), texto.toLowerCase());
            }
        } else {
            if (node.contemNoDireito()){
                LOG.info("BUSCA BINARIA | >>> DIREITA >>> | verificando na direita do \""+node+"\"");
                return buscaBinaria(node.getNoDireito(), texto.toLowerCase());
            }
        }
        return null;
    }

}
