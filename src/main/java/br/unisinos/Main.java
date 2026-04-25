package br.unisinos;

import br.unisinos.model.BTree;

public class Main {
    public static void main(String[] args) {
        //teste de insert
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);
        tree.insert(30);
        tree.insert(15);

        System.out.println(tree.search(15));
        System.out.println(tree.search(99));
    }
}