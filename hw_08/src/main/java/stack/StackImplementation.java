package stack;

public class StackImplementation<E> implements Stack<E> {
    private class Node {
        Node next;
        E value;

        Node(Node next, E value) {
            this.next = next;
            this.value = value;
        }
    }

    private Node head = null;

    @Override
    public E pop() {
        E value = head.value;
        head = head.next;
        return value;
    }

    @Override
    public E push(E item) {
        head = new Node(head, item);
        return item;
    }

    @Override
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
