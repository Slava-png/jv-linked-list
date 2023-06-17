package core.basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(tail, value, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
            return;
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("There is no element on index " + index);
        }

        Node<T> node = findNodeByIndex(index);
        Node<T> newNode = new Node<T>(node.prev, value, node);
        if (node.prev == null) {
            head = newNode;
        } else {
            node.prev.next = newNode;
        }
        node.prev = newNode;
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        indexValidator(index);

        return findNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        indexValidator(index);

        Node<T> node = findNodeByIndex(index);
        T previousValue = node.value;
        node.value = value;
        return previousValue;
    }

    @Override
    public T remove(int index) {
        indexValidator(index);

        Node<T> removedNode = findNodeByIndex(index);
        unlink(removedNode);
        size--;
        return removedNode.value;
    }

    @Override
    public boolean remove(T object) {
        Node<T> removedNode = findNodeByValue(object);

        if (removedNode == null) {
            return false;
        }

        unlink(removedNode);
        size--;
        if (size == 0) {
            head = null;
        }
        if (size == 1) {
            tail = null;
        }

        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public void indexValidator(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("There is no element on index " + index);
        }
    }

    public Node<T> findNodeByValue(T object) {
        Node<T> currNode = head;

        while (currNode != null) {
            if (Objects.equals(object, currNode.value)) {
                return currNode;
            }
            currNode = currNode.next;
        }

        return null;
    }

    public Node<T> findNodeByIndex(int index) {
        Node<T> currNode;

        if (size / 2 > index) {
            currNode = head;
            for (int i = 0; i < index; i++) {
                currNode = currNode.next;
            }
        } else {
            currNode = tail;
            for (int i = size - 1; i > index; i--) {
                currNode = currNode.prev;
            }
        }

        return currNode;
    }

    public void unlink(Node<T> node) {
        if (node.prev == null) {
            head = head.next;
        } else if (node.next == null) {
            tail = tail.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
    }

    private class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(Node<T> prev, T value, Node<T> next) {
            this.prev = prev;
            this.value = value;
            this.next = next;
        }
    }
}
