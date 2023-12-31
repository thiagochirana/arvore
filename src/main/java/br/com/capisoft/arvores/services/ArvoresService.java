package br.com.capisoft.arvores.services;

import br.com.capisoft.arvores.models.*;
import br.com.capisoft.arvores.models.DTOs.BuscaResultadoDTO;
import br.com.capisoft.arvores.models.DTOs.GerarDTO;
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
import java.util.Random;

@Service
public class ArvoresService{

    private static Logger LOG = LoggerFactory.getLogger(ArvoresService.class);

    private static MultipartFile arquivo;

    private Dados dados = new Dados();
    private Tempo tempo = new Tempo();
    private Busca busca = new Busca();

    private static boolean terminouProcessAVL = false;

    private static boolean terminouProcessBinario = false;

    private static Binario vetorBinario = new Binario();

    private static ControleArvores arvoreSimplesControl;

    private static ControleArvores arvoreAVLControl;

    private static ArvoreB arvoreB;

    private static ArvoreBPlus arvoreBPlus;

    @Autowired
    private ArvoreRepository arvores;

    @Autowired
    private NodeRepository nodes;

    public ResponseEntity processarArquivoParaArvores(MultipartFile arquivo) throws IOException {

        this.arquivo = arquivo;

        try {
            new Thread(gerarVetorAndArvores).start();
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e);
        }
        return ResponseEntity.ok("Carregamento realizado com sucesso");
    }

    public ResponseEntity obterInformacoes(){
        LOG.info("Obtendo informaçoes gerais de carregamento e enviar ao cliente");
        if (terminouProcessAVL && terminouProcessBinario){
            return ResponseEntity.ok(
                    GerarDTO.dasInformacoesTotais(vetorBinario,
                    arvoreSimplesControl.getArvore(),
                    arvoreAVLControl.getArvore(),
                    arvoreB,
                    arvoreBPlus
                    )
            );
        } else {
            return ResponseEntity.status(102).body("Ainda em processamento dos dados, por favor aguarde");
        }
    }


    private static Runnable gerarVetorAndArvores = new Runnable() {
        @Override
        public void run() {
            try {
                ExecutarArvores exec = new ExecutarArvores(arquivo,vetorBinario);
                exec.executarCarregamentoVetorBinario();
                vetorBinario = exec.vetorBinario;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            new Thread(adicionarEmArvoreAVL).start();
            new Thread(adicionarEmArvoreSimples).start();
            new Thread(adicionarEmArvoreB).start();
            new Thread(adicionarEmArvoreBPlus).start();
            arvoreSimplesControl.getArvore().comparacoes = arvoreAVLControl.getArvore().comparacoes = vetorBinario.contadorComparacoesBinaria;
        }
    };

    private static Runnable adicionarEmArvoreAVL = new Runnable() {
        @Override
        public void run() {
            long start = System.nanoTime();
            for (String palavraNode : vetorBinario.vetorTratado){
                adicionarNaArvore(palavraNode.toLowerCase(),true);
            }
            long stop = System.nanoTime();
            arvoreAVLControl.getArvore().tempoDeExecucao = Tempo.formatarTempoEmString(stop - start);
            terminouProcessAVL = true;
        }
    };

    private static Runnable adicionarEmArvoreSimples = new Runnable() {
        @Override
        public void run() {
            long start = System.nanoTime();
            for (String palavraNode : vetorBinario.vetorTratado){
                adicionarNaArvore(palavraNode.toLowerCase(),false);
            }
            long stop = System.nanoTime();
            arvoreSimplesControl.getArvore().tempoDeExecucao = Tempo.formatarTempoEmString(stop - start);
            terminouProcessBinario = true;
        }
    };

    private static Runnable adicionarEmArvoreB = new Runnable() {
        @Override
        public void run() {
            long start = System.nanoTime();
            arvoreB = new ArvoreB(randomizeT());
            for (String palavraNode : vetorBinario.vetorTratado){
                arvoreB.inserir(palavraNode);
            }
            long stop = System.nanoTime();
            arvoreB.tempoDeExecucao = Tempo.formatarTempoEmString(stop - start);
        }
    };

    private static Runnable adicionarEmArvoreBPlus = new Runnable() {
        @Override
        public void run() {
            long start = System.nanoTime();
            arvoreBPlus = new ArvoreBPlus(randomizeT());
            for (String palavraNode : vetorBinario.vetorTratado){
                arvoreBPlus.inserir(palavraNode);
            }
            long stop = System.nanoTime();
            arvoreBPlus.tempoDeExecucao = Tempo.formatarTempoEmString(stop - start);
        }
    };

    private static int randomizeT(){
        Random random = new Random();
        return random.nextInt((10 - 2) + 1) + 2;
    }

    /// PARA ARVORES RECURSIVAS
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
        vetorBinario.zerarContadores();
        arvoreAVLControl = null;
        arvoreSimplesControl = null;
        arvores.deleteAll();
        nodes.deleteAll();
        LOG.info("Deletado todos os nodes e árvores salvos");
        return ResponseEntity.ok("Todas as arvores foram excluídas");
    }


    // ----- METODOS PRIVADOS -----
    private static Arvore adicionarNaArvore(String textoNode, boolean isAVL){
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

class ExecutarArvores{

    public Binario vetorBinario;

    public MultipartFile arquivo;

    public ExecutarArvores(MultipartFile arquivo, Binario vetor){
        this.arquivo = arquivo;
        this.vetorBinario = vetor;
    }

    void executarCarregamentoVetorBinario() throws IOException {
        vetorBinario.vetorCru = Dados.carregarPalavrasEmVetor(arquivo);
        vetorBinario.tempoLeituraArquivo = Tempo.formatarTempoEmString(Dados.tempoLeituraArquivo);
        vetorBinario.executarBuscaBinariaAndInsercoes();
    }

}
