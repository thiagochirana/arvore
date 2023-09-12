package br.com.capisoft.arvores.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Dados {

    List<String> listaPalavras;

    public void adicionarTextoTeste(MultipartFile arquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(arquivo.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line); // Print each line to the console
        }
        reader.close();
    }
    
    public List<String> carregarListaDePalavras(MultipartFile arquivo) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(arquivo.getInputStream()));
        String line;
        List<String> lista = new ArrayList<>();
        Pattern nonAlphanumeric = Pattern.compile("\\p{M}");

        // Carregue as stopwords do arquivo
        Set<String> stopWords = carregarStopWords();

        while ((line = reader.readLine()) != null) {
            for (String s : line.split(" ")) {
                // Remova caracteres não-alfanuméricos
                String cleanedWord = nonAlphanumeric.matcher(s).replaceAll("");

                // Normalize a string para remover acentos
                String normalizedWord = Normalizer.normalize(cleanedWord, Normalizer.Form.NFD);
                String withoutAccentsWord = normalizedWord.replaceAll("\\p{M}", "").toLowerCase();

                // Adicionar a palavra sem acento
                if (!withoutAccentsWord.isEmpty() && !stopWords.contains(withoutAccentsWord.toLowerCase())) {
                    lista.add(withoutAccentsWord); 
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
}
