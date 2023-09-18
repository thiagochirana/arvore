package br.com.capisoft.arvores.services;

public class Tempo {

    public Tempo(){}

    public String formatarTempo(long tempo){
        if (tempo > 1000000000){
            return (tempo / 1000000000.0) + " segundos";
        } else if (tempo > 1000000){
            return (tempo / 1000000.0) + " milisegundos";
        }
        return tempo + " nanosegundos";
    }

    public static String formatarTempoEmString(long tempo){
        if (tempo > 1000000000){
            return (tempo / 1000000000.0) + " segundos";
        } else if (tempo > 1000000){
            return (tempo / 1000000.0) + " milisegundos";
        }
        return tempo + " nanosegundos";
    }
}
