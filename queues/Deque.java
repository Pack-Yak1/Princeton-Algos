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
     */
    private static final int INIT_SIZE = 16;
    private Item[] arr;
    private int start;
    private int end;

    // construct an empty deque
    public Deque() {
        start = INIT_SIZE / 2;
        end = start + 1;
        arr = (Item[]) new Object[INIT_SIZE];
    }

    // is the deque empty?
    public boolean isEmpty() {
        return start == end - 1;
    }

    // return the number of items on the deque
    public int size() {
        return end - start - 1;
    }

    public void resize(int len) {
        
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (start < 0) {
            resize(arr.length * 2);
        }
        arr[start] = item;
        start--;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (start > arr.length - 1) {
            resize(arr.length * 2);
        }
        arr[end] = item;
        end++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item i = arr[start + 1];
        start++;
        arr[start - 1] = null;
        return i;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item i = arr[end - 1];
        end--;
        arr[end + 1] = null;
        return i;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private int i = start + 1;

        public boolean hasNext() {
            return i < end;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            i++;
            return arr[i - 1];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}
