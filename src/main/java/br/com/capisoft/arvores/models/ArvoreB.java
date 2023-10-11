package br.com.capisoft.arvores.models;

public class ArvoreB {

    private int t;
    private Node root;

    public ArvoreB(int t) {
        this.t = t;
        root = new Node();
    }

    public void insert(int palavra) {
        Node raiz = root;

        if (raiz.n == 2 * t - 1) {
            Node no = new Node();
            root = no;
            no.children[0] = raiz;
            splitChild(no, 0);
            insertNonFull(no, palavra);
        } else {
            insertNonFull(raiz, palavra);
        }
    }

    private void insertNonFull(Node x, int k) {
        int i = x.n - 1;

        if (x.leaf){

            while (i >= 0 && k < x.keys[i]) {
                // Desloca a chave atual para a direita para fazer espaço para K.
                x.keys[i + 1] = x.keys[i];
                // Move para a próxima posição à esquerda
                i--;}
            // Após o loop, a posição correta para inserir
            x.keys[i + 1] = k;
            // O contador de chaves N do nó X é incrementado:
            x.n++;}

        // Caso o nó X não seja uma folha.
        else{
            /*Este loop procura a posição correta para inserir a chave K. A variável I começa como o
        índice da última chave no nó X. O loop desloca-se para a esquerda (diminuindo I) enquanto K for
        menor que a chave na posição I do nó X. O loop termina quando I chega a -1 (antes da primeira chave)
        ou quando a chave K for maior ou igual à chave na posição I.*/
            while (i >= 0 && k < x.keys[i]) {
                i--;}
            /*Após o loop, I é incrementado para apontar para a posição do filho à direita da chave em I.
            Se K fosse menor que todas as chaves no nó X, então I apontaria para a posição 0, que é a do
            primeiro filho.*/
            i++;
            // Esta condição verifica se o filho do nó X na posição I está cheio
            if (x.children[i].n == 2 * t - 1) {
                // Divide o filho cheio.
                splitChild(x, i);
                /*  Se k for maior que a chave no índice i de x (que pode ter mudado após a divisão),
                então mova para o próximo filho.*/
                if (k > x.keys[i]) {
                    i++;}}

            insertNonFull(x.children[i], k);}
    }

    private void splitChild(Node x, int i) {
        Node z = new Node();
        Node y = x.children[i];
        x.children[i + 1] = z;
        z.leaf = y.leaf;
        z.n = t - 1;
        for (int j = 0; j < t - 1; j++) {
            z.keys[j] = y.keys[j + t];
        }
        if (!y.leaf) {
            for (int j = 0; j < t; j++) {
                z.children[j] = y.children[j + t];
            }
        }

        y.n = t - 1;

        for (int j = x.n; j >= i + 1; j--) {
            x.children[j + 1] = x.children[j];
        }

        for (int j = x.n - 1; j >= i; j--) {
            x.keys[j + 1] = x.keys[j];
        }

        x.keys[i] = y.keys[t - 1];
        x.n++;
    }

    public void Delete(int key) {
        Delete(root, key);

        if (root.n == 0 && !root.leaf) {
            root = root.children[0];
        }
    }

    private void Delete(Node x, int k) {
        int idx = 0;

        while (idx < x.n && k > x.keys[idx]) {
            idx++;
        }

        if (idx < x.n && x.keys[idx] == k && x.leaf) {

            for (int i = idx; i < x.n - 1; i++) {
                x.keys[i] = x.keys[i + 1];
            }
            x.n--;
            return;
        }

        if (idx < x.n && x.keys[idx] == k) {
            Node y = x.children[idx];
            Node z = x.children[idx + 1];

            if (y.n >= t) {
                int pred = findPredecessor(y);
                x.keys[idx] = pred;
                Delete(y, pred);
            }

            else if (z.n >= t){
                int succ = findSuccessor(z);
                x.keys[idx] = succ;
                Delete(z, succ);
            }
            else {
                merge(x, idx);
                Delete(y, k);
            }
            return;
        }

        Node child = x.children[idx];

        if (child.n < t) {
            fill(x, idx);
        }
        Delete(x.children[idx], k);
    }

    private int findPredecessor(Node x) {
        while (!x.leaf) {
            x = x.children[x.n];
        }
        return x.keys[x.n - 1];
    }

    private int findSuccessor(Node x) {
        while (!x.leaf) {
            x = x.children[0];
        }
        return x.keys[0];
    }

    private void merge(Node x, int idx) {
        Node y = x.children[idx];
        Node z = x.children[idx + 1];

        y.keys[t - 1] = x.keys[idx];

        for (int j = 0; j < z.n; j++) {
            y.keys[t + j] = z.keys[j];
        }

        if (!z.leaf) {
            for (int j = 0; j <= z.n; j++) {
                y.children[t + j] = z.children[j];
            }
        }

        for (int j = idx; j < x.n - 1; j++) {
            x.keys[j] = x.keys[j + 1];
        }

        for (int j = idx + 1; j < x.n; j++) {
            x.children[j] = x.children[j + 1];
        }

        y.n += z.n + 1;
        x.n--;
    }

    private void fill(Node x, int idx) {
        if (idx > 0 && x.children[idx - 1].n >= t) {
            borrowFromPrev(x, idx);
        } else if (idx < x.n && x.children[idx + 1].n >= t) {
            borrowFromNext(x, idx);
        } else {
            if (idx < x.n){
                merge(x, idx);
            } else{
                merge(x, idx - 1);
            }
        }
    }

    private void borrowFromPrev(Node x, int idx) {
        Node child = x.children[idx];
        Node sibling = x.children[idx - 1];

        for (int i = child.n - 1; i >= 0; i--) {
            child.keys[i + 1] = child.keys[i];
        }

        if (!child.leaf) {
            for (int i = child.n; i >= 0; i--) {
                child.children[i + 1] = child.children[i];
            }
        }

        child.keys[0] = x.keys[idx - 1];

        if (!child.leaf) {
            child.children[0] = sibling.children[sibling.n];
        }

        x.keys[idx - 1] = sibling.keys[sibling.n - 1];

        child.n += 1;
        sibling.n -= 1;
    }

    private void borrowFromNext(Node x, int idx) {
        Node child = x.children[idx];
        Node sibling = x.children[idx + 1];

        child.keys[child.n] = x.keys[idx];

        if (!child.leaf) {
            child.children[child.n + 1] = sibling.children[0];
        }

        x.keys[idx] = sibling.keys[0];

        for (int i = 1; i < sibling.n; i++) {
            sibling.keys[i - 1] = sibling.keys[i];
        }

        if (!sibling.leaf) {
            for (int i = 1; i <= sibling.n; i++) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }

        child.n += 1;
        sibling.n -= 1;
    }

    private class Node {
        int n;
        int[] keys = new int[2 * t - 1];

        Node[] children = new Node[2 * t];
        boolean leaf = true;

        Node(){
            n = 0;
            for (int i = 0; i < 2 * t; i++) {
                children[i] = null;}
        }
    }

}
