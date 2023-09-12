package br.com.capisoft.arvores.controller;

import br.com.capisoft.arvores.services.ArvoresService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/arvore")
public class ArvoreController {

    private static Logger LOG = LoggerFactory.getLogger(ArvoreController.class);

    @Autowired
    private ArvoresService arvoresService;

    @GetMapping("/buscaPalavra={palavra}&arvoreIsAVL={isAVL}")
    public ResponseEntity uploadArquivoTeste(@PathVariable String palavra, @PathVariable boolean isAVL) {
        return arvoresService.buscarNode(palavra,isAVL);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarArvore(@PathVariable Long id){
        return arvoresService.buscarArvores(id);
    }

    @PostMapping("/simples/uploadTXT")
    @Transactional
    public ResponseEntity uploadArquivo(MultipartFile txt) throws IOException {
        LOG.info("Request recebida para popular arvore Simples");
        return arvoresService.obterTXTMontarArvoreSimples(txt);
    }

    @PostMapping("/avl/uploadTXT")
    @Transactional
    public ResponseEntity uploadArquivoArvoreAVL(MultipartFile txt) throws IOException{
        LOG.info("Request recebida para popular arvore AVL");
        return arvoresService.obterTXTEMontarArvoreAVL(txt);
    }

    @PostMapping("/simples/adicionar={palavra}")
    @Transactional
    public ResponseEntity adicionarNodeSimples(@PathVariable String palavra){
        return arvoresService.adicionarNodeArvoreSimples(palavra.toLowerCase());
    }

    @PostMapping("/avl/adicionar={palavra}")
    @Transactional
    public ResponseEntity adicionarNodeAVL(@PathVariable String palavra){
        return arvoresService.adicionarNodeArvoreAVL(palavra.toLowerCase());
    }
}
