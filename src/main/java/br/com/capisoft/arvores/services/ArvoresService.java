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

    private Busca busca = new Busca();

    private ControleArvores arvoreSimplesControl;

    private ControleArvores arvoreAVLControl;

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
        if (arvoreSimplesControl.getArvore().getId() != null){
            arv = updateArvore(arvoreSimplesControl.getArvore());
        } else {
            arv = salvarArvore(arvoreSimplesControl.getArvore());
        }
        return ResponseEntity.ok(GerarDTO.daArvore(arv));
    }

    public ResponseEntity obterTXTEMontarArvoreAVL(MultipartFile arquivo) throws IOException {
        for (String palavraNode : dados.carregarListaDePalavras(arquivo)){
            adicionarNaArvore(palavraNode,true);
        }
        Arvore arv;
        if (arvoreAVLControl.getArvore().getId() != null){
            arv = updateArvore(arvoreAVLControl.getArvore());
        } else {
            arv = salvarArvore(arvoreAVLControl.getArvore());
        }
        return ResponseEntity.ok(GerarDTO.daArvore(arv));
    }

    public ResponseEntity adicionarNodeArvoreSimples(String texto){
        Arvore arv = adicionarNaArvore(texto,false);
        return ResponseEntity.ok(GerarDTO.daArvore(salvarArvore(arv)));
    }

    public ResponseEntity adicionarNodeArvoreAVL(String texto){
        Arvore arv = adicionarNaArvore(texto, true);
        return ResponseEntity.ok(GerarDTO.daArvore(salvarArvore(arv)));
    }

    public ResponseEntity buscarNode(String textoDoNode, boolean arvoreIsAVL){
        Node no;
        if (arvoreIsAVL){
            if(arvoreAVLControl == null){
                return ResponseEntity.badRequest().body("Sem arvore AVL para pesquisar. Crie uma e tente novamente");
            }
            no = busca.binariaDaArvore(arvoreAVLControl.getArvore(),textoDoNode);
        } else {
            if(arvoreSimplesControl == null){
                return ResponseEntity.badRequest().body("Sem arvore simples para pesquisar. Crie uma e tente novamente");
            }
            no = busca.binariaDaArvore(arvoreSimplesControl.getArvore(),textoDoNode);
        }
        if (no != null){
            return ResponseEntity.ok(no.getDTO());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private Arvore adicionarNaArvore(String textoNode, boolean isAVL){
        Node novoNode = new Node(textoNode);
        Arvore arvore = new Arvore(novoNode, isAVL);
        if (isAVL){
            if (arvoreAVLControl == null){
                LOG.info("Arvore está vazia, vou iniciar ela com o root -> "+novoNode+ (isAVL ? ", tipo será AVL." : " sem balanceamento, tipo Simples binária."));
                arvoreAVLControl = new ControleArvores(arvore);
            } else {
                arvoreAVLControl.adicionarNaArvore(novoNode);
            }
            return arvoreAVLControl.getArvore();
        } else {
            if (arvoreSimplesControl == null){
                LOG.info("Arvore está vazia, vou iniciar ela com o root -> "+novoNode+ (isAVL ? ", tipo será AVL." : " sem balanceamento, tipo Simples binária."));
                arvoreSimplesControl = new ControleArvores(arvore);
            } else {
                arvoreSimplesControl.adicionarNaArvore(novoNode);
            }
            return arvoreSimplesControl.getArvore();
        }
    }

    private Arvore salvarArvore(Arvore arvore){
        LOG.info("SAVING ARVORE | Salvar nova arvore...");
        arvore.setRoot(salvarNodes(arvore.getRoot()));
        Arvore arv = arvores.save(arvore);
        LOG.info("SAVE | Realizado com sucesso!");
        return arv;
    }

    private Arvore updateArvore(Arvore arvore){
        LOG.info("UPDATING ARVORE| Realizando update da arvore...");
        Arvore find = arvores.findById(arvore.getId()).get();
        Arvore arv = arvores.save(find);
        LOG.info("UPDATE | Realizado com sucesso!");
        return arv;
    }

    private Node salvarNodes(Node no){
        if (no.contemNoEsquerdo()){
            salvarNodes(no.getNoEsquerdo());
        }
        if (no.contemNoDireito()){
            salvarNodes(no.getNoDireito());
        }
        LOG.info("SALVING NODE | Salvando...");
        Node no1 = nodes.save(no);
        LOG.info("SALVO | "+no1+" salvo com sucesso.");
        return no1;
    }
}
