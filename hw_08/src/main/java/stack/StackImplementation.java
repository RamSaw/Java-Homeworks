package stack;

import org.jetbrains.annotations.Nullable;

public class StackImplementation<E> implements Stack<E> {
    private class Node {
        Node next;
        E value;

        Node(@Nullable Node next, @Nullable E value) {
            this.next = next;
            this.value = value;
        }
    }

    private Node head = null;

    @Override
    @Nullable
    public E pop() {
        E value = head.value;
        head = head.next;
        return value;
    }

    @Override
    @Nullable
    public E push(@Nullable E item) {
        head = new Node(head, item);
        return item;
    }

    @Override
    @Nullable
    public E peek() {
        return head.value;
    }

    @Override
    public boolean empty() {
        return (head == null);
    }

    @Override
    public void clear() {
        head = null;
    }
}
