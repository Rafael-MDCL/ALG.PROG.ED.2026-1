package br.unisinos;
import br.unisinos.model.BTree;
import br.unisinos.service.BTreeTraversal;
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
        gerarExemplo6();
        gerarExemplo7();
        gerarExemplo8();
        gerarExemplo9();
        gerarExemplo10();
        System.out.println("\nTodos os arquivos .dot foram gerados em dotFiles/");
    }

    // exemplo 1 — arvore grande e profunda com 30+ nos e 4-5 niveis
    // multiplos splits e estrutura profunda balanceada
    private static void gerarExemplo1() {
        System.out.println("\n*** Exemplo 1: Arvore Grande e Profunda com evolucao");
        BTree tree = new BTree(2);

        int[] valores = {50, 25, 75, 10, 30, 60, 90, 5, 15, 20, 35, 55, 65, 85, 95,
                3, 7, 12, 17, 22, 27, 32, 37, 52, 57, 62, 67, 82, 87, 92, 97};

        // primeiros 5 valores
        for (int i = 0; i < 5; i++) {
            tree.insert(valores[i]);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo1a_Inicial.dot");
        System.out.println("Estado 1: 5 valores inseridos");

        // primeiros 15 valores
        for (int i = 5; i < 15; i++) {
            tree.insert(valores[i]);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo1b_Meio.dot");
        System.out.println("Estado 2: 15 valores inseridos");

        // todos os 31 valores
        for (int i = 15; i < valores.length; i++) {
            tree.insert(valores[i]);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo1c_Final.dot");
        System.out.println("Estado 3: 31 valores inseridos (final)");

        System.out.println("Caminhamento em inOrder:");
        BTreeTraversal.inOrder(tree.root);
        System.out.println();
    }

    // exemplo 2 — demonstra operacao borrowFromPrev (emprestimo do irmao esquerdo)
    // constroi arvore e remove chaves que forcam o emprestimo
    private static void gerarExemplo2() {
        System.out.println("\n***Exemplo 2: Emprestimo do Irmao Esquerdo com evolucao");
        BTree tree = new BTree(3);

        for (int i = 1; i <= 20; i++) {
            tree.insert(i);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo2a_AntesRemocoes.dot");
        System.out.println("Estado 1: Arvore completa com 1 a 20");

        // remove valores estrategicos para forcar borrowFromPrev
        tree.remove(11);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo2b_Remove11.dot");
        System.out.println("Estado 2: Removido 11");

        tree.remove(12);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo2c_Remove12.dot");
        System.out.println("Estado 3: Removido 12");

        tree.remove(13);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo2d_Remove13_EmprestimoEsquerdo.dot");
        System.out.println("Estado 4: Removido 13 (emprestimo do irmao esquerdo ocorre)");

        System.out.println("Caminhamento pre-ordem final:");
        BTreeTraversal.preOrder(tree.root);
        System.out.println();
    }

    // exemplo 3 — demonstra operacao borrowFromNext com emprestimo do irmao direito
    // cria cenario onde o irmao direito empresta chave
    private static void gerarExemplo3() {
        System.out.println("\n*** Exemplo 3: Emprestimo do Irmao Direito com evolucao");
        BTree tree = new BTree(2);

        int[] valores = {20, 40, 60, 80, 100, 120, 140, 30, 50, 70, 90, 110, 130, 10, 25, 35, 45};
        for (int v : valores) {
            tree.insert(v);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo3a_AntesRemocoes.dot");
        System.out.println("Estado 1: Arvore completa com 17 valores");

        // remove para forcar borrowFromNext
        tree.remove(30);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo3b_Remove30.dot");
        System.out.println("Estado 2: Removido 30");

        tree.remove(35);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo3c_Remove35_EmprestimoDireito.dot");
        System.out.println("Estado 3: Removido 35 (emprestimo do irmao direito ocorre)");

        System.out.println("Caminhamento em ordem final:");
        BTreeTraversal.inOrder(tree.root);
        System.out.println();
    }

    // exemplo 4 — demonstra multiplas operacoes de merge em cascata
    // remove varios nos para forcar merges consecutivos e reducao de altura
    private static void gerarExemplo4() {
        System.out.println("\n*** Exemplo 4: Multiplos Merges em Cascata com evolucao");
        BTree tree = new BTree(2);

        for (int i = 1; i <= 25; i++) {
            tree.insert(i);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo4a_Inicial25nos.dot");
        System.out.println("Estado 1: Arvore inicial com 25 nos");

        // remove valores que causam merges em cascata
        tree.remove(25);
        tree.remove(24);
        tree.remove(23);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo4b_Remove3valores.dot");
        System.out.println("Estado 2: Removidos 25, 24, 23");

        tree.remove(22);
        tree.remove(21);
        tree.remove(20);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo4c_Remove6valores.dot");
        System.out.println("Estado 3: Removidos ate 20");

        tree.remove(19);
        tree.remove(18);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo4d_Final_MultiplosMerges.dot");
        System.out.println("Estado 4: Removidos ate 18 (merges em cascata)");

        System.out.println("Caminhamento pos-ordem final:");
        BTreeTraversal.posOrder(tree.root);
        System.out.println();
    }

    // exemplo 5 — jornada de evolucao da arvore
    // arvore se adaptando atraves de multiplas operacoes mistas
    private static void gerarExemplo5() {
        System.out.println("\n*** Exemplo 5: Evolucao da Arvore com insercoes e remocoes Mistas");
        BTree tree = new BTree(2);

        System.out.println("Fase 1: Inserindo 10 a 24");
        for (int i = 10; i <= 24; i++) {
            tree.insert(i);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo5a_Fase1_Insercoes.dot");
        System.out.println("Estado 1: 15 valores inseridos (10 a 24)");

        // fase 2: remove alguns elementos
        System.out.println("Fase 2: Removendo 15, 17, 19");
        tree.remove(15);
        tree.remove(17);
        tree.remove(19);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo5b_Fase2_PrimeirasRemocoes.dot");
        System.out.println("Estado 2: Removidos 15, 17, 19");

        // fase 3: adiciona mais elementos
        System.out.println("Fase 3: Inserindo 1 a 9");
        for (int i = 1; i <= 9; i++) {
            tree.insert(i);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo5c_Fase3_MaisInsercoes.dot");
        System.out.println("Estado 3: Adicionados valores 1 a 9");

        // fase 4: remove varios para forcar rebalanceamento
        System.out.println("Fase 4: Removendo 20, 21, 22, 23, 10, 11, 12, 13");
        int[] remover = {20, 21, 22, 23, 10, 11, 12, 13};
        for (int v : remover) {
            tree.remove(v);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo5d_Fase4_Final.dot");
        System.out.println("Estado 4: Estado final apos todas operacoes");

        System.out.println("Caminhamento em ordem final:");
        BTreeTraversal.inOrder(tree.root);
        System.out.println();
    }

    // exemplo 6 — arvore grande com 35 nos
    private static void gerarExemplo6() {
        System.out.println("\n Exemplo 6: Arvore Grande");
        BTree tree = new BTree(3);

        System.out.println("Inserindo multiplos de 3 de 3 ate 105");

        // primeiros 10 valores
        for (int i = 3; i <= 30; i += 3) {
            tree.insert(i);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo6a_10valores.dot");
        System.out.println("Estado 1: 10 valores (3 a 30)");

        // primeiros 20 valores
        for (int i = 33; i <= 60; i += 3) {
            tree.insert(i);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo6b_20valores.dot");
        System.out.println("Estado 2: 20 valores (3 a 60)");

        // todos 35 valores
        for (int i = 63; i <= 105; i += 3) {
            tree.insert(i);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo6c_35valores_Final.dot");
        System.out.println("Estado 3: 35 valores completos (3 a 105)");

        System.out.println("Caminhamento pre-ordem final:");
        BTreeTraversal.preOrder(tree.root);
        System.out.println();
    }

    // exemplo 7 — todos os caminhamentos
    // arvore balanceada perfeita para visualizar as diferencas
    private static void gerarExemplo7() {
        System.out.println("\n*** Exemplo 7: Todos os Caminhamentos");
        BTree tree = new BTree(2);

        int[] valores = {50, 25, 75, 15, 35, 60, 90, 10, 20, 30, 40, 55, 70, 80, 100};
        for (int v : valores) {
            tree.insert(v);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo7_TodosCaminhamentos.dot");

        System.out.println("Valores inseridos: 50, 25, 75, 15, 35, 60, 90, 10, 20, 30, 40, 55, 70, 80, 100");

        System.out.println("\nCaminhamento Pre-Ordem:");
        BTreeTraversal.preOrder(tree.root);

        System.out.println("\nCaminhamento Em Ordem:");
        BTreeTraversal.inOrder(tree.root);

        System.out.println("\nCaminhamento Pos-Ordem:");
        BTreeTraversal.posOrder(tree.root);
        System.out.println();

        // busca
        System.out.println("\nTestes de busca:");
        System.out.println("Buscar 60: " + (tree.search(60) ? "Encontrado" : "Nao encontrado"));
        System.out.println("Buscar 45: " + (tree.search(45) ? "Encontrado" : "Nao encontrado"));
        System.out.println("Buscar 100: " + (tree.search(100) ? "Encontrado" : "Nao encontrado"));
    }

    // exemplo 8 — demonstra substituicao por predecessor e sucessor
    // remove nos internos para acionar getPredecessor e getSuccessor
    private static void gerarExemplo8() {
        System.out.println("\n*** Exemplo 8: Predecessor e Sucessor com evolucao");
        BTree tree = new BTree(3);

        for (int i = 1; i <= 25; i++) {
            tree.insert(i);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo8a_Inicial.dot");
        System.out.println("Estado 1: Arvore com valores 1 a 25");

        // remove no interno para usar predecessor
        System.out.println("\nRemovendo 13 usa predecessor");
        tree.remove(13);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo8b_RemovePredecessor.dot");
        System.out.println("Estado 2: Removido 13 substituido por predecessor");

        // remove outro no interno para usar sucessor
        System.out.println("Removendo 7 (usa sucessor)");
        tree.remove(7);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo8c_RemoveSucessor.dot");
        System.out.println("Estado 3: Removido 7 substituido por sucessor");

        System.out.println("\nCaminhamento apos remocoes:");
        BTreeTraversal.inOrder(tree.root);
        System.out.println();
    }

    // exemplo 9 — insercao em padrao ziguezague
    // testa balanceamento com insercoes alternadas
    private static void gerarExemplo9() {
        System.out.println("\n*** Exemplo 9: Padrao Ziguezague com evolucao");
        BTree tree = new BTree(2);

        int[] valores = {50, 10, 90, 20, 80, 30, 70, 40, 60, 5, 95, 15, 85, 25, 75, 35, 65, 45, 55};

        System.out.println("Inserindo em padrao alternado (ziguezague)");

        // primeiros 6 valores
        for (int i = 0; i < 6; i++) {
            tree.insert(valores[i]);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo9a_Inicio.dot");
        System.out.println("Estado 1: 6 valores (50,10,90,20,80,30)");

        // primeiros 12 valores
        for (int i = 6; i < 12; i++) {
            tree.insert(valores[i]);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo9b_Meio.dot");
        System.out.println("Estado 2: 12 valores inseridos");

        // todos valores
        for (int i = 12; i < valores.length; i++) {
            tree.insert(valores[i]);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo9c_Final.dot");
        System.out.println("Estado 3: 19 valores completos");

        System.out.println("\nCaminhamento em ordem:");
        BTreeTraversal.inOrder(tree.root);
        System.out.println();
    }

    // exemplo 10 — teste completo
    private static void gerarExemplo10() {
        System.out.println("\n=== Exemplo 10: Teste Completo de Stress (com evolucao) ===");
        BTree tree = new BTree(2);

        System.out.println("Fase 1: Inserindo 40 valores");
        int[] inserir = {50, 25, 75, 10, 30, 60, 90, 5, 15, 20, 35, 55, 65, 85, 95,
                3, 7, 12, 17, 22, 27, 32, 37, 52, 57, 62, 67, 82, 87, 92, 97,
                1, 8, 13, 18, 23, 28, 33, 38, 42};

        for (int v : inserir) {
            tree.insert(v);
        }
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo10a_Fase1_40valores.dot");
        System.out.println("Estado 1: 40 valores inseridos");

        // fase 2: testa buscas
        System.out.println("\nFase 2: Testando buscas");
        int[] buscar = {50, 1, 97, 100, 25, 200};
        for (int v : buscar) {
            System.out.println("  Buscar " + v + ": " + (tree.search(v) ? "Encontrado" : "Nao encontrado"));
        }

        // fase 3: remove valores que acionam diferentes operacoes
        System.out.println("\nFase 3: Removendo valores estrategicos");

        // remove primeiros 5 valores
        tree.remove(1);
        tree.remove(50);
        tree.remove(97);
        tree.remove(25);
        tree.remove(85);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo10b_Fase3_Remove5.dot");
        System.out.println("Estado 2: Removidos 5 valores (1,50,97,25,85)");

        // remove mais 5 valores
        tree.remove(7);
        tree.remove(67);
        tree.remove(30);
        tree.remove(90);
        tree.remove(15);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo10c_Fase3_Remove10.dot");
        System.out.println("Estado 3: Removidos 10 valores no total");

        // remove ultimos 5 valores
        tree.remove(75);
        tree.remove(5);
        tree.remove(95);
        tree.remove(35);
        tree.remove(60);
        salvarDot(BTreeVisualization.generateDot(tree), "dotFiles/Exemplo10d_Fase3_Remove15_Final.dot");
        System.out.println("Estado 4: Removidos 15 valores no total (final)");

        System.out.println("\nFase 4: Estado final");
        System.out.println("Caminhamento pre-ordem:");
        BTreeTraversal.preOrder(tree.root);
        System.out.println("\n\nCaminhamento em ordem:");
        BTreeTraversal.inOrder(tree.root);
        System.out.println("\n\nCaminhamento pos-ordem:");
        BTreeTraversal.posOrder(tree.root);
        System.out.println();
    }

    private static void salvarDot(String conteudo, String caminho) {
        try {
            java.io.File dir = new java.io.File("dotFiles");
            if (!dir.exists()) dir.mkdirs();

            FileWriter fw = new FileWriter(caminho);
            fw.write(conteudo);
            fw.close();
            System.out.println("✓ Salvo: " + caminho);
        } catch (IOException e) {
            System.err.println("Erro ao salvar " + caminho + ": " + e.getMessage());
        }
    }
}