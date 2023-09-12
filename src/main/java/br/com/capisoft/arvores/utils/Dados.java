package br.com.capisoft.arvores.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> carregarListaDePalavras(MultipartFile arquivo) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(arquivo.getInputStream()));
        String line;
        List<String> lista = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            for (String s : line.split(" ")){
                //tratamento das palavras para não obter com caracteres não-alfanuméricos || TODO -> AQUI É GO HORSE, FAVOR TROCAR ESSA GAMBITÉCNICA
                lista.add(s
                        .trim()
                        .replace(".","")
                        .replace(",","")
                        .replace("#","")
                        .replace("%","")
                        .replace("@","")
                        .replace("¨","")
                        .replace("*","")
                        .replace("(","")
                        .replace(")","")
                        .replace("{","")
                        .replace("}","")
                        .replace("[","")
                        .replace("]","")
                        .replace(";","")
                        .replace("<","")
                        .replace(">","")
                        .replace(" + ","")
                        .replace("=","")
                        .replace("â","a")
                        .replace("ã","a")
                        .replace("á","a")
                        .replace("é","e")
                        .replace("ẽ","e")
                        .replace("ê","e")
                        .replace("í","i")
                        .replace("ĩ","i")
                        .replace("î","i")
                        .replace("ó","o")
                        .replace("ô","o")
                        .replace("õ","o")
                        .replace("ú","u")
                        .replace("ũ","u")
                        .replace("û","u")
                        .replace("ç","c")
                );
            }
        }
        reader.close();
        this.listaPalavras = lista;
        return lista;
    }

    public List<String> getTextoCarregado(){
        return this.listaPalavras;
    }

    public boolean listaEstaVazia(){
        return this.listaPalavras != null;
    }
}
