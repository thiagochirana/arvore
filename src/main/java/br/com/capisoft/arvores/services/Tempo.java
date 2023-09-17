package br.com.capisoft.arvores.services;

public class Tempo {

    public Tempo(){}

    public String formatarTempo(long tempo){
        if (tempo > 1000){
            return (tempo / 1000.0) + " ms";
        }
        return tempo + " nanosegundos";
    }

    public static String formatarTempoEmString(long tempo){
        if (tempo > 1000){
            return (tempo / 1000.0) + " ms";
        }
        return tempo + " nanosegundos";
    }
}
