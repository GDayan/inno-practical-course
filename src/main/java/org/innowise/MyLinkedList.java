package org.innowise;

/**
 * Implementation of a doubly linked list.
 *
 * @param <T> the type of elements in this list
 */
public class MyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Internal class representing a node in the doubly linked list.
     *
     * @param <T> the type of data stored in the node
     */
    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        /**
         * Constructs a new node with the specified data.
         *
         * @param data the data to store in the node
         */
        Node(T data) {
            this.data = data;
        }
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Adds an element to the beginning of the list.
     *
     * @param el the element to add
     */
    public void addFirst(T el) {
        Node<T> node = new Node<>(el);
        if (head == null) {
            head = tail = node;
        } else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    /**
     * Adds an element to the end of the list.
     *
     * @param el the element to add
     */
    public void addLast(T el) {
        Node<T> node = new Node<>(el);
        if (tail == null) {
            head = tail = node;
        } else {
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    /**
     * Adds an element at the specified index in the list.
     *
     * @param index the position to insert the element
     * @param el    the element to add
     * @throws IndexOutOfBoundsException if index < 0 or index > size
     */
    public void add(int index, T el) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            addFirst(el);
            return;
        }
        if (index == size) {
            addLast(el);
            return;
        }

        Node<T> current = getNode(index);
        Node<T> node = new Node<>(el);
        Node<T> prevNode = current.prev;

        prevNode.next = node;
        node.prev = prevNode;
        node.next = current;
        current.prev = node;

        size++;
    }

    /**
     * Returns the first element of the list.
     *
     * @return the first element
     * @throws IllegalStateException if the list is empty
     */
    public T getFirst() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        return head.data;
    }

    /**
     * Returns the last element of the list.
     *
     * @return the last element
     * @throws IllegalStateException if the list is empty
     */
    public T getLast() {
        if (tail == null) {
            throw new IllegalStateException("List is empty");
        }
        return tail.data;
    }

    /**
     * Returns the element at the specified index.
     *
     * @param index the index of the element
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        return getNode(index).data;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * @return the removed element
     * @throws IllegalStateException if the list is empty
     */
    public T removeFirst() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        T data = head.data;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null;
        }
        size--;
        return data;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * @return the removed element
     * @throws IllegalStateException if the list is empty
     */
    public T removeLast() {
        if (tail == null) {
            throw new IllegalStateException("List is empty");
        }
        T data = tail.data;
        tail = tail.prev;
        if (tail != null) {
            tail.next = null;
        } else {
            head = null;
        }
        size--;
        return data;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * @param index the index of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) return removeFirst();
        if (index == size - 1) return removeLast();

        Node<T> current = getNode(index);
        Node<T> prevNode = current.prev;
        Node<T> nextNode = current.next;

        prevNode.next = nextNode;
        nextNode.prev = prevNode;

        size--;
        return current.data;
    }

    /**
     * Returns the node at the specified index.
     *
     * @param index the index of the node
     * @return the node at the specified index
     * @throws IndexOutOfBoundsException if index < 0 or index >= size
     */
    public Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }
}
