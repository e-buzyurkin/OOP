package ru.nsu.buzyurkin;

import java.util.*;

public class Tree<T> implements Iterable<T>{

    private T value;
    private Tree<T> parent;
    private List<Tree<T>> children;
    private int modifyCount = 0;

    private void incAncestorsModCount(Tree<T> current) {
        if (current.parent == null) {
            return;
        }

        current.modifyCount++;
        incAncestorsModCount(current.parent);
    }

    public Tree(T value) {
        this.value = value;
        this.parent = null;
        this.children = new ArrayList<>();
    }


    /**
     * Adds a new child node with the specified value to the current node.
     *
     * @param value The value of the new child node.
     * @return The newly created child node.
     */
    public Tree<T> addChild(T value) {
        this.modifyCount++;
        incAncestorsModCount(this);

        Tree<T> child = new Tree<T>(value);
        child.parent = this;
        this.children.add(child);

        return child;
    }

    /**
     * Adds a subtree as a child to the current node.
     *
     * @param subtree The subtree to add as a child.
     */
    public void addChild(Tree<T> subtree) {
        this.modifyCount++;
        incAncestorsModCount(this);

        subtree.parent = this;
        this.children.add(subtree);
    }

    /**
     * Removes the current node from the tree, including its descendants.
     */
    public void remove() {
        this.modifyCount++;
        incAncestorsModCount(this);

        if (this.parent != null) {
            this.parent.children.remove(this);
        }
        this.value = null;
        this.children.clear();
    }

    /**
     * Compares this tree to another object for equality.
     * Two trees are considered equal if their values and structures are the same.
     *
     * @param obj The object to compare with this tree.
     * @return true if the two trees are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Tree)) {
            return false;
        }

        Tree<T> otherTree = (Tree<T>) obj;

        if (this.value != otherTree.value) {
            return false;
        }

        if (this.children.size() != otherTree.children.size()) {
            return false;
        }

        List<T> thisTreeValues = this.children.stream().map(tree -> tree.value).toList();
        List<T> otherTreeValues = otherTree.children.stream().map(tree -> tree.value).toList();

        if (!equalLists(thisTreeValues, otherTreeValues)) {
            return false;
        }

        for (int i = 0; i < this.children.size(); i++) {
            Tree<T> curChild = this.children.get(i);

            for (int j = 0; j < otherTree.children.size(); j++) {
                Tree<T> otherChild = otherTree.children.get(j);

                if (curChild.value == otherChild.value) {
                    if (!curChild.equals(otherChild)) {
                        return false;
                    }
                }

            }

        }

        return true;
    }

    private boolean equalLists(List<T> arr1, List<T> arr2) {
        HashSet<T> set1 = new HashSet<T>(arr1);
        HashSet<T> set2 = new HashSet<T>(arr2);
        return set1.equals(set2);
    }

    /**
     * Returns a Depth-First Search (DFS) iterator for the tree.
     *
     * @return A DFS iterator for the tree.
     */
    @Override
    public Iterator<T> iterator() {
        return new DFSIterator();
    }

    /**
     * An iterator for traversing the tree in a Depth-First Search (DFS) order.
     * It allows iterating over the elements in the tree from top to bottom and left to right.
     */
    private class DFSIterator implements Iterator<T> {
        private Stack<Tree<T>> stack;
        private int modifyCountDFS;

        public DFSIterator() {
            modifyCountDFS = modifyCount;

            stack = new Stack<>();
            if (value != null) {
                stack.push(Tree.this);
            }
        }

        @Override
        public boolean hasNext() {
            if (modifyCount != modifyCountDFS) {
                throw new ConcurrentModificationException();
            }

            return !stack.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Tree<T> current = stack.pop();

            // Push children in reverse order to traverse left to right
            ListIterator<Tree<T>> childrenIterator = current.children.listIterator(current.children.size());
            while (childrenIterator.hasPrevious()) {
                stack.push(childrenIterator.previous());
            }

            return current.value;
        }

        @Override
        public void remove() {
            throw new ConcurrentModificationException();
        }
    }

    /**
     * Returns a Breadth-First Search (BFS) iterator for the tree.
     *
     * @return A BFS iterator for the tree.
     */
    public Iterator<T> iteratorBFS() {
        return new BFSIterator();
    }

    /**
     * An iterator for traversing the tree in a Breadth-First Search (BFS) order.
     * It allows iterating over the elements in the tree level by level, from left to right.
     */
    private class BFSIterator implements Iterator<T> {
        private Queue<Tree<T>> queue;
        private int modifyCountBFS;

        public BFSIterator() {
            modifyCountBFS = modifyCount;

            queue = new LinkedList<>();
            if (value != null) {
                queue.add(Tree.this);
            }
        }

        @Override
        public boolean hasNext() {
            if (modifyCount != modifyCountBFS) {
                throw new ConcurrentModificationException();
            }

            return !queue.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Tree<T> current = queue.poll();

            // Enqueue children for the next level
            for (Tree<T> child : current.children) {
                if (child.value != null) {
                    queue.offer(child);
                }
            }

            return current.value;
        }

        @Override
        public void remove() {
            throw new ConcurrentModificationException();
        }
    }
}