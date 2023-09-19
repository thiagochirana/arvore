package br.com.capisoft.arvores.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Binario {

    private Long id;

    public String[] vetorCru;

    public String[] vetorTratado;

    public String[] vetorOrganizado;

    public long tempoOrdenacaoVetor = 0;

    public long tempoExecucaoBuscaBinaria = 0;

    public String tempoLeituraArquivo = "";

    public int contadorComparacoesBinaria = 0;

    public Map<String, Integer> frequenciaDePalavras;

    private static Logger LOG = LoggerFactory.getLogger(Binario.class);

    public Binario(){
        this.vetorTratado = new String[1];
        this.vetorOrganizado = new String[1];
        this.frequenciaDePalavras = new HashMap<>();
    }

    public void adicionarFrequenciaPalavra(String palavra){
        if (this.frequenciaDePalavras.containsKey(palavra.toLowerCase())){
            this.frequenciaDePalavras.put(palavra.toLowerCase(), this.frequenciaDePalavras.get(palavra.toLowerCase()) + 1);
        } else {
            this.frequenciaDePalavras.put(palavra.toLowerCase(),1);
        }
    }

    private boolean jaExistePalavra(String palavra){
        return this.frequenciaDePalavras.containsKey(palavra.toLowerCase());
    }

    public void executarBuscaBinariaAndInsercoes(){

        StringBuilder palavrasConcat = new StringBuilder();

        for(String palavra : vetorCru){
            if (!jaExistePalavra(palavra)) palavrasConcat.append(palavra).append(" ");
        }

        vetorTratado = palavrasConcat.toString().split(" ");

        for (String palavra : vetorTratado){
            long start = System.nanoTime();
            int res = buscaBinaria(palavra, 0, vetorOrganizado.length-1);
            long stop = System.nanoTime();
            tempoExecucaoBuscaBinaria += (stop - start);

            if(res == -1){
                insert(palavra,res);
            } else {
                adicionarFrequenciaPalavra(palavra);
            }
            long stopOrd = System.nanoTime();
            tempoOrdenacaoVetor += stopOrd - start;
        }
        LOG.info("Execucao e inserções no vetor está concluída");
        LOG.info("VETOR ->> "+vetorOrganizado);
    }

    private int buscaBinaria(String palavra, int inicio, int fim){
        contadorComparacoesBinaria++;
        int meio = (fim-inicio)/2;

        if (this.vetorOrganizado == null || this.vetorOrganizado.length == 1){
            this.vetorOrganizado = new String[]{palavra};
            return 0;
        }

        int res = palavra.compareToIgnoreCase(this.vetorOrganizado[meio]);

        if (res < 0){
            buscaBinaria(palavra, inicio, meio);
        } else if (res > 0){
            buscaBinaria(palavra, meio, fim);
        } else {
            return meio;
        }
        return -1;
    }

    private void insert(String palavra, int pos){
        adicionarFrequenciaPalavra(palavra);

        String[] vetAux = new String[vetorOrganizado.length + 1];

        for (int i = 0;  i < pos ; i++){
            vetAux[i] = vetorOrganizado[i];
        }

        vetAux[pos] = palavra;

        for(int i = pos; i < vetorOrganizado.length ; i++){
            vetAux[i+1] = vetorOrganizado[i];
        }

        vetorOrganizado = vetAux;
    }

    public Map<String, Integer> getFrequenciaDePalavras() {
        return this.frequenciaDePalavras;
    }

    public void zerarContadores(){
        frequenciaDePalavras = new HashMap<>();
        tempoLeituraArquivo = null;
        contadorComparacoesBinaria = 0;
        tempoOrdenacaoVetor = 0;
        tempoExecucaoBuscaBinaria = 0;
    }
}
