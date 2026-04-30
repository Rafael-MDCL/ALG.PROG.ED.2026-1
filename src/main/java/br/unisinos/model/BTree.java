package br.unisinos.model;

public class BTree {

    public BTreeNode root;
    private int t;

    public BTree(final int t) {
        this.root = null;
        this.t = t;
    }

    public boolean search(final int key) {
        if (root == null) return false;
        return searchNode(root, key) != null;
    }

    public void insert(final int key) {
        if (root == null) {
            root = new BTreeNode(t, true);
            root.getKeys()[0] = key;
            root.setNumKeys(1);

        } else {
            if (root.getNumKeys() == 2 * t - 1) {
                BTreeNode novaRaiz = new BTreeNode(t, false);
                novaRaiz.getChildren()[0] = root;
                splitChild(novaRaiz, 0, root);
                root = novaRaiz;
            }
            insertNonFull(root, key);
        }
    }

    public void remove(final int key) {
        if (root == null) return;

        removeKey(root, key);

        if (root.getNumKeys() == 0 && !root.isLeaf()) {
            root = root.getChildren()[0];
        }
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
        // cria o novo node que vai receber a metade direita
        BTreeNode newNode = new BTreeNode(t, fullChild.isLeaf());
        newNode.setNumKeys(t - 1);

        // copia a metade direita das chaves para o novo node
        for (int j = 0; j < t - 1; j++) {
            newNode.getKeys()[j] = fullChild.getKeys()[j + t];
        }

        // se nao for folha copia os filhos tambem
        if (!fullChild.isLeaf()) {
            for (int j = 0; j < t; j++) {
                newNode.getChildren()[j] = fullChild.getChildren()[j + t];
            }
        }

        // filho cheio agora só tem a metade esquerda
        fullChild.setNumKeys(t - 1);

        // abre espaco no pai para encaixar novo filho
        for (int j = pai.getNumKeys(); j >= i + 1; j--) {
            pai.getChildren()[j + 1] = pai.getChildren()[j];
        }
        pai.getChildren()[i + 1] = newNode;

        // abre espaco no pai para a chave do meio
        for (int j = pai.getNumKeys() - 1; j >= i; j--) {
            pai.getKeys()[j + 1] = pai.getKeys()[j];
        }

        // sobre a chave do meio para o pai
        pai.getKeys()[i] = fullChild.getKeys()[t - 1];
        pai.setNumKeys(pai.getNumKeys() + 1);
    }

    private void insertNonFull(final BTreeNode node, final int key) {
        int i = node.getNumKeys() - 1;

        if (node.isLeaf()) {
            // abre espaço e insere a chave na posicao certa
            while (i >= 0 && key < node.getKeys()[i]) {
                node.getKeys()[i + 1] = node.getKeys()[i];
                i--;
            }
            node.getKeys()[i + 1] = key;
            node.setNumKeys(node.getNumKeys() + 1);

        } else {
            // encontra o filho certo para descer
            while (i >= 0 && key < node.getKeys()[i]) {
                i--;
            }
            i++;

            // se o filho esta cheio faz split antes de descer
            if (node.getChildren()[i].getNumKeys() == 2 * t - 1) {
                splitChild(node, i, node.getChildren()[i]);

                // depois do split decide qual dos dois filhos usar
                if (key > node.getKeys()[i]) {
                    i++;
                }
            }
            insertNonFull(node.getChildren()[i], key);
        }
    }

    private void removeKey(BTreeNode node, int key) {
        int i = findKeyIndex(node, key);

        if (i < node.getNumKeys() && node.getKeys()[i] == key) {
            // chave esta nesse node
            if (node.isLeaf()) {
                removeFromLeaf(node, i);
            } else {
                removeFromNonLeaf(node, i);
            }
        } else {
            if (node.isLeaf()) {
                return;
            }

            // garante que o filho tem chaves suficientes
            if (node.getChildren()[i].getNumKeys() < t) {
                fill(node, i);
            }

            // desce para o filho certo
            if (i > node.getNumKeys()) {
                removeKey(node.getChildren()[i - 1], key);
            } else {
                removeKey(node.getChildren()[i], key);
            }
        }
    }

    private int findKeyIndex(BTreeNode node, int key) {
        int i = 0;
        while (i < node.getNumKeys() && key > node.getKeys()[i]) {
            i++;
        }
        return i;
    }

    private void removeFromLeaf(BTreeNode node, int i) {
        // desloca todas as chaves a direita de i uma posicao para a esquerda
        for (int j = i + 1; j < node.getNumKeys(); j++) {
            node.getKeys()[j - 1] = node.getKeys()[j];
        }
        node.setNumKeys(node.getNumKeys() - 1);
    }

