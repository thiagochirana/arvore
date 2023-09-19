package br.com.capisoft.arvores.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Entity
@Table(name = "arvore")
public class Arvore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "arvore_id")
    private Long id;

    @Column(name = "altura")
    private int altura = 0;

    @OneToOne(cascade = CascadeType.ALL)
    private Node root;

    @OneToMany(mappedBy = "arvore", cascade = CascadeType.ALL)
    private List<Palavra> palavra = new ArrayList<>();

    @Transient
    boolean isAVL = false;

    @Transient
    public int comparacoes = 0;

    @Transient
    public int rotacoes = 0;

    @Transient
    public String tempoDeExecucao;


    public Arvore(Node root) {
        this.root = root;
        this.altura++;
        root.adicionarNivel();
        this.isAVL = false;
    }

    public Arvore(Node root, boolean isAVL) {
        this.isAVL = isAVL;
        this.root = root;
        this.altura++;
        root.adicionarNivel();
    }

    public Arvore(){}

    public boolean contemRaiz(){
        return this.root != null;
    }

    public Node getRoot() {
        return root;
    }

    public int getAltura(){
        return this.altura;
    }

    public void aumentarAltura(){
        this.altura++;
    }

    public void diminuirAltura(){
        this.altura--;
    }

    public boolean isAVL(){
        return this.isAVL;
    }

    public Long getId() {
        return id;
    }

    public void adicionarNaListaDePalavras(String palavra){
        String word = palavra.toLowerCase();
        if (this.palavra.size() == 0){
            this.palavra.add(new Palavra(this,word));
        } else {
            Iterator<Palavra> iterator = this.palavra.iterator();
            boolean palavraEncontrada = false;

            while (iterator.hasNext()) {
                Palavra p = iterator.next();
                if (p.getPalavra().toLowerCase().equals(word)) {
                    p.aumentarContagem();
                    palavraEncontrada = true;
                    break; // Exit the loop since we found the word
                }
            }

            if (!palavraEncontrada) {
                this.palavra.add(new Palavra(this,word));
            }
        }
    }

    public List<Palavra> getListaDePalavras(){
        return this.palavra;
    }

    public void setRoot(Node node){
        this.root = node;
    }

    @Override
    public String toString() {
        return "ARVORE *[RAIZ=" + root.toString() + ", é AVL? =" + (isAVL ?"SIM":"NAO") + "]*";
    }
}
