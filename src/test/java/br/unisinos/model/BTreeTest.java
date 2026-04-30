package br.unisinos.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import br.unisinos.service.BTreeTraversal;

import java.io.ByteArrayOutputStream;

class BTreeTest {

    @Test
    void testSearchEncontrado() {
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);

        assertTrue(tree.search(10));
        assertTrue(tree.search(20));
        assertTrue(tree.search(5));
    }

    @Test
    void testSearchNaoEncontrado() {
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);

        assertFalse(tree.search(99));
        assertFalse(tree.search(0));
    }

    @Test
    void testSearchArvoreVazia() {
        BTree tree = new BTree(2);
        assertFalse(tree.search(10));
    }


    @Test
    void testInsertSimples() {
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);

        assertTrue(tree.search(10));
        assertTrue(tree.search(20));
        assertTrue(tree.search(5));
    }

    @Test
    void testInsertComSplit() {
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);

        assertTrue(tree.search(10));
        assertTrue(tree.search(20));
        assertTrue(tree.search(30));
        assertTrue(tree.search(40));
    }

    @Test
    void testInsertMuitasChaves() {
        BTree tree = new BTree(2);
        int[] valores = {10, 20, 5, 30, 15, 25, 35, 1, 7, 12};
        for (int v : valores) tree.insert(v);

        for (int v : valores) {
            assertTrue(tree.search(v), "Deveria encontrar: " + v);
        }
    }

    @Test
    void testRemoveCaso1FolhaSemUnderflow() {
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);
        tree.insert(30);
        tree.insert(15);

        tree.remove(5);
        assertFalse(tree.search(5));
        assertTrue(tree.search(10));
        assertTrue(tree.search(15));
    }

    @Test
    void testRemoveCaso2ChaveNaoFolha() {
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);
        tree.insert(30);
        tree.insert(15);
        tree.insert(25);
        tree.insert(35);

        tree.remove(20);
        assertFalse(tree.search(20));

        assertTrue(tree.search(10));
        assertTrue(tree.search(25));
        assertTrue(tree.search(30));
    }

    @Test
    void testRemoveCaso3IrmaoRico() {
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(40);
        tree.insert(50);
        tree.insert(5);

        tree.remove(5);
        assertFalse(tree.search(5));
        assertTrue(tree.search(10));
        assertTrue(tree.search(20));
    }

    @Test
    void testRemoveCaso4IrmaoPobre() {
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);
        tree.insert(30);
        tree.insert(15);

        tree.remove(15);
        assertFalse(tree.search(15));
        assertTrue(tree.search(10));
        assertTrue(tree.search(20));
        assertTrue(tree.search(30));
    }

    @Test
    void testRemoveNaoExistente() {
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);

        assertDoesNotThrow(() -> tree.remove(99));
        assertTrue(tree.search(10));
        assertTrue(tree.search(20));
    }

    @Test
    void testRemoveTodos() {
        BTree tree = new BTree(2);
        int[] valores = {10, 20, 5, 30, 15};
        for (int v : valores) tree.insert(v);
        for (int v : valores) tree.remove(v);

        for (int v : valores) {
            assertFalse(tree.search(v), "Não deveria encontrar: " + v);
        }
    }

    @Test
    void testPreOrderSimples() {
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        BTreeTraversal.preOrder(tree.root);
        System.setOut(System.out);

        assertEquals("5 10 20 ", out.toString());
    }

    @Test
    void testInOrderProduzsOrdemCrescente() {
        BTree tree = new BTree(2);
        int[] valores = {10, 20, 5, 30, 15, 25, 35, 1, 7, 12};
        for (int v : valores) tree.insert(v);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        BTreeTraversal.inOrder(tree.root);
        System.setOut(System.out);

        // in-order numa BST/BTree sempre produz ordem crescente
        assertEquals("1 5 7 10 12 15 20 25 30 35 ", out.toString());
    }

    @Test
    void testPosOrderSimples() {
        BTree tree = new BTree(2);
        tree.insert(10);
        tree.insert(20);
        tree.insert(5);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        BTreeTraversal.posOrder(tree.root);
        System.setOut(System.out);

        assertEquals("5 10 20 ", out.toString());
    }
}