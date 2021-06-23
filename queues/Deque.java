/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    /**
     * Class invariant:
     * `start` is the first free index of `arr` when adding to the front.
     * `end` is the first empty index of `arr` when adding to the end.
     * `arr[i] == null` iff it is empty.
     */
    private static final int INIT_SIZE = 2;
    private Item[] arr;
    private int start;
    private int end;

    // construct an empty deque
    public Deque() {
        start = INIT_SIZE - 1;
        end = 0;
        arr = (Item[]) new Object[INIT_SIZE];
    }

    // is the deque empty?
    public boolean isEmpty() {
        int s = (start + 1) % arr.length;
        int e = (end - 1) % arr.length;
        e = e < 0 ? e + arr.length : e;
        return arr[e] == null && arr[s] == null;
    }

    // return the number of items on the deque
    public int size() {
        // Empty => size() == 0
        if (isEmpty()) {
            return 0;
        }
        // Full => size() == arr.length
        if (start + 1 == end) {
            return arr.length;
        }
        return end <= start ? end + arr.length - start - 1 : end - start - 1;
    }

    public void resize(int len) {
        Item[] newArr = (Item[]) new Object[len];
        int newStart = len - 1;
        int newEnd = 0;
        if (!isEmpty()) {
            for (int i = (start + 1) % arr.length; i != end - 1; i = (i + 1) % arr.length) {
                newArr[newEnd] = arr[i];
                newEnd = (newEnd + 1) % newArr.length;
            }
        }
        System.out.println(len);
        arr = newArr;
        start = newStart;
        end = newEnd;
        for (int i = 0; i < len; i++) {
            System.out.println(arr[i]);
        }
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size() == arr.length) {
            resize(arr.length * 2);
        }
        arr[start] = item;
        start = (start - 1) % arr.length;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size() == arr.length) {
            resize(arr.length * 2);
        }
        arr[end] = item;
        end = (end + 1) % arr.length;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if ((size()) * 4 <= arr.length) {
            resize(arr.length / 2);
        }
        int startIdx = (start + 1) % arr.length;
        Item i = arr[startIdx];
        arr[startIdx] = null;
        start = startIdx;
        return i;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if ((size()) * 4 <= arr.length) {
            resize(arr.length / 2);
        }
        int endIdx = (end - 1) % arr.length;
        endIdx = endIdx < 0 ? endIdx + arr.length : endIdx;
        Item i = arr[endIdx];
        arr[endIdx] = null;
        end = endIdx;
        return i;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private int i = start + 1;

        public boolean hasNext() {
            return !isEmpty() && i != end - 1;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            i = (i + 1) % arr.length;
            return arr[i - 1];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<>();
        System.out.println(
                String.format("Testing new deque is empty: %s",
                              d.isEmpty() ? "Passed" : "Failed"));
        d.addFirst(1);
        System.out.println(
                String.format("Testing size on deque with 1 element added to front: %s",
                              d.size() == 1 ? "Passed" : "Failed"));
        System.out.println(
                String.format("Testing removeFirst on deque with 1 element added to front: %s",
                              d.removeFirst() == 1 ? "Passed" : "Failed"));
        d.addFirst(2);
        System.out.println(
                String.format("Testing removeLast on deque with 1 element added to front: %s",
                              d.removeLast() == 2 ? "Passed" : "Failed"));
        System.out.println(String.format("Added and removed 2 elements, check if deque empty: %s",
                                         d.isEmpty() ? "Passed" : "Failed"));
        d.addLast(3);
        System.out.println(
                String.format("Testing size on deque with 1 element added to back: %s",
                              d.size() == 1 ? "Passed" : "Failed"));
        System.out.println(
                String.format("Testing removeFirst on deque with 1 element added to back: %s",
                              d.removeFirst() == 3 ? "Passed" : "Failed"));
        d.addLast(4);
        System.out.println(
                String.format("Testing removeLast on deque with 1 element added to back: %s",
                              d.removeLast() == 4 ? "Passed" : "Failed"));
        for (int i = 0; i < 9; i++) {
            d.addLast(i);
        }
        System.out.println(d.size());
        for (Integer i : d) {
            System.out.println(i);
        }
    }

}
