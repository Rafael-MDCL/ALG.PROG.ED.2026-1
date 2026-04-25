package br.unisinos.model;

public class BTreeNode {

    private int t;
    private int[] keys;
    private BTreeNode[] children;
    private int n;
    private boolean leaf;

    public BTreeNode(final int t, final boolean leaf) {
        this.t = t;
        this.n=0;
        this.leaf=leaf;
        this.keys= new int[2 * t - 1];
        this.children = new BTreeNode[2 * t];
    }

    public int getT() {
        return t;
    }

    public int[] getKeys() {
        return keys;
    }

    public BTreeNode[] getChildren() {
        return children;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public int getNumKeys() {
        return n;
    }

    public void setT(final int t) {
        this.t = t;
    }

    public void setKeys(final int[] keys) {
        this.keys = keys;
    }

    public void setChildren(final BTreeNode[] children) {
        this.children = children;
    }

    public void setLeaf(final boolean leaf) {
        this.leaf = leaf;
    }

    public void setNumKeys(final int numKeys) {
        this.n = numKeys;
    }

}
