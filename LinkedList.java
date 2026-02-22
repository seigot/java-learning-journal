/**
 * Homework 07 - LinkedList implementation (no imports allowed)
 *
 * Notes enforced by spec:
 * - LinkedList<E> is public, all methods are public
 * - Node<E> is a private inner generic class, and all of its elements are private
 * - All instance variables of LinkedList are private
 * - No checked exceptions are declared in headers
 * - IndexOutOfBoundsException message must be: "Index out of bounds: [index]"
 */
public class LinkedList<E> {

    // Head node of the linked list (null if empty)
    private Node<E> head;

    // Number of values/nodes in the list
    private int size;

    /**
     * Private generic inner node class.
     * Each node stores its data and a reference to the next node.
     */
    private class Node<E> {
        private E data;        // value stored in this node (may be null)
        private Node<E> next;  // reference to next node (null if last)

        // 2-arg constructor: (data, next) in that order
        private Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    /**
     * 0-arg constructor: creates an empty list.
     */
    public LinkedList() {
        head = null;
        size = 0;
    }

    /**
     * @return number of elements in the list
     */
    public int size() {
        return size;
    }

    /**
     * @return true if list has no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all elements from the list.
     * Tip from spec: should be 2 lines.
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Adds value at a specific index.
     * Valid indexes: 0..size (inclusive)
     *
     * @param index where to insert
     * @param value value to insert (may be null)
     */
    public void add(int index, E value) {
        checkIndexForAdd(index);

        // Insert at head
        if (index == 0) {
            head = new Node<E>(value, head);
            size++;
            return;
        }

        // Insert after node at (index - 1)
        Node<E> prev = nodeAt(index - 1);
        prev.next = new Node<E>(value, prev.next);
        size++;
    }

    /**
     * Adds value at the end of the list.
     * Tip from spec: can be a single line by using add(int, E).
     *
     * @param value value to add (may be null)
     */
    public void add(E value) {
        add(size, value);
    }

    /**
     * Checks whether the list contains the given value.
     * Must handle null safely and compare by equality.
     *
     * @param o value to search for (may be null)
     * @return true if present
     */
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    /**
     * Returns element at index.
     * Valid indexes: 0..size-1
     *
     * @param index position
     * @return element at index
     */
    public E get(int index) {
        checkIndexForAccess(index);
        return nodeAt(index).data;
    }

    /**
     * Returns index of first occurrence of the specified element, or -1 if not present.
     * Must handle null safely and compare by equality.
     *
     * @param o value to search for (may be null)
     * @return first index or -1
     */
    public int indexOf(Object o) {
        Node<E> current = head;
        int i = 0;
        while (current != null) {
            if (equalsValue(o, current.data)) {
                return i;
            }
            current = current.next;
            i++;
        }
        return -1;
    }

    /**
     * Removes and returns element at index.
     * Valid indexes: 0..size-1
     *
     * @param index position
     * @return removed element
     */
    public E remove(int index) {
        checkIndexForAccess(index);

        // Remove head
        if (index == 0) {
            E removed = head.data;
            head = head.next;
            size--;
            return removed;
        }

        // Remove node after prev
        Node<E> prev = nodeAt(index - 1);
        Node<E> toRemove = prev.next;
        E removed = toRemove.data;
        prev.next = toRemove.next;
        size--;
        return removed;
    }

    /**
     * Removes the first occurrence of a value in the linked list.
     * Returns true if the value was present (and removed), false otherwise.
     *
     * @param o value to remove (may be null)
     * @return whether removal happened
     */
    public boolean remove(Object o) {
        if (head == null) {
            return false;
        }

        // Remove head if it matches
        if (equalsValue(o, head.data)) {
            head = head.next;
            size--;
            return true;
        }

        // Find first matching node
        Node<E> prev = head;
        Node<E> current = head.next;
        while (current != null) {
            if (equalsValue(o, current.data)) {
                prev.next = current.next;
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }

        return false;
    }

    /**
     * Updates the value at an index and returns the old value.
     * Valid indexes: 0..size-1
     *
     * @param index position
     * @param value new value (may be null)
     * @return old value
     */
    public E set(int index, E value) {
        checkIndexForAccess(index);
        Node<E> node = nodeAt(index);
        E old = node.data;
        node.data = value;
        return old;
    }

    /**
     * Returns list representation:
     * - "[]", "[1]", "[1, 2]" etc.
     * - null values printed as "null" (String.valueOf)
     *
     * @return string form of list
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        Node<E> current = head;
        while (current != null) {
            sb.append(String.valueOf(current.data));
            current = current.next;
            if (current != null) {
                sb.append(", ");
            }
        }

        sb.append(']');
        return sb.toString();
    }

    /**
     * equals method must be present.
     * OPTIONAL for points, but implemented correctly here without raw types.
     *
     * @param obj object to compare
     * @return true if obj is a LinkedList with same size and same elements in order
     */
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LinkedList)) { // cannot use LinkedList<E> due to type erasure
            return false;
        }

        LinkedList<?> other = (LinkedList<?>) obj;
        if (this.size != other.size) {
            return false;
        }

        Node<E> a = this.head;
        LinkedList<?>.Node<?> b = other.head; // node of unknown type belonging to other list

        while (a != null && b != null) {
            if (!equalsValue(a.data, b.data)) {
                return false;
            }
            a = a.next;
            b = b.next;
        }

        // Both should reach null together if sizes match
        return a == null && b == null;
    }

    // ---------- Private helper methods (allowed by spec) ----------

    // Returns node at a valid index (0..size-1). Assumes index already validated.
    private Node<E> nodeAt(int index) {
        Node<E> current = head;
        int i = 0;
        while (i < index) {
            current = current.next;
            i++;
        }
        return current;
    }

    // Null-safe equality check: compares by equality (like Objects.equals without imports).
    private boolean equalsValue(Object a, Object b) {
        if (a == b) { // covers both null
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }

    // Validate index for access (get/set/remove): 0..size-1
    private void checkIndexForAccess(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }

    // Validate index for add: 0..size
    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
    }
}