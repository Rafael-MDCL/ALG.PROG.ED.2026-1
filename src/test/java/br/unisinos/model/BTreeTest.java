package br.unisinos.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
}