package br.com.capisoft.arvores.models;

import br.com.capisoft.arvores.models.DTOs.NodeSimplesDTO;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "node")
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "palavra")
    public String palavra;

    @Column(name = "nivel_atual")
    public int niveis = 0;

    @Column(name = "altura_atual")
    public int altura = 0;

    @ManyToOne
    public Node direita;

    @ManyToOne
    public Node esquerda;

    @Transient
    public int fatorBalanceamento = 0;

    public Node(String palavra) {
        this.palavra = palavra;
    }

    public Node(){}

    public Node getNoDireito(){
       return this.direita;
    }

    public Node getNoEsquerdo(){
        return this.esquerda;
    }

    public boolean contemNoDireito(){
        return this.direita != null;
    }

    public boolean contemNoEsquerdo(){
        return this.esquerda != null;
    }

    public void adicionarNaEsquerda(Node node){
        this.esquerda = node;
    }

    public void adicionarNaDireita(Node node){
        this.direita = node;
    }

    public void removerNaEsquerda(){
        this.esquerda = null;
    }

    public void removerNaDireita(){
        this.direita = null;
    }

    public String getPalavra(){
        return this.palavra;
    }

    public void adicionarNivel(){
        this.niveis++;
    }

    public void removerNivel(){
        this.niveis--;
    }

    public int getNiveis() {
        return niveis;
    }

    public void setNiveis(int niveis) {
        this.niveis = niveis;
    }

    public int getFatorBalanceamento() {
        return fatorBalanceamento;
    }

    public void setFatorBalanceamento(int fatorBalanceamento) {
        this.fatorBalanceamento = fatorBalanceamento;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void setEsquerda(Node node){
        this.esquerda = node;
    }

    public void setDireita(Node node){
        this.direita = node;
    }

    public Long getId() {
        return this.id;
    }

    public NodeSimplesDTO getDTO(){
        return new NodeSimplesDTO(
                this.getId(),
                this.getPalavra(),
                this.getNiveis()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return niveis == node.niveis && Objects.equals(id, node.id) && Objects.equals(palavra, node.palavra) && Objects.equals(direita, node.direita) && Objects.equals(esquerda, node.esquerda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, palavra, niveis, direita, esquerda);
    }

    @Override
    public String toString() {
        return "NODE['" + palavra.toUpperCase() + "\']";
    }
}
