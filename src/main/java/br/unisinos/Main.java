package br.unisinos;
import br.unisinos.model.BTree;
import br.unisinos.visualization.BTreeVisualization;

import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        gerarExemplo1();
        gerarExemplo2();
        gerarExemplo3();
        gerarExemplo4();
        gerarExemplo5();
        System.out.println("Arquivos .dot gerados em dotFiles/");
    }

    // exemplo 1 — insercoes simples sem split
    private static void gerarExemplo1() {
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo1.dot");
    }

    // exemplo 2 — insercoes que causam split na raiz
    private static void gerarExemplo2() {
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo2.dot");
    }

    // exemplo 3 — arvore maior com varios niveis
    private static void gerarExemplo3() {
        BTree tree = new BTree(2);
        int[] valores = {10, 20, 5, 30, 15, 25, 35, 1, 7, 12};
        for (int v : valores) tree.insert(v);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo3.dot");
    }

    // exemplo 4 — insercoes seguidas de remocoes em folha
    private static void gerarExemplo4() {
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);
        tree.insert(30);
        tree.insert(15);
        tree.remove(5);
        tree.remove(15);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo4.dot");
    }

    // exemplo 5 — remocao de chave interna com merge
    private static void gerarExemplo5() {
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);
        tree.insert(30);
        tree.insert(15);
        tree.insert(25);
        tree.insert(35);
        tree.remove(20);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo5.dot");
    }

    private static void salvarDot(String conteudo, String caminho) {
        try {
            java.io.File dir = new java.io.File("dotFiles");
            if (!dir.exists()) dir.mkdirs();

            FileWriter fw = new FileWriter(caminho);
            fw.write(conteudo);
            fw.close();
            System.out.println("Salvo: " + caminho);
        } catch (IOException e) {
            System.err.println("Erro ao salvar " + caminho + ": " + e.getMessage());
        }
    }
}