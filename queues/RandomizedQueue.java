/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_SIZE = 2;
    private Item[] arr;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        arr = (Item[]) new Object[INIT_SIZE];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    private void resize(int len) {
        Item[] newArr = (Item[]) new Object[len];
        for (int i = 0; i < size; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (arr.length == size) {
            resize(2 * arr.length);
        }
        arr[size] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (size * 4 <= arr.length) {
            resize(arr.length / 2);
        }
        int i = StdRandom.uniform(size);
        Item item = arr[i];
        size--;
        arr[i] = arr[size];
        arr[size] = item;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return arr[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator();
    }

    private class RQIterator implements Iterator<Item> {
        private final RandomizedQueue<Item> r;

        public RQIterator() {
            r = new RandomizedQueue<Item>();
            r.arr = arr;
            r.size = size;
        }

        public boolean hasNext() {
            return !r.isEmpty();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return r.dequeue();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> d = new RandomizedQueue<>();
        System.out.println(
                String.format("Testing new deque is empty: %s",
                              d.isEmpty() ? "Passed" : "Failed"));
        d.enqueue(1);
        System.out.println(
                String.format("Testing size on deque with 1 element added to front: %s",
                              d.size() == 1 ? "Passed" : "Failed"));
        System.out.println(
                String.format("Testing removeFirst on deque with 1 element added to front: %s",
                              d.dequeue() == 1 ? "Passed" : "Failed"));
        for (int i = 0; i < 20; i++) {
            d.enqueue(i);
        }
        System.out.println("Testing iterator round 1");
        for (Integer i : d) {
            System.out.println(i);
        }
        System.out.println("Finish testing iterator round 1");
        System.out.println("Testing iterator round 2");
        for (Integer i : d) {
            System.out.println(i);
        }
        System.out.println("Finish testing iterator round 2");
        System.out.println("Testing iterator round 3");
        for (Integer i : d) {
            System.out.println(i);
        }
        System.out.println("Finish testing iterator round 3");
        System.out.printf("size of d: %d\n", d.size());
        int s = d.size();
        for (int i = 0; i < s; i++) {
            System.out.println(d.dequeue());
        }
        System.out.printf("Size after removing all 20 elements: %d", d.size());
    }
}
