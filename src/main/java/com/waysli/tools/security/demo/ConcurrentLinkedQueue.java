package com.waysli.tools.security.demo;

import java.util.concurrent.atomic.AtomicReference;

public class ConcurrentLinkedQueue<T> {
    private static class Node<T> {
        volatile AtomicReference<T> item;
        volatile AtomicReference<Node<T>> next;

        Node(T item) {
            this.item = new AtomicReference<>(item);
            this.next = new AtomicReference<>();
        }

        boolean casItem(T cmp, T val) {
            return item.compareAndSet(cmp, val);
        }

        boolean casNext(Node<T> cmp, Node<T> val) {
            return next.compareAndSet(cmp, val);
        }

        void lazySetNext(Node<T> val) {
            next.lazySet(val);
        }
    }

    public ConcurrentLinkedQueue() {
        head = new AtomicReference<>(new Node<>(null));
        tail = new AtomicReference<>(head.get());
    }

    private transient volatile AtomicReference<Node<T>> head;

    private transient volatile AtomicReference<Node<T>> tail;

    private boolean casTail(Node<T> cmp, Node<T> val) {
        return tail.compareAndSet(cmp, val);
    }

    private boolean casHead(Node<T> cmp, Node<T> val) {
        return head.compareAndSet(cmp, val);
    }

    final void updateHead(Node<T> h, Node<T> p) {
        if (h != p && casHead(h, p))
            h.lazySetNext(h);
    }

    final Node<T> succ(Node<T> p) {
        Node<T> next = p.next.get();
        return (p == next) ? head.get() : next;
    }

    private static void checkNotNull(Object v) {
        if (v == null)
            throw new NullPointerException();
    }

    public boolean offer(T item) {
        checkNotNull(item);
        final Node<T> newNode = new Node<>(item);

        for (Node<T> t = tail.get(), p = t;;) {
            Node<T> next = succ(p);
            if (next == null) {
                if (p.casNext(null, newNode)) {
                    if (p != t) {
                        casTail(t, newNode);
                    }
                    return true;
                }
            } else if (t != (t = tail.get())) { // tail is changed, retry from new tail.
                p = t;
            } else {
                p = next;
            }
        }
    }

    public T poll() {
        for (Node<T> h = head.get(), p = h, q;;) {
            T item = p.item.get();
            if (item != null && p.casItem(item, null)) {
                if (p != h) {
                    updateHead(h, ((q = p.next.get()) == null ? p : q));
                }
                return item;
            }
            Node<T> next = succ(p);
            if (next == null) {
                updateHead(h, p);
                return null;
            }
            p = next;
        }
    }
}
