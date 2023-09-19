package br.com.capisoft.arvores.utils;

import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.io.*;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.Collections.replaceAll;

public class Dados {

    List<String> listaPalavras;

    public static long tempoLeituraArquivo = 0;

    public void adicionarTextoTeste(MultipartFile arquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(arquivo.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line); // Print each line to the console
        }
        reader.close();
    }

    public static String[] carregarPalavrasEmVetor(MultipartFile arquivo) throws IOException {
        long start = System.nanoTime();
        BufferedReader br = new BufferedReader(new InputStreamReader(arquivo.getInputStream()));
        String linha = "";
        StringBuilder palavrasAux = new StringBuilder();
        while((linha = br.readLine()) != null){
            String linhaSemAcento = removerAcentos(linha);
            palavrasAux.append(linhaSemAcento).append(" ");
        }
        long stop = System.nanoTime();
        tempoLeituraArquivo = (stop-start);
        return palavrasAux.toString().replaceAll("[^a-zA-Z0-9 ]", "").split(" ");
    }

    public List<String> carregarListaDePalavras(MultipartFile arquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(arquivo.getInputStream()));
        String line;
        List<String> lista = new ArrayList<>();
        Pattern wordPattern = Pattern.compile("\\p{L}+"); // Palavras com acentos

        // Carregue as stopwords do arquivo
        Set<String> stopWords = carregarStopWords();

        while ((line = reader.readLine()) != null) {
            for (String s : line.split(" ")) {
                String[] words = s.split("[^\\p{L}]+");

                for (String word : words) {
                    // Adicione a palavra original à lista
                    if (!word.isEmpty() && !stopWords.contains(word.toLowerCase())) {
                        lista.add(word.toLowerCase());
                    }
                }
            }
        }
        reader.close();
        this.listaPalavras = lista;
        return lista;
    }

    // Método para carregar stopwords do arquivo
    private Set<String> carregarStopWords() throws IOException {
        Set<String> stopWords = new HashSet<>();
        File stopWordsFile = new File("./src/main/resources/utils/stopwords.txt");

        if (stopWordsFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(stopWordsFile));
            String line;

            while ((line = reader.readLine()) != null) {
                stopWords.add(line.trim().toLowerCase());
            }

            reader.close();
        }

        return stopWords;
    }

    public List<String> getTextoCarregado(){
        return this.listaPalavras;
    }

    public boolean listaEstaVazia(){
        return this.listaPalavras != null;
    }

    public static String removerAcentos(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[^\\p{ASCII}]", "");
        return texto;
    }
}
