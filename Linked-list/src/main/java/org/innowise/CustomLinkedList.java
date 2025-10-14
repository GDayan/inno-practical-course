package org.innowise;

/**
 * A custom implementation of a doubly-linked list that supports basic list operations.
 * This implementation provides efficient insertion and deletion from both ends of the list
 * and supports element access by index with optimized traversal.
 *
 * @param <T> the type of elements maintained by this list
 * @author Innowise
 * @version 1.0
 */
public class CustomLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Internal node class representing an element in the linked list.
     * Each node contains the data, reference to the next node, and reference to the previous node.
     *
     * @param <T> the type of element stored in the node
     */
    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        /**
         * Constructs a new node with the specified data.
         *
         * @param data the element to be stored in the node
         */
        Node(T data) {
            this.data = data;
        }
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }

    /**
     * Inserts the specified element at the beginning of this list.
     *
     * @param element the element to be added to the front of the list
     */
    public void addFirst(T element) {
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param element the element to be added to the end of the list
     */
    public void addLast(T element) {
        Node<T> newNode = new Node<>(element);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * @param index the index at which the specified element is to be inserted
     * @param element the element to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index > size)
     */
    public void add(int index, T element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            addFirst(element);
        } else if (index == size) {
            addLast(element);
        } else {
            Node<T> current = getNode(index);
            Node<T> newNode = new Node<>(element);

            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = newNode;
            current.prev = newNode;

            size++;
        }
    }

    /**
     * Returns the first element in this list.
     *
     * @return the first element in this list
     * @throws IllegalStateException if this list is empty
     */
    public T getFirst() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        return head.data;
    }

    /**
     * Returns the last element in this list.
     *
     * @return the last element in this list
     * @throws IllegalStateException if this list is empty
     */
    public T getLast() {
        if (tail == null) {
            throw new IllegalStateException("List is empty");
        }
        return tail.data;
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index the index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    public T get(int index) {
        return getNode(index).data;
    }

    /**
     * Removes and returns the first element from this list.
     *
     * @return the first element from this list
     * @throws IllegalStateException if this list is empty
     */
    public T removeFirst() {
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }

        T removeData = head.data;
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return removeData;
    }

    /**
     * Removes and returns the last element from this list.
     *
     * @return the last element from this list
     * @throws IllegalStateException if this list is empty
     */
    public T removeLast() {
        if (tail == null) {
            throw new IllegalStateException("List is empty");
        }

        T removeData = tail.data;
        if (tail == head) {
            tail = head = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }

        size--;
        return removeData;
    }

    /**
     * Removes and returns the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     *
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            return removeFirst();
        } else if (index == size - 1) {
            return removeLast();
        } else {
            Node<T> nodeToRemove = getNode(index);
            T removeData = nodeToRemove.data;

            nodeToRemove.prev.next = nodeToRemove.next;
            nodeToRemove.next.prev = nodeToRemove.prev;
            size--;
            return removeData;
        }
    }

    /**
     * Returns the node at the specified position in this list.
     * This method uses optimized traversal by starting from the head if the index
     * is in the first half of the list, or from the tail if in the second half.
     *
     * @param index the index of the node to return
     * @return the node at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size)
     */
    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
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

    /**
     * Returns a string representation of this list.
     * The string representation consists of a list of the list's elements
     * in the order they are stored, enclosed in square brackets ("[]").
     * Adjacent elements are separated by the characters ", " (comma and space).
     *
     * @return a string representation of this list
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        Node<T> current = head;
        while (current != null) {
            stringBuilder.append(current.data);
            if (current.next != null) {
                stringBuilder.append(", ");
            }
            current = current.next;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}