package br.unisinos.service;

import br.unisinos.model.BTreeNode;

public class BTreeTraversal {

    public BTreeTraversal() {

    }

    public static void preOrder(final BTreeNode node) {
        if (node == null) return;

        for (int i = 0; i < node.getNumKeys(); i++) {
            System.out.print(node.getKeys()[i] + " ");
        }

        if (!node.isLeaf()) {
            for (int i = 0; i <= node.getNumKeys(); i++) {
                preOrder(node.getChildren()[i]);
            }
        }
    }

    public static void inOrder(final BTreeNode node) {
        if (node == null) return;

        for (int i = 0; i < node.getNumKeys(); i++) {
            if (!node.isLeaf()) {
                inOrder(node.getChildren()[i]);
            }
            System.out.print(node.getKeys()[i] + " ");
        }

        if (!node.isLeaf()) {
            inOrder(node.getChildren()[node.getNumKeys()]);
        }
    }

    public static void posOrder(final BTreeNode node) {
        if (node == null) return;

        if (!node.isLeaf()) {
            for (int i = 0; i <= node.getNumKeys(); i++) {
                posOrder(node.getChildren()[i]);
            }
        }

        for (int i = 0; i < node.getNumKeys(); i++) {
            System.out.print(node.getKeys()[i] + " ");
        }
    }
}