    private void removeFromNonLeaf(BTreeNode node, int i) {
        int key = node.getKeys()[i];

        if (node.getChildren()[i].getNumKeys() >= t) {
            // pega a maior chave da subarvore esquerda
            int predecessor = getPredecessor(node, i);
            node.getKeys()[i] = predecessor;
            removeKey(node.getChildren()[i], predecessor);

        } else if (node.getChildren()[i + 1].getNumKeys() >= t) {
            // pega o menor chave da subarvore direita
            int successor = getSuccessor(node, i);
            node.getKeys()[i] = successor;
            removeKey(node.getChildren()[i + 1], successor);

        } else {
            // caso ambos os filhos tem o minimo
            merge(node, i);
            removeKey(node.getChildren()[i], key);
        }
    }

    private int getPredecessor(BTreeNode node, int i) {
        // desce sempre pelo filho mais a direita ate chegar na leaf
        BTreeNode current = node.getChildren()[i];
        while (!current.isLeaf()) {
            current = current.getChildren()[current.getNumKeys()];
        }
        return current.getKeys()[current.getNumKeys() - 1];
    }

    private int getSuccessor(BTreeNode node, int i) {
        // desce sempre pelo filho mais a esquerda ate chegar na leaf
        BTreeNode current = node.getChildren()[i + 1];
        while (!current.isLeaf()) {
            current = current.getChildren()[0];
        }
        return current.getKeys()[0];
    }

    private void fill(BTreeNode node, int i) {
        if (i != 0 && node.getChildren()[i - 1].getNumKeys() >= t) {
            borrowFromPrev(node, i);  // irmao esquerdo e rico

        } else if (i != node.getNumKeys() && node.getChildren()[i + 1].getNumKeys() >= t) {
            borrowFromNext(node, i);  // irmao direito e rico

        } else {
            if (i != node.getNumKeys()) {
                merge(node, i);       // merge com irmao direito
            } else {
                merge(node, i - 1);   // merge com irmao esquerdo
            }
        }
    }

    private void borrowFromPrev(BTreeNode node, int i) {
        BTreeNode filho = node.getChildren()[i];
        BTreeNode irmao = node.getChildren()[i - 1];

        // abre espaco no filho deslocando tudo para a direita
        for (int j = filho.getNumKeys() - 1; j >= 0; j--) {
            filho.getKeys()[j + 1] = filho.getKeys()[j];
        }
        if (!filho.isLeaf()) {
            for (int j = filho.getNumKeys(); j >= 0; j--) {
                filho.getChildren()[j + 1] = filho.getChildren()[j];
            }
        }

        // chave separadora do pai desce para o filho
        filho.getKeys()[0] = node.getKeys()[i - 1];
        if (!filho.isLeaf()) {
            filho.getChildren()[0] = irmao.getChildren()[irmao.getNumKeys()];
        }

        // ultima chave do irmao sobe para o pai
        node.getKeys()[i - 1] = irmao.getKeys()[irmao.getNumKeys() - 1];

        filho.setNumKeys(filho.getNumKeys() + 1);
        irmao.setNumKeys(irmao.getNumKeys() - 1);
    }

    private void borrowFromNext(BTreeNode node, int i) {
        BTreeNode filho = node.getChildren()[i];
        BTreeNode irmao = node.getChildren()[i + 1];

        // chave separadora do pai vai para o final do filho
        filho.getKeys()[filho.getNumKeys()] = node.getKeys()[i];
        if (!filho.isLeaf()) {
            filho.getChildren()[filho.getNumKeys() + 1] = irmao.getChildren()[0];
        }

        // primeira chave do irmao sobe para o pai
        node.getKeys()[i] = irmao.getKeys()[0];

        // desloca as chaves do irmao para a esquerda
        for (int j = 1; j < irmao.getNumKeys(); j++) {
            irmao.getKeys()[j - 1] = irmao.getKeys()[j];
        }
        if (!irmao.isLeaf()) {
            for (int j = 1; j <= irmao.getNumKeys(); j++) {
                irmao.getChildren()[j - 1] = irmao.getChildren()[j];
            }
        }

        filho.setNumKeys(filho.getNumKeys() + 1);
        irmao.setNumKeys(irmao.getNumKeys() - 1);
    }

    private void merge(BTreeNode node, int i) {
        BTreeNode filho = node.getChildren()[i];
        BTreeNode irmao = node.getChildren()[i + 1];

        // chave separadora do pai desce para o meio do filho
        filho.getKeys()[t - 1] = node.getKeys()[i];

        // copia as chaves do irmao para o filho
        for (int j = 0; j < irmao.getNumKeys(); j++) {
            filho.getKeys()[j + t] = irmao.getKeys()[j];
        }
        if (!filho.isLeaf()) {
            for (int j = 0; j <= irmao.getNumKeys(); j++) {
                filho.getChildren()[j + t] = irmao.getChildren()[j];
            }
        }

        // remove chave separadora do pai
        for (int j = i + 1; j < node.getNumKeys(); j++) {
            node.getKeys()[j - 1] = node.getKeys()[j];
        }
        for (int j = i + 2; j <= node.getNumKeys(); j++) {
            node.getChildren()[j - 1] = node.getChildren()[j];
        }

        filho.setNumKeys(filho.getNumKeys() + irmao.getNumKeys() + 1);
        node.setNumKeys(node.getNumKeys() - 1);
    }


}
