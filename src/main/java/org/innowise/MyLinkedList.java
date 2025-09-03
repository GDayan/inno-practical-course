package org.innowise;

public class MyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T>{
        T data;
        Node<T> next;
        Node<T> prev;

        Node(T data){
            this.data = data;
        }
    }

    public int size(){
        return size;
    }

    public void addFirst(T el){
        Node<T> node = new Node<>(el);
        if(head == null){
            head = tail = node;
        }
        else{
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    public void addLast(T el){
        Node<T> node = new Node<>(el);
        if(tail == null){
            head = tail = node;
        }
        else{
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public void add(int index, T el){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){
            addFirst(el);
            return;
        }
        if(index == size){
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

    public T getFirst(){
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        return head.data;
    }

    public T getLast(){
        if (tail == null) {
            throw new IllegalStateException("List is empty");
        }
        return tail.data;
    }

    public T get(int index){
        return getNode(index).data;
    }

    public T removeFirst(){
        if (head == null) {
            throw new IllegalStateException("List is empty");
        }
        T data = head.data;
        head = head.next;
        if (head != null) {
            head.prev = null;
        }
        else {
            tail = null;
        }
        size--;
        return data;
    }

    public T removeLast(){
        if(tail == null){
            throw new IllegalStateException("List is empty");
        }
        T data = tail.data;
        tail = tail.prev;
        if(tail != null){
            tail.next = null;
        }
        else{
            head = null;
        }
        size--;
        return data;
    }

    public T remove(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){
            return removeFirst();
        }
        if(index == size - 1){
            return removeLast();
        }
        Node<T> current = getNode(index);
        Node<T> prevNode = current.prev;
        Node<T> nextNode = current.next;

        prevNode.next = nextNode;
        nextNode.prev = prevNode;

        size--;
        return current.data;
    }

    public Node<T> getNode(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        Node<T> current;
        if(index < size / 2){
            current = head;
            for(int i = 0; i < index; i++){
                current = current.next;
            }
        }
        else{
            current = tail;
            for(int i = size - 1; i > index; i--){
                current = current.prev;
            }
        }
        return current;
    }

}
