package co.boorse.seleniumtable;

import java.util.Iterator;
import java.util.function.Function;

class LazyIterator<E> implements Iterator<E> {

    private int stop;

    private int step;

    private int counter;

    private Function<Integer, E> producer;

    LazyIterator(int start, int stop, Function<Integer, E> producer) {
        this(start, stop, 1, producer);
    }

    LazyIterator(int start, int stop, int step, Function<Integer, E> producer) {
        this.stop = stop;
        this.step = step;
        this.producer = producer;
        this.counter = start - step;
    }

    @Override
    public boolean hasNext() {
        return (counter + step) < stop;
    }

    @Override
    public E next() {
        counter += step;
        return producer.apply(counter);
    }
}
