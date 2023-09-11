package br.com.capisoft.arvores.services;

import br.com.capisoft.arvores.models.Arvore;
import br.com.capisoft.arvores.models.DTOs.GerarDTO;
import br.com.capisoft.arvores.models.Node;
import br.com.capisoft.arvores.repositories.ArvoreRepository;
import br.com.capisoft.arvores.repositories.NodeRepository;
import br.com.capisoft.arvores.utils.Dados;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ArvoresService {

    private static Logger LOG = LoggerFactory.getLogger(ArvoresService.class);

    private Dados dados = new Dados();

    private ControleArvores arvoreControl;

    @Autowired
    private ArvoreRepository arvores;

    @Autowired
    private NodeRepository nodes;

    public ResponseEntity arquivoLeituraTeste(MultipartFile arquivo) throws IOException {
        dados.adicionarTextoTeste(arquivo);
        return ResponseEntity.ok("lido com sucesso");
    }

    public ResponseEntity obterTXTMontarArvoreSimples(MultipartFile arquivo) throws IOException {
        for (String palavraNode : dados.carregarListaDePalavras(arquivo)){
            adicionarNaArvore(palavraNode,false);
        }
        Arvore arv;
        if (arvoreControl.getArvore().getId() != null){
            arv = updateArvore(arvoreControl.getArvore());
        } else {
            arv = salvarArvore(arvoreControl.getArvore());
        }
        return ResponseEntity.ok(GerarDTO.daArvore(arv));
    }

    public ResponseEntity obterTXTEMontarArvoreAVL(MultipartFile arquivo) throws IOException {
        for (String palavraNode : dados.carregarListaDePalavras(arquivo)){
            adicionarNaArvore(palavraNode,true);
        }
        Arvore arv;
        if (arvoreControl.getArvore().getId() != null){
            arv = updateArvore(arvoreControl.getArvore());
        } else {
            arv = salvarArvore(arvoreControl.getArvore());
        }
        return ResponseEntity.ok(GerarDTO.daArvore(arv));
    }

    public ResponseEntity adicionarNodeArvoreSimples(String texto){
        Arvore arv = adicionarNaArvore(texto,false);
        return ResponseEntity.ok(salvarArvore(arv));
    }

    public ResponseEntity adicionarNodeArvoreAVL(String texto){
        Arvore arv = adicionarNaArvore(texto, true);
        return ResponseEntity.ok(salvarArvore(arv));
    }

    public ResponseEntity buscarNode(String textoDoNode){
        Node no = Busca.binariaDaArvore(arvoreControl.getArvore(),textoDoNode);
        if (no != null){
            return ResponseEntity.ok(no.getDTO());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Arvore adicionarNaArvore(String textoNode, boolean isAVL){
        Node novoNode = new Node(textoNode);
        Arvore arvore = new Arvore(novoNode, isAVL);
        if (arvoreControl == null){
            LOG.info("Arvore está vazia, vou iniciar ela com o root -> "+novoNode+ (isAVL ? ", tipo será AVL." : " sem balanceamento, tipo Simples binária."));
            arvoreControl = new ControleArvores(arvore);
        } else {
            arvoreControl.adicionarNaArvore(novoNode);
        }
        return arvoreControl.getArvore();
    }

    private Arvore salvarArvore(Arvore arvore){
        arvore.setRoot(salvarNodes(arvore.getRoot()));
        return arvores.save(arvore);
    }

    private Arvore updateArvore(Arvore arvore){
        Arvore find = arvores.findById(arvore.getId()).get();
        return arvores.save(find);
    }

    private Node salvarNodes(Node no){
        if (no.contemNoEsquerdo()){
            salvarNodes(no.getNoEsquerdo());
        }
        if (no.contemNoDireito()){
            salvarNodes(no.getNoDireito());
        }
        return nodes.save(no);
    }
}
