package ru.nsu.buzyrkin;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.buzyurkin.Tree;
import java.util.*;

public class TreeTest {

    /**
     * Test case for checking equality between two trees with different child orders.
     */
    @Test
    void test_equality1() {
        Tree<String> tree = new Tree<>("R1");
        tree.addChild("A");
        tree.addChild("B");

        Tree<String> tree1 = new Tree<>("R1");
        tree1.addChild("B");
        tree1.addChild("A");

        assertTrue(tree.equals(tree1));
    }

    /**
     * Test case for checking equality between two complex trees with different child orders.
     */
    @Test
    void test_equality2() {
        Tree<String> tree = new Tree<>("R1");
        var a1 = tree.addChild("A");
        var b1 = tree.addChild("B");
        a1.addChild("C");
        a1.addChild("D");
        b1.addChild("E");
        b1.addChild("F");


        Tree<String> tree1 = new Tree<>("R1");
        var a2 = tree1.addChild("A");
        var b2 = tree1.addChild("B");
        a2.addChild("D");
        b2.addChild("F");
        a2.addChild("C");
        b2.addChild("E");

        assertTrue(tree.equals(tree1));
    }

    /**
     * Test case for checking inequality between two trees after a node removal.
     */
    @Test
    void test_equality3() {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        a.remove();

        Tree<String> tree1 = new Tree<>("R1");
        tree1.addChild("A");
        var c = tree1.addChild("R2");
        c.addChild("D");
        c.addChild("C");

        assertNotEquals(tree, tree1);
    }

    /**
     * Test case for checking equality between two trees after a node removal.
     */
    @Test
    void test_equality4() {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        var b = a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);
        a.remove();

        Tree<String> tree1 = new Tree<>("R1");
        var c = tree1.addChild("R2");
        c.addChild("D");
        c.addChild("C");

        assertEquals(tree, tree1);
    }

    /**
     * Test case for iterating the entire tree using Depth-First Search (DFS) order.
     */
    @Test
    void test_iteratorWholeTreeDfs() {
        Tree<String> tree = new Tree<>("R1");
        var a1 = tree.addChild("A");
        var b1 = tree.addChild("B");
        a1.addChild("C");
        a1.addChild("D");
        b1.addChild("E");
        b1.addChild("F");

        Iterator<String> iteratorDfs = tree.iterator();
        int size = 0;
        while (iteratorDfs.hasNext()) {
            size++;
            iteratorDfs.next();
        }

        assertEquals(7, size);
    }

    /**
     * Test case for iterating the entire tree using Breadth-First Search (BFS) order.
     */
    @Test
    void test_iteratorWholeTreeBfs() {
        Tree<String> tree = new Tree<>("R1");
        var a1 = tree.addChild("A");
        var b1 = tree.addChild("B");
        a1.addChild("C");
        a1.addChild("D");
        b1.addChild("E");
        b1.addChild("F");

        Iterator<String> iteratorBfs = tree.iteratorBfs();
        int size = 0;
        while (iteratorBfs.hasNext()) {
            size++;
            iteratorBfs.next();
        }

        assertEquals(7, size);
    }

    /**
     * Test case for checking the order of elements when iterating the tree using DFS.
     */
    @Test
    void test_iteratorRightOrderDfs() {
        Tree<String> tree = new Tree<>("R1");
        var a1 = tree.addChild("A");
        var b1 = tree.addChild("B");
        a1.addChild("C");
        a1.addChild("D");
        b1.addChild("E");
        b1.addChild("F");

        Iterator<String> iteratorDfs = tree.iterator();
        int step = 3;
        String val = "";
        while (step > 0) {
            step--;
            if (iteratorDfs.hasNext()) {
                val = iteratorDfs.next();
            }
        }

        assertEquals("C", val);
    }

    /**
     * Test case for ensuring no exceptions are thrown when iterating
     *      after tree modification using DFS.
     */
    @Test
    public void test_iteratorNoExceptionDfs() {
        Tree<String> tree = new Tree<>("R1");
        var a1 = tree.addChild("A");
        var b1 = tree.addChild("B");
        a1.addChild("C");
        a1.addChild("D");
        b1.addChild("E");
        b1.addChild("F");

        Iterator<String> iteratorA = a1.iterator();

        assertDoesNotThrow(() -> {
            b1.addChild("other");
            while (iteratorA.hasNext()) {
                iteratorA.next();
            }
        });
    }

    /**
     * Test case for checking exceptions when iterating after tree modification using DFS.
     */
    @Test
    public void test_iteratorExceptionDfs() {
        Tree<String> tree = new Tree<>("R1");
        var a1 = tree.addChild("A");
        var b1 = tree.addChild("B");
        a1.addChild("C");
        a1.addChild("D");
        b1.addChild("E");
        b1.addChild("F");

        Iterator<String> iteratorA = b1.iterator();

        assertThrows(ConcurrentModificationException.class, () -> {
            b1.addChild("other");
            while (iteratorA.hasNext()) {
                iteratorA.next();
            }
        });
    }

    /**
     * Test case for ensuring no exceptions are thrown when iterating
     *      after tree modification using BFS.
     */
    @Test
    public void test_iteratorNoExceptionBfs() {
        Tree<String> tree = new Tree<>("R1");
        var a1 = tree.addChild("A");
        var b1 = tree.addChild("B");
        a1.addChild("C");
        a1.addChild("D");
        b1.addChild("E");
        b1.addChild("F");

        Iterator<String> iteratorA = a1.iteratorBfs();

        assertDoesNotThrow(() -> {
            b1.addChild("other");
            while (iteratorA.hasNext()) {
                iteratorA.next();
            }
        });
    }

    /**
     * Test case for checking exceptions when iterating after tree modification using BFS.
     */
    @Test
    public void test_iteratorExceptionBfs() {
        Tree<String> tree = new Tree<>("R1");
        var a1 = tree.addChild("A");
        var b1 = tree.addChild("B");
        a1.addChild("C");
        a1.addChild("D");
        b1.addChild("E");
        b1.addChild("F");

        Iterator<String> iteratorA = b1.iteratorBfs();

        assertThrows(ConcurrentModificationException.class, () -> {
            b1.addChild("other");
            while (iteratorA.hasNext()) {
                iteratorA.next();
            }
        });
    }
}