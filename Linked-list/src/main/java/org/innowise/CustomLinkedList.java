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

    public void addFirst(T element){
        Node<T> newNode = new Node<>(element);
        if(head == null){
            head = tail = newNode;
        }else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    public void addLast(T element){
        Node<T> newNode = new Node<>(element);
        if(tail == null){
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public void add(int index, T element){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if(index == 0){
            addFirst(element);
        } else if (index == size) {
            addLast(element);
        } else{
            Node<T> current = getNode(index);
            Node<T> newNode = new Node<>(element);

            newNode.prev = current.prev;
            newNode.next = current;
            current.prev.next = current;
            current.prev = newNode;

            size++;
        }
    }

    public T getFirst(){
        if(head == null){
            throw new IllegalStateException("List is empty");
        }
        return head.data;
    }

    public T getLast(){
        if(tail == null){
            throw new IllegalStateException("List is empty");
        }
        return tail.data;
    }

    public T get(int index){
        return getNode(index).data;
    }

    public T removeFirst(){
        if(head == null){
            throw new IllegalStateException("List is empty");
        }

        T removeData = head.data;
        if(head == tail){
            head = tail = null;
        }else {
            head = head.next;
            head.prev = null;
        }
        size--;
        return removeData;
    }

    public T removeLast(){
        if(tail == null){
            throw new IllegalStateException("List is empty");
        }

        T removeData = tail.data;
        if(tail == head){
            tail = head = null;
        }else {
            tail = tail.prev;
            tail.next = null;
        }

        size--;
        return removeData;
    }

    public T remove(int index){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0){
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

    public Node<T> getNode(int index){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current;
        if(index < size / 2){
            current = head;
            for(int i = 0; i < index; i++){
                current = current.next;
            }
        } else {
            current = tail;
            for(int i = size - 1; i > index; i--){
                current = current.prev;
            }
        }
        return current;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("[");
        Node<T> current = head;
        while (current != null){
            stringBuilder.append(current.data);
            if(current.next != null){
                stringBuilder.append(", ");
            }
            current = current.next;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
