package com.java;

import java.util.List;

public class Node<T> {
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node(T value) {
        this.value = value;
    }
}
