package br.com.capisoft.arvores.controller;

import br.com.capisoft.arvores.services.ArvoresService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/arvore")
@CrossOrigin(origins = "http://localhost:63342")
public class ArvoreController {

    private static Logger LOG = LoggerFactory.getLogger(ArvoreController.class);

    @Autowired
    private ArvoresService arvoresService;

    @GetMapping("/buscaPalavra")
    @ResponseBody
    public ResponseEntity uploadArquivoTeste(@RequestParam("palavra") String palavra, @RequestParam("arvoreIsAVL") boolean isAVL) {
        return arvoresService.buscarNode(palavra,isAVL);
    }

    @GetMapping("/buscarArvore")
    public ResponseEntity buscarArvore(@RequestParam("id") Long id){
        return arvoresService.buscarArvores(id);
    }

    @GetMapping("/buscarInfo")
    public ResponseEntity buscarInformacoesAtuais(){
        return arvoresService.obterInformacoes();
    }

    @PostMapping("/uploadTXT")
    @Transactional
    public ResponseEntity uploadArquivoTXT(MultipartFile txt) throws IOException {
        LOG.info("Request recebida em arquivo");
        return arvoresService.processarArquivoParaArvores(txt);
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

    @PostMapping("/simples/adicionar")
    @Transactional
    public ResponseEntity adicionarNodeSimples(@RequestParam("palavra") String palavra){
        return arvoresService.adicionarNodeArvoreSimples(palavra.toLowerCase());
    }

    @PostMapping("/avl/adicionar")
    @Transactional
    public ResponseEntity adicionarNodeAVL(@RequestParam("palavra") String palavra){
        return arvoresService.adicionarNodeArvoreAVL(palavra.toLowerCase());
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity removerTodasAsArvores(){
        return arvoresService.deletarArvores();
    }
}
