package br.com.capisoft.arvores.services;

import br.com.capisoft.arvores.models.Arvore;
import br.com.capisoft.arvores.models.DTOs.BuscaResultadoDTO;
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
import java.util.Optional;

@Service
public class ArvoresService {

    private static Logger LOG = LoggerFactory.getLogger(ArvoresService.class);

    private Dados dados = new Dados();
    private Tempo tempo = new Tempo();
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
        long start = System.nanoTime();
        for (String palavraNode : dados.carregarListaDePalavras(arquivo)){
            adicionarNaArvore(palavraNode.toLowerCase(),false);
        }
        long stop = System.nanoTime();
        Arvore arv = arvoreSimplesControl.getArvore();
        arv.tempoDeExecucao = tempo.formatarTempo(stop - start);
        return ResponseEntity.ok(GerarDTO.daArvore(salvarArvore(arv)));
    }

    public ResponseEntity obterTXTEMontarArvoreAVL(MultipartFile arquivo) throws IOException {
        long start = System.nanoTime();
        for (String palavraNode : dados.carregarListaDePalavras(arquivo)){
            adicionarNaArvore(palavraNode.toLowerCase(),true);
        }
        long stop = System.nanoTime();
        Arvore arv = arvoreAVLControl.getArvore();
        arv.tempoDeExecucao = tempo.formatarTempo(stop - start);
        return ResponseEntity.ok(GerarDTO.daArvore(salvarArvore(arv)));
    }

    public ResponseEntity adicionarNodeArvoreSimples(String texto){
        long start = System.nanoTime();
        if (arvoreSimplesControl == null){
            return ResponseEntity.badRequest().body("Sem arvore simples para inserir. Crie uma realizando um Upload de arquivo");
        }
        Arvore arv = adicionarNaArvore(texto,false);
        long stop = System.nanoTime();
        arv.tempoDeExecucao = tempo.formatarTempo(stop - start);
        return ResponseEntity.ok(GerarDTO.daArvore(salvarArvore(arv)));
    }

    public ResponseEntity adicionarNodeArvoreAVL(String texto){
        long start = System.nanoTime();
        if (arvoreAVLControl == null){
            return ResponseEntity.badRequest().body("Sem arvore AVL para inserir. Crie uma realizando um Upload de arquivo");
        }
        Arvore arv = adicionarNaArvore(texto, true);
        long stop = System.nanoTime();
        arv.tempoDeExecucao = tempo.formatarTempo(stop - start);
        return ResponseEntity.ok(GerarDTO.daArvore(salvarArvore(arv)));
    }

    public ResponseEntity buscarNode(String textoDoNode, boolean arvoreIsAVL){
        Node no;
        long start = System.nanoTime();
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
        long stop = System.nanoTime();
        if (no != null){
            return ResponseEntity.ok( new BuscaResultadoDTO(
                    no.getDTO(),
                    tempo.formatarTempo(stop - start),
                    busca.getPassosDados()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity buscarArvores(Long id){
        Optional<Arvore> find = arvores.findById(id);
        if (find.isPresent()){
            return ResponseEntity.ok(GerarDTO.daArvore(find.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity deletarArvores(){
        arvoreAVLControl = null;
        arvoreSimplesControl = null;
        arvores.deleteAll();
        nodes.deleteAll();
        LOG.info("Deletado todos os nodes e árvores salvos");
        return ResponseEntity.ok("Todas as arvores foram excluídas");
    }


    // ----- METODOS PRIVADOS -----
    private Arvore adicionarNaArvore(String textoNode, boolean isAVL){
        Node novoNode = new Node(textoNode);
        if (isAVL){
            if (arvoreAVLControl == null){
                LOG.info("Arvore está vazia, vou iniciar ela com o root -> "+novoNode+ ", tipo será AVL.");
                arvoreAVLControl = new ControleArvores(new Arvore(novoNode, true));
            } else {
                arvoreAVLControl.adicionarNaArvore(novoNode);
            }
            return arvoreAVLControl.getArvore();
        } else {
            if (arvoreSimplesControl == null){
                LOG.info("Arvore está vazia, vou iniciar ela com o root "+novoNode+ " sem balanceamento, tipo Simples binária.");
                arvoreSimplesControl = new ControleArvores(new Arvore(novoNode, false));
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
