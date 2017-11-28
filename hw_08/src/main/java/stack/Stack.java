package stack;

/**
 * Stack interface.
 * @param <E> type of storing values.
 */
public interface Stack<E> {
    /**
     * Pops element from stack/
     * @return element that was popped.
     */
    E pop();

    /**
     * Pushed element on stack.
     * @param item element to push.
     * @return element that was pushed.
     */
    E push(E item);

    /**
     * Returns element on top without popping it.
     * @return element on top.
     */
    E peek();

    /**
     * Checks whether stack is empty.
     * @return true if it is empty otherwise false.
     */
    boolean empty();

    /**
     * Clears all stack, no elements will be saved.
     */
    void clear();
}
