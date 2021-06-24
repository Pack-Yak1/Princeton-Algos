/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        private Item val;
        private Node prev;
        private Node next;

        public Node(Item i) {
            val = i;
            prev = null;
            next = null;
        }

        public Node(Item i, Node p, Node n) {
            val = i;
            prev = p;
            next = n;
        }

        public Item getVal() {
            return val;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrev() {
            return prev;
        }

        public void setVal(Item val) {
            this.val = val;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public void setPrev(Node prev) {
            this.prev = prev;
        }

        public void insertBefore(Node after) {
            next = after;
            after.prev = this;
        }

        public void insertAfter(Node before) {
            prev = before;
            before.next = this;
        }
    }

    private Node first;
    private Node last;
    private int size;

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node n = new Node(item);
        if (size == 0) {
            first = n;
            last = n;
        }
        else {
            n.insertBefore(first);
            first = n;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node n = new Node(item);
        if (size == 0) {
            first = n;
            last = n;
        }
        else {
            n.insertAfter(last);
            last = n;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node n = first;
        Item item = n.getVal();
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            first = first.getNext();
            first.setPrev(null);
            n.setNext(null);
            n.setVal(null);
        }
        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node n = last;
        Item item = n.getVal();
        if (size == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.getPrev();
            last.setNext(null);
            n.setPrev(null);
            n.setVal(null);
        }
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node n;

        public DequeIterator() {
            n = first;
        }

        public boolean hasNext() {
            return n != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = n.getVal();
            n = n.getNext();
            return item;
        }
    }


    // /**
    //  * Class invariant:
    //  * `start` is the first free index of `arr` when adding to the front.
    //  * `end` is the first empty index of `arr` when adding to the end.
    //  * `arr[i] == null` iff it is empty.
    //  */
    // private static final int INIT_SIZE = 2;
    // private Item[] arr;
    // private int start;
    // private int end;
    //
    // // construct an empty deque
    // public Deque() {
    //     start = INIT_SIZE - 1;
    //     end = 0;
    //     arr = (Item[]) new Object[INIT_SIZE];
    // }
    //
    // // is the deque empty?
    // public boolean isEmpty() {
    //     int s = (start + 1) % arr.length;
    //     int e = (end - 1) % arr.length;
    //     e = e < 0 ? e + arr.length : e;
    //     return arr[e] == null && arr[s] == null;
    // }
    //
    // // return the number of items on the deque
    // public int size() {
    //     // Empty => size() == 0
    //     if (isEmpty()) {
    //         return 0;
    //     }
    //     // Full => size() == arr.length
    //     if ((start + 1) % arr.length == end) {
    //         return arr.length;
    //     }
    //     return end <= start ? end + arr.length - start - 1 : end - start - 1;
    // }
    //
    // private void resize(int len) {
    //     // System.out.println("Resizing occurred");
    //     // System.out.println(String.format("new arr has length %d", len));
    //     // System.out.println(String.format("old arr was not empty: %b", !isEmpty()));
    //     Item[] newArr = (Item[]) new Object[len];
    //     int newStart = len - 1;
    //     int newEnd = 0;
    //     if (!isEmpty()) {
    //         int startIdx = (start + 1) % arr.length;
    //         boolean firstRound = startIdx == end;
    //         for (int i = startIdx; i != end || firstRound;
    //              i = (i + 1) % arr.length) {
    //             if (i == startIdx && i == end) {
    //                 firstRound = false;
    //             }
    //             // System.out.println(String.format("i = %d, newEnd = %d", i, newEnd));
    //             newArr[newEnd] = arr[i];
    //             newEnd = (newEnd + 1) % newArr.length;
    //         }
    //     }
    //     arr = newArr;
    //     start = newStart;
    //     end = newEnd;
    // }
    //
    // // add the item to the front
    // public void addFirst(Item item) {
    //     if (item == null) {
    //         throw new IllegalArgumentException();
    //     }
    //     if (size() == arr.length) {
    //         resize(arr.length * 2);
    //     }
    //     arr[start] = item;
    //     start = (start - 1);
    //     start = start < 0 ? start + arr.length : start;
    // }
    //
    // // add the item to the back
    // public void addLast(Item item) {
    //     if (item == null) {
    //         throw new IllegalArgumentException();
    //     }
    //     if (size() == arr.length) {
    //         resize(arr.length * 2);
    //     }
    //     arr[end] = item;
    //     end = (end + 1) % arr.length;
    // }
    //
    // // remove and return the item from the front
    // public Item removeFirst() {
    //     if (isEmpty()) {
    //         throw new NoSuchElementException();
    //     }
    //     if ((size()) * 4 <= arr.length) {
    //         resize(arr.length / 2);
    //     }
    //     int startIdx = (start + 1) % arr.length;
    //     Item i = arr[startIdx];
    //     arr[startIdx] = null;
    //     start = startIdx;
    //     return i;
    // }
    //
    // // remove and return the item from the back
    // public Item removeLast() {
    //     if (isEmpty()) {
    //         throw new NoSuchElementException();
    //     }
    //     if ((size()) * 4 <= arr.length) {
    //         resize(arr.length / 2);
    //     }
    //     int endIdx = (end - 1) % arr.length;
    //     endIdx = endIdx < 0 ? endIdx + arr.length : endIdx;
    //     Item i = arr[endIdx];
    //     arr[endIdx] = null;
    //     end = endIdx;
    //     return i;
    // }
    //
    // // return an iterator over items in order from front to back
    // public Iterator<Item> iterator() {
    //     return new DequeIterator();
    // }
    //
    // private class DequeIterator implements Iterator<Item> {
    //     private final int s = (start + 1) % arr.length;
    //     private int i = s;
    //     private boolean seenEnd = false;
    //
    //     public boolean hasNext() {
    //         if (isEmpty()) {
    //             return false;
    //         }
    //         // For handling full arrays
    //         if (i == s && i == end) {
    //             if (!seenEnd) {
    //                 seenEnd = true;
    //                 return true;
    //             }
    //             else {
    //                 return false;
    //             }
    //         }
    //         return i != end;
    //     }
    //
    //     public void remove() {
    //         throw new UnsupportedOperationException();
    //     }
    //
    //     public Item next() {
    //         if (!hasNext()) {
    //             throw new NoSuchElementException();
    //         }
    //         Item item = arr[i];
    //         i = (i + 1) % arr.length;
    //         return item;
    //     }
    // }

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

        for (int i = 0; i < 100; i++) {
            d.addLast(i);
        }
        System.out.println(String.format("After adding 100 elements, size is %d", d.size()));
        System.out.println("Testing iterator (should see 0 to 99)");
        for (Integer i : d) {
            System.out.println(i);
        }
        System.out.println("Finished testing iterator");
        for (int i = 0; i < 100; i++) {
            if (i < 50) {
                System.out.println(d.removeFirst());
            }
            else {
                System.out.println(d.removeLast());
            }
        }
    }

}
