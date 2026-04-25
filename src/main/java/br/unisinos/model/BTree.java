package br.unisinos.model;

public class BTree {

    public BTreeNode root;
    private int t;

    public BTree(final int t) {
        this.root = null;
        this.t = t;
    }

    public boolean search(final int key) {
        return searchNode(root, key) != null;
    }

    public void insert(final int key) {
        if (root == null) {
            // arvore vazia - cria a raiz
            root = new BTreeNode(t, true);
            root.getKeys()[0] = key;
            root.setNumKeys(1);

        } else {
            // se a raiz esta cheia, precisa crescer
            if (root.getNumKeys() == 2 * t - 1) {
                BTreeNode novaRaiz = new BTreeNode(t, false);
                novaRaiz.getChildren()[0] = root;
                splitChild(novaRaiz, 0, root);
                root = novaRaiz;
            }
            insertNonFull(root, key);
        }
    }

    public void remove() {
        //no-op
    }

    private BTreeNode searchNode(final BTreeNode node, final int key) {
        int i = 0;
        while (i < node.getNumKeys() && key > node.getKeys()[i]) {
            i++;
        }
        if (i < node.getNumKeys() && key == node.getKeys()[i]) {
            return node;
        }
        if (node.isLeaf()) {
            return null;
        }
        return searchNode(node.getChildren()[i], key);
    }

    private void splitChild(final BTreeNode pai, final int i, final BTreeNode fullChild) {
        // Cria o novo node que vai receber a metade direita
        BTreeNode newNode = new BTreeNode(t, fullChild.isLeaf());
        newNode.setNumKeys(t - 1);

        // Copia a metade direita das chaves para o novo node
        for (int j = 0; j < t - 1; j++) {
            newNode.getKeys()[j] = fullChild.getKeys()[j + t];
        }

        // Se não for folha, copia os filhos também
        if (!fullChild.isLeaf()) {
            for (int j = 0; j < t; j++) {
                newNode.getChildren()[j] = fullChild.getChildren()[j + t];
            }
        }

        // O filho cheio agora só tem a metade esquerda
        fullChild.setNumKeys(t - 1);

        // Abre espaço no pai para encaixar o novo filho
        for (int j = pai.getNumKeys(); j >= i + 1; j--) {
            pai.getChildren()[j + 1] = pai.getChildren()[j];
        }
        pai.getChildren()[i + 1] = newNode;

        // Abre espaço no pai para a chave do meio
        for (int j = pai.getNumKeys() - 1; j >= i; j--) {
            pai.getKeys()[j + 1] = pai.getKeys()[j];
        }

        // Sobe a chave do meio para o pai
        pai.getKeys()[i] = fullChild.getKeys()[t - 1];
        pai.setNumKeys(pai.getNumKeys() + 1);
    }

    private void insertNonFull(final BTreeNode node, final int key) {
        int i = node.getNumKeys() - 1;

        if (node.isLeaf()) {
            // Abre espaço e insere a chave na posição correta
            while (i >= 0 && key < node.getKeys()[i]) {
                node.getKeys()[i + 1] = node.getKeys()[i];
                i--;
            }
            node.getKeys()[i + 1] = key;
            node.setNumKeys(node.getNumKeys() + 1);

        } else {
            // Encontra o filho certo para descer
            while (i >= 0 && key < node.getKeys()[i]) {
                i--;
            }
            i++;

            // Se o filho está cheio, faz o split antes de descer
            if (node.getChildren()[i].getNumKeys() == 2 * t - 1) {
                splitChild(node, i, node.getChildren()[i]);

                // Depois do split decide qual dos dois filhos usar
                if (key > node.getKeys()[i]) {
                    i++;
                }
            }
            insertNonFull(node.getChildren()[i], key);
        }
    }
}
