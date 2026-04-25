package br.unisinos.model;

public class BRTreeNode {

    //implementar chaves, filhos, contador de chaves, folha ou não

    private int[] keys;
    private BRTreeNode[] children;
    private boolean leaf;
    private int numKeys;

    public BRTreeNode(final int t, final boolean leaf) {
        this.numKeys=0;
        this.leaf=leaf;
        this.keys= new int[2 * t - 1];
        this.children = new BRTreeNode[2 * t];
    }

    public int[] getKeys() {
        return keys;
    }

    public BRTreeNode[] getChildren() {
        return children;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public int getNumKeys() {
        return numKeys;
    }

    public void setKeys(int[] keys) {
        this.keys = keys;
    }

    public void setChildren(BRTreeNode[] children) {
        this.children = children;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public void setNumKeys(int numKeys) {
        this.numKeys = numKeys;
    }


}
