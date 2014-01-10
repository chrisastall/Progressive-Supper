package org.progressive.iterators;
import java.util.Iterator;
import java.util.List;

public class ReverseIterator<T> implements Iterator<T>, Iterable<T> {

    private final List<T> collection;
    private int position;

    public ReverseIterator(List<T> collection) {
        this.collection = collection;
        this.position = collection.size() - 1;
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return position >= 0;
    }

    @Override
    public T next() {
        return collection.get(position--);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}